/*
 * junixsocket
 *
 * Copyright 2009-2021 Christian Kohlschütter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.newsclub.net.unix;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.kohlschutter.testutil.AssertUtil;
import com.kohlschutter.util.IOUtil;

/**
 * Tests {@link Socket#setSoTimeout(int)} behavior.
 * 
 * @see <a href="http://code.google.com/p/junixsocket/issues/detail?id=14">Issue 14</a>
 */
public class SoTimeoutTest extends SocketTestBase {
  /**
   * Triggers a case where {@link Socket#setSoTimeout(int)} fails on some platforms: when the socket
   * is closed.
   * 
   * @throws IOException on error.
   */
  @Test
  public void issue14Fail() throws Exception {
    Semaphore sema = new Semaphore(0);
    try (ServerThread serverThread = new ServerThread() {

      @Override
      protected void handleConnection(final AFUNIXSocket socket) throws IOException {
        try {
          socket.close();
        } finally {
          sema.release();
        }
      }
    }; AFUNIXSocket sock = connectToServer()) {
      sema.acquire();

      try {
        sock.setSoTimeout((int) TimeUnit.SECONDS.toMillis(12));
        // Socket#setSoTimeout(int) did not throw a SocketException. This is OK.
      } catch (final SocketException e) {
        // expected, as the socket is actually closed
      }
    }
  }

  /**
   * Triggers a regular case where {@link Socket#setSoTimeout(int)} should work.
   * 
   * @throws IOException on error.
   */
  @Test
  public void issue14Pass() throws Exception {
    Semaphore sema = new Semaphore(0);
    try (ServerThread serverThread = new ServerThread() {

      @Override
      protected void handleConnection(final AFUNIXSocket sock) throws IOException {
        // Let's wait some time for a byte that never gets sent by the
        // client
        try (InputStream inputStream = sock.getInputStream()) {
          inputStream.read();
          sema.acquire();
        } catch (InterruptedException | IOException e) {
          // ignore
        }
      }
    }; AFUNIXSocket sock = connectToServer()) {
      assertTrue(sock.isConnected());
      assertFalse(sock.isClosed());
      try {
        sock.setSoTimeout((int) TimeUnit.SECONDS.toMillis(12));
      } catch (IOException e) {
        e.printStackTrace();
        throw e;
      } finally {
        sema.release();
      }
    }
  }

  @Test
  public void testSocketTimeoutExceptionRead() throws Exception {
    final File tempFile = newTempFile();
    final AFUNIXSocketAddress address = AFUNIXSocketAddress.of(tempFile);
    try (AFUNIXServerSocket server = AFUNIXServerSocket.bindOn(address);
        AFUNIXSocket client = AFUNIXSocket.connectTo(address)) {

      final AFUNIXSocket socket = server.accept();
      socket.setSoTimeout(500);
      byte[] buf = new byte[socket.getReceiveBufferSize()];
      InputStream in = socket.getInputStream();

      assertThrows(SocketTimeoutException.class, () -> {
        AssertUtil.ignoreValue(in.read(buf));
      });
    } finally {
      IOUtil.delete(tempFile);
    }
  }

  @Test
  public void testSocketTimeoutExceptionWrite() throws Exception {
    final File tempFile = newTempFile();
    final AFUNIXSocketAddress address = AFUNIXSocketAddress.of(tempFile);
    try (AFUNIXServerSocket server = AFUNIXServerSocket.bindOn(address);
        AFUNIXSocket client = AFUNIXSocket.connectTo(address)) {

      final AFUNIXSocket socket = server.accept();
      socket.setSoTimeout(500);
      byte[] buf = new byte[socket.getSendBufferSize()];
      OutputStream out = socket.getOutputStream();

      try {
        out.write(buf);
        out.write(buf);
      } catch (SocketTimeoutException e) {
        // expected but not guaranteed
      }
    } finally {
      IOUtil.delete(tempFile);
    }
  }
}

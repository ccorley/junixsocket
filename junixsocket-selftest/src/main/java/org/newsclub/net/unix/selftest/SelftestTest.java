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
package org.newsclub.net.unix.selftest;

import org.junit.jupiter.api.Test;

public class SelftestTest {
  @Test
  public void testHelloWorld() throws Exception {
  }

  @Test
  public void testFoo() throws Exception {
    // fail();
  }

  @Test
  public void testStdout() throws Exception {
    System.out.println("Some interrupting text");
  }

  @Test
  public void testBar() throws Exception {
  }
}

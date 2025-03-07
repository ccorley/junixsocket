# junixsocket

junixsocket is a Java/JNI library that allows the use of [Unix Domain Sockets](https://en.wikipedia.org/wiki/Unix_domain_socket) (AF_UNIX sockets) from Java.

## Why it's cool

* *junixsocket* is the most complete implementation of Unix Domain Sockets for the Java ecosystem.
* Comes with pre-built native libraries for several operating systems and platforms, including
  macOS, Linux, Windows, Solaris, FreeBSD, NetBSD, OpenBSD, DragonFlyBSD.
* Supports all Java versions since Java 7\*.
* Supports both the Java Socket API and NIO (`java.net.Socket`, `java.net.SocketChannel`, etc.)
* Supports streams and datagrams.
* Supports Remote Method Invocation (RMI) over AF_UNIX.
* Supports JDBC database connectors (connect to a local database server via Unix sockets).
    * Generic *AFUNIXSocketFactory* for databases like PostgreSQL
    * Custom socket factory for MySQL Connector/J, as [recommended by Oracle](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-unix-socket.html)
* Supports [peer credentials](https://kohlschutter.github.io/junixsocket/peercreds.html).
* Supports sending and receiving [file descriptors](https://kohlschutter.github.io/junixsocket/filedescriptors.html).
* Supports the abstract namespace on Linux.
* Supports socketpair, and instantiating socket classes from file descriptors.
* Supports HTTP over unix socket (using [NanoHTTPD](https://github.com/NanoHttpd/nanohttpd)).
* Supports JPMS/Jigsaw modules. Modularized so you can install only what you need.
* Provides a selftest package with 100+ tests to ensure compatibility with any target platform.
* Apache 2.0 licensed.

`*` (Tested up to Java 17; Java 7 support is limited to core functionality, no RMI).

## Quick links

 * [Project website](https://kohlschutter.github.io/junixsocket/)
 * [Github project](https://github.com/kohlschutter/junixsocket/)
 * [Changelog](https://kohlschutter.github.io/junixsocket/changelog.html)
 * [Getting started](https://kohlschutter.github.io/junixsocket/quickstart.html)
 * [Demo code](https://kohlschutter.github.io/junixsocket/demo.html) ([Java source](https://kohlschutter.github.io/junixsocket/junixsocket-demo/xref/index.html))
    - Sockets (`org.newsclub.net.unix.demo`)
    - RMI over Unix Sockets (`org.newsclub.net.unix.demo.rmi` and `org.newsclub.net.unix.demo.rmi.services`)
    - MySQL over Unix Sockets  (`org.newsclub.net.mysql.demo`)

  * API Javadocs
    - [The core (common) API](https://kohlschutter.github.io/junixsocket/junixsocket-common/apidocs/org.newsclub.net.unix/org/newsclub/net/unix/package-summary.html)
    - [The RMI-over-Unix-Socket API](https://kohlschutter.github.io/junixsocket/junixsocket-rmi/apidocs/org.newsclub.net.unix.rmi/org/newsclub/net/unix/rmi/package-summary.html)
    
  * [Unix Domain Socket Reference](https://kohlschutter.github.io/junixsocket/unixsockets.html)

## Licensing

junixsocket is released under the Apache 2.0 License.

Commercial support is available through [Kohlschütter Search Intelligence](http://www.kohlschutter.com/).

## Self-test

To verify that the software works as expected on your platform, you can run the selftest program,
which is located in the "junixsocket-dist" distribution package:

```
java -jar junixsocket-selftest-VERSION-jar-with-dependencies.jar 
```

(with VERSION being the corresponding junixsocket version).

## Maven dependency

To include the core junixsocket functionality in your project, add the following Maven dependency

```
<dependency>
  <groupId>com.kohlschutter.junixsocket</groupId>
  <artifactId>junixsocket-core</artifactId>
  <version>2.4.1</version>
  <type>pom</type>
</dependency>
```

For more, optional packages (RMI, MySQL, server, etc.) see [here](https://kohlschutter.github.io/junixsocket/dependency.html)

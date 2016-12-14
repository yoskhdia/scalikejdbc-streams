# ScalikeJDBC-streams

[![Release](https://jitpack.io/v/yoskhdia/scalikejdbc-streams.svg)](https://jitpack.io/#yoskhdia/scalikejdbc-streams)

It is assumed to be used in batch application.
In JDBC, you need to keep connections constantly while subscribing to streaming. Therefore, please reconsider the connection pool setting and always be careful not to exhaust the connection.


## Getting Started

supports Scala 2.11.x, 2.12.x

```
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies ++= Seq(
  "com.github.yoskhdia" %% "scalikejdbc-streams" % "<latest version>",
  "com.h2database"  %  "h2"                 % "1.4.+",
  "ch.qos.logback"  %  "logback-classic"    % "1.1.+"
)
```

For the full document of ScalikeJDBC please refer to the following
[http://scalikejdbc.org/](http://scalikejdbc.org/)


## Example

```scala
import scalikejdbc._
import scalikejdbc.streams._

// We recommend that you prepare a ThreadPoolExecutor that generates daemon threads
implicit val executor = AsyncExecutor(scala.concurrent.ExecutionContext.global)

val publisher = DB stream {
  sql"select id from users".map(r => r.int("id")).cursor
}
publisher.subscribe(???)
```

Please refer to the document on usage of ScalikeJDBC such as connection pool initialization
http://scalikejdbc.org/documentation/configuration.html


# Note

For MySQL, PostgreSQL, and others, several settings are required to enable CURSOR (Streaming).
In ScalikeJDBC-streams, if driverName can be identified, this setting is automatically enabled.
Currently only MySQL and PostgreSQL are supported.

```scala
import scalikejdbc._
val poolSettings = ConnectionPoolSettings(driverName = "com.mysql.jdbc.Driver")
Class.forName(poolSettings.driverName)
ConnectionPool.singleton("jdbc:mysql://127.0.0.1/scalikejdbc_streams_test", "user", "pass", poolSettings)
```

With scalikejdbc-config you can easily delegate these settings to application.conf.
http://scalikejdbc.org/documentation/configuration.html#scalikejdbc-config

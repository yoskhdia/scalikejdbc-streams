# ScalikeJDBC-streams

[![Release](https://jitpack.io/v/yoskhdia/scalikejdbc-streams.svg)](https://jitpack.io/#yoskhdia/scalikejdbc-streams)

バッチアプリケーションでの利用を想定しています。
JDBCでは、ストリーミングをサブスクライブしている間、常にコネクションを保持し続けている必要があります。そのため、コネクションプールの設定を見直すとともに、コネクションが枯渇しないよう常に注意してください。


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

ScalikeJDBCのフルドキュメントは以下を参照してください。
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

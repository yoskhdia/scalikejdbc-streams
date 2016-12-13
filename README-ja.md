# ScalikeJDBC-streams

[![Release](https://jitpack.io/v/yoskhdia/scalikejdbc-streams.svg)](https://jitpack.io/#yoskhdia/scalikejdbc-streams)

バッチアプリケーションでの利用を想定しています。
JDBCでは、ストリーミングをサブスクライブしている間、常にコネクションを保持し続けている必要があります。そのため、コネクションプールの設定を見直すとともに、コネクションが枯渇しないよう常に注意してください。


## Getting Started

supports Scala 2.11.x, 2.12.x

```
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "com.github.yoskhdia" %% "scalikejdbc-streams" % <<check the latest version from jitpack.>>
```

## Example

```scala
import scalikejdbc._
import scalikejdbc.streams._

val publisher = DB stream {
  sql"select id from users".map(r => r.int("id")).cursor
}
publisher.subscribe(???)
```

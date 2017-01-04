# ScalikeJDBC-streams

[![Release](https://jitpack.io/v/yoskhdia/scalikejdbc-streams.svg)](https://jitpack.io/#yoskhdia/scalikejdbc-streams)

**このライブラリは、[ScalikeJDBC公式に取り込まれました](https://github.com/scalikejdbc/scalikejdbc/tree/master/scalikejdbc-streams)。このGitリポジトリではメンテナンスされませんので公式のライブラリを使用するようにしてください。**

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

コネクションプールの初期化などScalikeJDBCに関する使用法はドキュメントを参照してください
http://scalikejdbc.org/documentation/configuration.html


# Note

MySQLやPostgreSQLなどではCURSOR(Streaming)を有効にするには、いくつかの設定が必要です。
ScalikeJDBC-streamsではdriverNameが識別できる場合には、この設定を自動的に有効にします。
現在はMySQLおよびPostgreSQLのみサポートしています。

```scala
import scalikejdbc._
val poolSettings = ConnectionPoolSettings(driverName = "com.mysql.jdbc.Driver")
Class.forName(poolSettings.driverName)
ConnectionPool.singleton("jdbc:mysql://127.0.0.1/scalikejdbc_streams_test", "user", "pass", poolSettings)
```

scalikejdbc-configを使用すると、これらの設定をapplication.confに簡単に委譲することができます。
http://scalikejdbc.org/documentation/configuration.html#scalikejdbc-config


# ReactiveStreams TCK

ReactiveStreamsではTCKが公開されています。
https://github.com/reactive-streams/reactive-streams-jvm/tree/v1.0.0/tck
ScalikeJDBC-streamsはPublisherVerificationをパスしています。
ただし、[Structure of the TCK](https://github.com/reactive-streams/reactive-streams-jvm/tree/v1.0.0/tck#structure-of-the-tck)項にも記載がありますが、
いくつかのテストは自動テストで（意味のある）確認をすることができません。
もし、良いアイディアがあればPullRequestを送ってください！

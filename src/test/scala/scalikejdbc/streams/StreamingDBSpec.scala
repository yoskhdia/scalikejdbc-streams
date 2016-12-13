package scalikejdbc.streams

import org.scalatest._
import scalikejdbc._

import scala.concurrent.ExecutionContext.Implicits.global

class StreamingDBSpec extends FlatSpec with Matchers {

  "DB.stream" should "create DatabasePublisher" in {
    val publisher = DB stream {
      sql"select id from users".map(r => r.int("id")).cursor
    }
    publisher shouldBe a[DatabasePublisher[_]]
  }

}

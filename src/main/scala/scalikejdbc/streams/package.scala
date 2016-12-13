package scalikejdbc

import scalikejdbc.GeneralizedTypeConstraintsForWithExtractor.=:=

import scala.concurrent.ExecutionContext

package object streams { self =>
  final val DefaultFetchSize: Int = 1000

  def stream[A, E <: WithExtractor](
    sql: StreamingSQL[A, E],
    dbName: Any = ConnectionPool.DEFAULT_NAME
  )(
    implicit
    executor: StreamingDB.Executor,
    context: DB.CPContext = DB.NoCPContext,
    settings: SettingsProvider = SettingsProvider.default
  ): DatabasePublisher[A] = {
    val db = StreamingDB(dbName, executor)
    db.stream(sql)
  }

  implicit class StreamingDBConverter(val db: DB.type) extends AnyVal {

    def stream[A, E <: WithExtractor](sql: StreamingSQL[A, E])(implicit
      executor: StreamingDB.Executor,
      context: DB.CPContext = DB.NoCPContext,
      settings: SettingsProvider = SettingsProvider.default): DatabasePublisher[A] = {

      self.stream(sql)
    }
  }

  implicit class StreamingNamedDBConverter(val db: NamedDB) extends AnyVal {

    def stream[A, E <: WithExtractor](sql: StreamingSQL[A, E])(implicit
      executor: StreamingDB.Executor,
      context: DB.CPContext = DB.NoCPContext,
      settings: SettingsProvider = SettingsProvider.default): DatabasePublisher[A] = {

      self.stream(sql, db.name)
    }
  }

  implicit class StreamingSQLConverter[A, E <: WithExtractor](val sql: SQL[A, E]) extends AnyVal {
    def cursorWith(fetchSize: Int = DefaultFetchSize)(implicit hasExtractor: SQL[A, E]#ThisSQL =:= SQL[A, E]#SQLWithExtractor): StreamingSQL[A, E] = {
      new CursorStreamingSQL[A, E](sql, fetchSize)
    }

    def cursor()(implicit hasExtractor: SQL[A, E]#ThisSQL =:= SQL[A, E]#SQLWithExtractor): StreamingSQL[A, E] = cursorWith()
  }

}

package scalikejdbc.streams

import scalikejdbc.{ DBSession, SQL, WithExtractor, WrappedResultSet }

trait StreamingSQL[A, E <: WithExtractor] {
  private[streams] def underlying: SQL[A, E]

  def statement: String

  def parameters: Seq[Any]

  def extractor: WrappedResultSet => A

  protected[streams] def setSessionAttributes(session: DBSession): DBSession = {
    session
      .fetchSize(underlying.fetchSize)
      .tags(underlying.tags: _*)
      .queryTimeout(underlying.queryTimeout)
  }
}

final class CursorStreamingSQL[A, E <: WithExtractor](s: SQL[A, E], fetchSize: Int) extends StreamingSQL[A, E] {
  // clone the SQL instance so that it is not changed
  private[streams] val underlying = (new SQL[A, E](s.statement, s.rawParameters)(s.extractor) {}).fetchSize(fetchSize)

  override def statement: String = underlying.statement

  override def parameters: Seq[Any] = underlying.parameters

  override def extractor: (WrappedResultSet) => A = underlying.extractor
}

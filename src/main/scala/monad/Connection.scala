package monad

import scala.util.Try

object Connection {


  type Payload = String
  //Definition
  case class Connection(host:String,port:String)
  val config = Map(
    "host" -> "localhost",
    "port" -> "4040"
  )
  trait HttpService[M[_]]{
    def getConnection(cnf:Map[String,String]):M[Connection]
    def issueRequest(connection:Connection,payload:Payload):M[Payload]
  }


  val optionHttpService = new HttpService[Option] {
    override def getConnection(cnf: Map[Payload, Payload]): Option[Connection] = for{
      host<-cnf.get("host")
      port <- cnf.get("port")
    }yield Connection(host,port)

    override def issueRequest(connection: Connection, payload: Payload): Option[Payload] = payload match {
      case payload if payload.length<20 => Some(payload)
      case _ => None
    }
  }
  val tryHttpService = new HttpService[Try] {
    def opToTry[A](op:Option[A]):Try[A] = Try{op.get}
    override def getConnection(cnf: Map[Payload, Payload]): Try[Connection] = for{
      host<-opToTry(cnf.get("host"))
      port <- opToTry(cnf.get("port"))
    }yield Connection(host,port)

    override def issueRequest(connection: Connection, payload: Payload): Try[Payload] = payload match {
      case payload if payload.length<20 => Try(payload)
      case _ => Try{throw new Exception("Payload too big")}
    }
  }

  def main(args: Array[String]): Unit = {
    val maybeRequest = for{
      connection <- optionHttpService.getConnection(config)
      payload <- optionHttpService.issueRequest(connection,(1 to 20).map(_=>"!").mkString(""))
    }yield payload
    val tryRequest = for{
      connection <- tryHttpService.getConnection(config)
      payload <- tryHttpService.issueRequest(connection,(1 to 20).map(_=>"!").mkString(""))
    }yield payload


    println(maybeRequest)
    println(tryRequest)
  }

}

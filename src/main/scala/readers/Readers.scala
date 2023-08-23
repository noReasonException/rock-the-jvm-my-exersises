package readers

import cats.data.Reader


object Readers {

  case class Configuration(dbUsername:String,dbPassword:String,host:String,port:Int,nThreads:Int,emailReplyTo:String)
  case class DbConnection(usernname:String,password:String){
    def getOrderStatus(orderId:Long):String = "Dispached"
    def getLastOrderId(username:String):Long = 542643
  }
  case class EmailService(emailReplyTo:String){
    def sendEmail(address:String,content:String) = print(s"Sending email to $address with content \n$content")
  }


  val dbReader:Reader[Configuration,DbConnection] = Reader.apply(conf=>DbConnection(conf.dbUsername,conf.dbPassword))
  val emailReader:Reader[Configuration,EmailService] = Reader.apply(conf=>EmailService(conf.emailReplyTo))
  implicit val conf: Configuration = Configuration(
    dbUsername = "root",
    dbPassword = "toor",
    host = "192.168.1.2",
    port = 27017,
    nThreads = 8,
    emailReplyTo = "example@company.com")

  /**
   * There is a pattern in cats, we represent a Reader[A,B] as ReaderT[Id,A,B]...
   * @param username
   * @return
   */


  def getLastOrderStatus(username:String)(implicit conf:Configuration):String = {
    val v = for{
      usersLastOrderId <- dbReader.map(_.getLastOrderId(username))
      userLastOrderStatus <- dbReader.map(_.getOrderStatus(usersLastOrderId))
    }yield  userLastOrderStatus
     v.apply(conf)
  }


  def emailUser(username:String,userEmail:String)={
    //fetch the status of the last order
    //email them using the emailService ('your order has status')
    for{
      usersLastOrderId <- dbReader.map(_.getLastOrderId(username))
      userLastOrderStatus <- dbReader.map(_.getOrderStatus(usersLastOrderId))
      emailUser <- emailReader.map(_.sendEmail(userEmail,s"Hi $username \nYour order $usersLastOrderId has status $userLastOrderStatus\nRegards \nThe Company Team"))
    }yield  userLastOrderStatus
  }





  def main(args: Array[String]): Unit = {
    emailUser("Stefanos","stefannstefanou@gmail.com").apply(conf)
  }
}

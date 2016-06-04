object POJO {

  case class IpInfo(ip: String, country: Option[String], city: Option[String], latitude: Option[Double], longitude: Option[Double])

  case class IpPairSummary(distance: Option[Double], ip1Info: IpInfo, ip2Info: IpInfo)

  case class IpPairSummaryRequest(ip1: String, ip2: String)

  case class JustInfo(ip: String, country: Option[String])

  case class JustRequest(t: String, w: String, c: String)

  case class WorkbenchInfo(caption: String, text: Seq[Either[String, JustRequest]])


}

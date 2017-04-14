package model


import java.util.Date
import java.text.SimpleDateFormat


case class Reading (val userId : String, val trajectoryId: String, val lat: Float, val lon : Float, val alt: Float, val date : String, val time : String) extends java.io.Serializable {

	private val ts = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss").parse(date + "," + time).getTime

	def toTuple  = {
		(userId + "|" + ts.toString, trajectoryId, lat, lon, alt, ts, userId)
	}
}

//val s = "000,20081023025304,39.984702,116.318417,0,492,39744.1201851852,2008-10-23,02:53:04"
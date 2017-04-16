package model


import java.util.Date
import java.text.SimpleDateFormat


case class Reading (val userId : String, val trajectoryId: String, val lat: Float, val lon : Float, val alt: Float, val date : String, val time : String) extends java.io.Serializable {

	val ts = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss").parse(date + "," + time).getTime

	def toTuple  = {
		(userId + "|" + (Long.MaxValue - ts).toString, trajectoryId, lat, lon, alt, ts, userId)
	}
}
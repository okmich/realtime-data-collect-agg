package model


import java.util.Date
import org.joda.time.Duration



case class Trajectory (val plat: Float, val plon: Float, val lat: Float,  val lon: Float, val pts: Long, val ts: Long, val trajId : String) extends java.io.Serializable {

	
	val dist = distance(plat, plon, lat, lon)
	val timeDiff = timeDiffSec(pts, ts)

	def distance(xlat: Float, xlon: Float, ylat: Float, ylon: Float, el1: Float = 0f, el2: Float = 0f) : Double = {
	    val R =  6371 // Radius of the earth
	    val latDistance = Math.toRadians(ylat - xlat)
	    val lonDistance = Math.toRadians(ylon - xlon)
	    val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
	                 Math.cos(Math.toRadians(xlat)) * Math.cos(Math.toRadians(ylat)) *
	                 Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)

	    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
	    var distance = R * c * 1000 // convert to meters
	    val height = el1 - el2

	    distance = Math.pow(distance, 2) + Math.pow(height, 2)
	    Math.sqrt(distance)
	}


	def timeDiffSec(ts1: Long, ts2: Long) : Long = {
		new Duration(ts1,ts2).toStandardSeconds.getSeconds()
	}

	def toTuple  = {
		(trajId + "|" + (Long.MaxValue - ts).toString, plat, plon, lat, lon, dist, pts, ts, timeDiff, trajId)
	}
}
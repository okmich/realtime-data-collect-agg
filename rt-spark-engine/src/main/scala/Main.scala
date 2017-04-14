
import java.io.Serializable

import kafka.serializer.StringDecoder

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import org.apache.spark.streaming._
import org.apache.spark.streaming.api.java._
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

import it.nerdammer.spark.hbase._

import model.Reading

import integration.KafkaMessageProducer

object Main {

	def main(args: Array[String]) : Unit  = {
		if (args.length < 1){
			println("General arguments: <consume_topic> <produce_topic> <broker_list> <zk_url>")
			System.exit(-1)
		}
		
		val sparkConf = new SparkConf().setAppName("Sensor Data Stream Processor")
		val streamingCtx = new StreamingContext(sparkConf, Milliseconds(250))

		val topics = Set("test_topic")
		val brokers = "192.168.8.120:9092"
		val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
		val data = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](streamingCtx, kafkaParams, topics)

		// Get the lines, split them into words, count the words and print
		data.map(_._2).foreachRDD(processRDD(_))
		

		// Start the computation
	    streamingCtx.start()
	    streamingCtx.awaitTermination()
	}

	/**
	 * 
	 * process the rdd passed as parameter.
	 * processing involves
	 *	- convert to a data object
	 *	- send the data to kafka 
	 *	- save the data to hbase cf main
	 *
	 * @param rdd
	 */
	def processRDD(rdd: RDD[String]) : Unit ={
		rdd foreach ((r: String) => println("======================== " + r)) 
	}


	/**
	 * 
	 * send the items of this rdd to a kafka topic
	 * @param rdd
	 */
	def sendToKafka(rdd: RDD[Reading]) : Unit = {
		
	}

	/**
	 * 
	 * save the items of this rdd to a hbase table
	 * @param rdd
	 */
	def saveReadingToHBase(rdd: RDD[Reading]) : Unit = {
		rdd.map(r => r.toTuple)
		 	.toHBaseTable("mvment")
		    .toColumns("trajId", "lat", "lon", "alt", "ts", "userId")
		    .inColumnFamily("main")
		    .save()
	}
}



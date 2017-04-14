package integration

import java.util.{Date, Properties}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class KafkaMessageProducer(val brokers: String, val topic : String) extends java.io.Serializable {
	
	private val props = new Properties()
	props.put("bootstrap.servers", this.brokers)
	props.put("client.id", "SparkKafkaMessageProducer")
	props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
	props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  	private val kafkaProducer = new KafkaProducer[String, String](props)


  	def sendMessage(data : String) : Unit = {
  		val record = new ProducerRecord[String, String](topic, Long.MaxValue.toString, data)
  		kafkaProducer.send(record)
  		kafkaProducer.flush()
  	}
}
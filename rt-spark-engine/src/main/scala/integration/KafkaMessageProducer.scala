package integration

import java.util.{Date, Properties}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class KafkaMessageProducer(val brokers: String, val topic : String) extends java.io.Serializable {
	
	private val props = new Properties()
	props.put("bootstrap.servers", this.brokers)
	props.put("client.id", "SparkKafkaMessageProducer")
	props.put("acks", "all");
	props.put("retries", new Integer(0));
	props.put("batch.size", new Integer(16384));
	props.put("linger.ms", new Integer(1));
	props.put("buffer.memory", new Integer(33554432));
	props.put("metadata.fetch.timeout.ms", new Integer(1000));
	props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
	props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  	private val kafkaProducer = new KafkaProducer[String, String](props)


  	def sendMessage(data : String) : Unit = {
  		val record = new ProducerRecord[String, String](topic, Long.MaxValue.toString, data)
  		kafkaProducer.send(record)
  		//kafkaProducer.flush()
  	}
}

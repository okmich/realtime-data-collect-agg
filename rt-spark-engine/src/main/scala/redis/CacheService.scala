package redis

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

class CacheService extends java.io.Serializable {

	private val jedis : Jedis  = new Jedis(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT)

    def getAndSet(key : String, value: String)  = {
		//the redis endpoint object
		// val jedis : Jedis = new Jedis("127.0.0.1", 6379)

		val retValue = jedis.get(key)
		this.jedis.set(key, value)

		retValue
    }

	def disconnect : Unit = {
		this.jedis.disconnect
	}
}
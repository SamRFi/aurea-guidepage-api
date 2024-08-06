package apartments.aurea.guidepage_api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class RedisIntegrationTest {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    fun testRedisConnection() {
        val key = "testKey"
        val value = "testValue"

        redisTemplate.opsForValue().set(key, value)
        val retrievedValue = redisTemplate.opsForValue().get(key)

        assertThat(retrievedValue).isEqualTo(value)
    }
}
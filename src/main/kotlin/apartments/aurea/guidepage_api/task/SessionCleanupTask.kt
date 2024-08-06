package apartments.aurea.guidepage_api.task

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SessionCleanupTask(private val redisTemplate: RedisTemplate<String, Any>) {

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    fun cleanupExpiredSessions() {
        val keys = redisTemplate.keys("*")
        keys.forEach { key ->
            if (redisTemplate.getExpire(key) == 0L) {
                redisTemplate.delete(key)
            }
        }
    }
}
package apartments.aurea.guidepage_api.controller

import apartments.aurea.guidepage_api.service.UserService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.time.Duration

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        val user = userService.validateUser(request.accessCode)
        return if (user != null) {
            val sessionId = UUID.randomUUID().toString()
            // Store session in Redis
            redisTemplate.opsForValue().set(sessionId, user.id, Duration.ofMinutes(30))
            ResponseEntity.ok(mapOf("sessionId" to sessionId))
        } else {
            ResponseEntity.badRequest().body("Invalid access code")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Session-Id") sessionId: String): ResponseEntity<Any> {
        redisTemplate.delete(sessionId)
        return ResponseEntity.ok("Logged out successfully")
    }
}

data class LoginRequest(val accessCode: String)
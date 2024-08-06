package apartments.aurea.guidepage_api.controller

import apartments.aurea.guidepage_api.model.Home
import apartments.aurea.guidepage_api.service.HomeService
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/homes")
class HomeController(
    private val homeService: HomeService,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val logger = LoggerFactory.getLogger(HomeController::class.java)

    @GetMapping
    fun getAllHomes(@RequestHeader("Session-Id") sessionId: String): ResponseEntity<List<Home>> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            ResponseEntity.ok(homeService.getHomesByUserId(userId))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @GetMapping("/{id}")
    fun getHome(@RequestHeader("Session-Id") sessionId: String, @PathVariable id: String): ResponseEntity<Home> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            val home = homeService.getHomeById(id)
            if (home != null && home.userId == userId) {
                ResponseEntity.ok(home)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @GetMapping("/user/{userId}")
    fun getHomesByUserId(@RequestHeader("Session-Id") sessionId: String, @PathVariable userId: String): ResponseEntity<List<Home>> {
        val authenticatedUserId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (authenticatedUserId != null && authenticatedUserId == userId) {
            ResponseEntity.ok(homeService.getHomesByUserId(userId))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createHome(@RequestHeader("Session-Id") sessionId: String, @RequestBody home: Home): ResponseEntity<Home> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        logger.info("Session ID: $sessionId, User ID: $userId")
        return if (userId != null) {
            val createdHome = homeService.createHome(home.copy(userId = userId))
            ResponseEntity.ok(createdHome)
        } else {
            logger.warn("User ID not found for session: $sessionId")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PutMapping("/{id}")
    fun updateHome(@RequestHeader("Session-Id") sessionId: String, @PathVariable id: String, @RequestBody home: Home): ResponseEntity<Home> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            val existingHome = homeService.getHomeById(id)
            if (existingHome != null && existingHome.userId == userId) {
                val updatedHome = homeService.updateHome(id, home.copy(userId = userId))
                if (updatedHome != null) {
                    ResponseEntity.ok(updatedHome)
                } else {
                    ResponseEntity.notFound().build()
                }
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteHome(@RequestHeader("Session-Id") sessionId: String, @PathVariable id: String): ResponseEntity<Unit> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            val existingHome = homeService.getHomeById(id)
            if (existingHome != null && existingHome.userId == userId) {
                homeService.deleteHome(id)
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}
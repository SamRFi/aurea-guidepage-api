package apartments.aurea.guidepage_api.controller

import apartments.aurea.guidepage_api.model.GuidePage
import apartments.aurea.guidepage_api.service.GuidePageService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/guidepages")
class GuidePageController(
    private val guidePageService: GuidePageService,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    @GetMapping
    fun getAllGuidePages(@RequestHeader("Session-Id") sessionId: String): ResponseEntity<List<GuidePage>> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            ResponseEntity.ok(guidePageService.getAllGuidePages())
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @GetMapping("/{id}")
    fun getGuidePage(@RequestHeader("Session-Id") sessionId: String, @PathVariable id: String): ResponseEntity<GuidePage> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            val guidePage = guidePageService.getGuidePageById(id)
            if (guidePage != null) {
                ResponseEntity.ok(guidePage)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @GetMapping("/home/{homeId}")
    fun getGuidePageByHomeId(@RequestHeader("Session-Id") sessionId: String, @PathVariable homeId: String): ResponseEntity<GuidePage> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            val guidePage = guidePageService.getGuidePageByHomeId(homeId)
            if (guidePage != null) {
                ResponseEntity.ok(guidePage)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createGuidePage(@RequestHeader("Session-Id") sessionId: String, @RequestBody guidePage: GuidePage): ResponseEntity<GuidePage> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            val createdGuidePage = guidePageService.createGuidePage(guidePage)
            ResponseEntity.ok(createdGuidePage)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PutMapping("/{id}")
    fun updateGuidePage(@RequestHeader("Session-Id") sessionId: String, @PathVariable id: String, @RequestBody guidePage: GuidePage): ResponseEntity<GuidePage> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            val updatedGuidePage = guidePageService.updateGuidePage(id, guidePage)
            if (updatedGuidePage != null) {
                ResponseEntity.ok(updatedGuidePage)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGuidePage(@RequestHeader("Session-Id") sessionId: String, @PathVariable id: String): ResponseEntity<Unit> {
        val userId = redisTemplate.opsForValue().get(sessionId) as String?
        return if (userId != null) {
            guidePageService.deleteGuidePage(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}

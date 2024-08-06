package apartments.aurea.guidepage_api.controller

import apartments.aurea.guidepage_api.model.Home
import apartments.aurea.guidepage_api.service.HomeService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/homes")
class HomeController(
    private val homeService: HomeService
) {
    private val logger = LoggerFactory.getLogger(HomeController::class.java)

    @GetMapping
    fun getAllHomes(@RequestAttribute("userId") userId: String): ResponseEntity<List<Home>> {
        return ResponseEntity.ok(homeService.getHomesByUserId(userId))
    }

    @GetMapping("/{id}")
    fun getHome(@RequestAttribute("userId") userId: String, @PathVariable id: String): ResponseEntity<Home> {
        val home = homeService.getHomeById(id)
        return if (home != null && home.userId == userId) {
            ResponseEntity.ok(home)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/user/{userId}")
    fun getHomesByUserId(@RequestAttribute("userId") authenticatedUserId: String, @PathVariable userId: String): ResponseEntity<List<Home>> {
        return if (authenticatedUserId == userId) {
            ResponseEntity.ok(homeService.getHomesByUserId(userId))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createHome(@RequestAttribute("userId") userId: String, @RequestBody home: Home): ResponseEntity<Home> {
        val createdHome = homeService.createHome(home.copy(userId = userId))
        return ResponseEntity.ok(createdHome)
    }

    @PutMapping("/{id}")
    fun updateHome(@RequestAttribute("userId") userId: String, @PathVariable id: String, @RequestBody home: Home): ResponseEntity<Home> {
        val existingHome = homeService.getHomeById(id)
        return if (existingHome != null && existingHome.userId == userId) {
            val updatedHome = homeService.updateHome(id, home.copy(userId = userId))
            if (updatedHome != null) {
                ResponseEntity.ok(updatedHome)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteHome(@RequestAttribute("userId") userId: String, @PathVariable id: String): ResponseEntity<Unit> {
        val existingHome = homeService.getHomeById(id)
        return if (existingHome != null && existingHome.userId == userId) {
            homeService.deleteHome(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

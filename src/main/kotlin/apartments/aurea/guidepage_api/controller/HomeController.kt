package apartments.aurea.guidepage_api.controller

import apartments.aurea.guidepage_api.model.Home
import apartments.aurea.guidepage_api.service.HomeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/homes")
class HomeController(private val homeService: HomeService) {

    @GetMapping
    fun getAllHomes(): List<Home> = homeService.getAllHomes()

    @GetMapping("/{id}")
    fun getHome(@PathVariable id: String): ResponseEntity<Home> {
        val home = homeService.getHomeById(id)
        return if (home != null) ResponseEntity.ok(home) else ResponseEntity.notFound().build()
    }

    @GetMapping("/user/{userId}")
    fun getHomesByUserId(@PathVariable userId: String): List<Home> = homeService.getHomesByUserId(userId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createHome(@RequestBody home: Home): Home = homeService.createHome(home)

    @PutMapping("/{id}")
    fun updateHome(@PathVariable id: String, @RequestBody home: Home): ResponseEntity<Home> {
        val updatedHome = homeService.updateHome(id, home)
        return if (updatedHome != null) ResponseEntity.ok(updatedHome) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteHome(@PathVariable id: String) = homeService.deleteHome(id)
}
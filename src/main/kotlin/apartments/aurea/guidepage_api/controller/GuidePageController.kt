package apartments.aurea.guidepage_api.controller

import apartments.aurea.guidepage_api.model.GuidePage
import apartments.aurea.guidepage_api.service.GuidePageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/guidepages")
class GuidePageController(private val guidePageService: GuidePageService) {

    @GetMapping
    fun getAllGuidePages(): List<GuidePage> = guidePageService.getAllGuidePages()

    @GetMapping("/{id}")
    fun getGuidePage(@PathVariable id: String): ResponseEntity<GuidePage> {
        val guidePage = guidePageService.getGuidePageById(id)
        return if (guidePage != null) ResponseEntity.ok(guidePage) else ResponseEntity.notFound().build()
    }

    @GetMapping("/home/{homeId}")
    fun getGuidePageByHomeId(@PathVariable homeId: String): ResponseEntity<GuidePage> {
        val guidePage = guidePageService.getGuidePageByHomeId(homeId)
        return if (guidePage != null) ResponseEntity.ok(guidePage) else ResponseEntity.notFound().build()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createGuidePage(@RequestBody guidePage: GuidePage): GuidePage = guidePageService.createGuidePage(guidePage)

    @PutMapping("/{id}")
    fun updateGuidePage(@PathVariable id: String, @RequestBody guidePage: GuidePage): ResponseEntity<GuidePage> {
        val updatedGuidePage = guidePageService.updateGuidePage(id, guidePage)
        return if (updatedGuidePage != null) ResponseEntity.ok(updatedGuidePage) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGuidePage(@PathVariable id: String) = guidePageService.deleteGuidePage(id)
}
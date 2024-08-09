package apartments.aurea.guidepage_api.controller

import apartments.aurea.guidepage_api.service.S3Service
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/images")
class ImageController(private val s3Service: S3Service) {

    @PostMapping
    fun uploadImage(@RequestParam("image") image: MultipartFile): ResponseEntity<String> {
        val key = s3Service.uploadImage(image.inputStream, image.contentType ?: "application/octet-stream")
        return ResponseEntity.ok(key)
    }

    @GetMapping("/{key}")
    fun getImage(@PathVariable key: String): ResponseEntity<Any> {
        val imageUrl = s3Service.getImageUrl(key)
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapOf("url" to imageUrl.toString()))
    }
}
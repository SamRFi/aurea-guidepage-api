package apartments.aurea.guidepage_api.repository

import apartments.aurea.guidepage_api.model.Home
import org.springframework.data.mongodb.repository.MongoRepository

interface HomeRepository : MongoRepository<Home, String> {
    fun findByUserId(userId: String): List<Home>
}
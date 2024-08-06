package apartments.aurea.guidepage_api.repository

import apartments.aurea.guidepage_api.model.GuidePage
import org.springframework.data.mongodb.repository.MongoRepository

interface GuidePageRepository : MongoRepository<GuidePage, String> {
    fun findByHomeId(homeId: String): GuidePage?
}
package apartments.aurea.guidepage_api.repository

import apartments.aurea.guidepage_api.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByAccessCode(accessCode: String): User?
}
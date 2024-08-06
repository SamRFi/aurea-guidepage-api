package apartments.aurea.guidepage_api.repository

import apartments.aurea.guidepage_api.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User, String> {
    fun findByAccessCode(accessCode: String): User?
    override fun findById(id: String): Optional<User>
}
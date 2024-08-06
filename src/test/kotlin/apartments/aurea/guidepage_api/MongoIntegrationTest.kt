package apartments.aurea.guidepage_api

import apartments.aurea.guidepage_api.model.Home
import apartments.aurea.guidepage_api.repository.HomeRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class MongoIntegrationTest @Autowired constructor(
    private val mongoTemplate: MongoTemplate,
    private val homeRepository: HomeRepository
) {

    @Test
    fun `should connect to MongoDB and perform operations`() {
        // Given
        val home = Home(name = "Integration Test Home", address = "456 Integration St", userId = "user789")

        // When
        val savedHome = mongoTemplate.save(home)
        val retrievedHome = homeRepository.findById(savedHome.id!!)

        // Then
        assertThat(retrievedHome.isPresent).isTrue
        assertThat(retrievedHome.get()).isEqualTo(savedHome)

        // Cleanup
        mongoTemplate.remove(savedHome)
    }
}
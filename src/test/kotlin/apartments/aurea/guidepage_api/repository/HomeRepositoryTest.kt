package apartments.aurea.guidepage_api.repository

import apartments.aurea.guidepage_api.model.Home
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.assertj.core.api.Assertions.assertThat

@DataMongoTest
class HomeRepositoryTest @Autowired constructor(
    private val homeRepository: HomeRepository
) {

    @Test
    fun `should save and retrieve a home`() {
        // Given
        val home = Home(name = "Test Home", address = "123 Test St", userId = "user123")

        // When
        val savedHome = homeRepository.save(home)
        val retrievedHome = homeRepository.findById(savedHome.id!!)

        // Then
        assertThat(retrievedHome.isPresent).isTrue
        assertThat(retrievedHome.get()).isEqualTo(savedHome)
    }

    @Test
    fun `should find homes by userId`() {
        // Given
        val userId = "user456"
        val home1 = Home(name = "Home 1", address = "Address 1", userId = userId)
        val home2 = Home(name = "Home 2", address = "Address 2", userId = userId)
        homeRepository.saveAll(listOf(home1, home2))

        // When
        val foundHomes = homeRepository.findByUserId(userId)

        // Then
        assertThat(foundHomes).hasSize(2)
        assertThat(foundHomes.map { it.userId }).containsOnly(userId)
    }
}
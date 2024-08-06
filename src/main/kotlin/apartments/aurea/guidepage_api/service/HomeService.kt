package apartments.aurea.guidepage_api.service

import apartments.aurea.guidepage_api.model.Home
import apartments.aurea.guidepage_api.repository.HomeRepository
import org.springframework.stereotype.Service

@Service
class HomeService(private val homeRepository: HomeRepository) {

    fun getAllHomes(): List<Home> = homeRepository.findAll()

    fun getHomeById(id: String): Home? = homeRepository.findById(id).orElse(null)

    fun getHomesByUserId(userId: String): List<Home> = homeRepository.findByUserId(userId)

    fun createHome(home: Home): Home = homeRepository.save(home)

    fun updateHome(id: String, home: Home): Home? {
        return if (homeRepository.existsById(id)) {
            homeRepository.save(home.copy(id = id))
        } else null
    }

    fun deleteHome(id: String) {
        homeRepository.deleteById(id)
    }
}
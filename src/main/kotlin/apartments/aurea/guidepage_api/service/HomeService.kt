package apartments.aurea.guidepage_api.service

import apartments.aurea.guidepage_api.model.GuidePage
import apartments.aurea.guidepage_api.model.Home
import apartments.aurea.guidepage_api.repository.HomeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HomeService(
    private val homeRepository: HomeRepository,
    private val guidePageService: GuidePageService
) {

    fun getAllHomes(): List<Home> = homeRepository.findAll()

    fun getHomeById(id: String): Home? = homeRepository.findById(id).orElse(null)

    fun getHomesByUserId(userId: String): List<Home> = homeRepository.findByUserId(userId)

    @Transactional
    fun createHome(home: Home): Home {
        val createdHome = homeRepository.save(home)
        val guidePage = GuidePage(homeId = createdHome.id!!, sections = emptyList())
        guidePageService.createGuidePage(guidePage)
        return createdHome
    }

    fun updateHome(id: String, home: Home): Home? {
        return if (homeRepository.existsById(id)) {
            homeRepository.save(home.copy(id = id))
        } else null
    }

    fun deleteHome(id: String) {
        homeRepository.deleteById(id)
    }
}

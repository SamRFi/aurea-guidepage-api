package apartments.aurea.guidepage_api.service

import apartments.aurea.guidepage_api.model.GuidePage
import apartments.aurea.guidepage_api.repository.GuidePageRepository
import org.springframework.stereotype.Service

@Service
class GuidePageService(private val guidePageRepository: GuidePageRepository) {

    fun getAllGuidePages(): List<GuidePage> = guidePageRepository.findAll()

    fun getGuidePageById(id: String): GuidePage? = guidePageRepository.findById(id).orElse(null)

    fun getGuidePageByHomeId(homeId: String): GuidePage? = guidePageRepository.findByHomeId(homeId)

    fun createGuidePage(guidePage: GuidePage): GuidePage = guidePageRepository.save(guidePage)

    fun updateGuidePage(id: String, guidePage: GuidePage): GuidePage? {
        return if (guidePageRepository.existsById(id)) {
            guidePageRepository.save(guidePage.copy(id = id))
        } else null
    }

    fun deleteGuidePage(id: String) {
        guidePageRepository.deleteById(id)
    }
}
package apartments.aurea.guidepage_api.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "guide_pages")
data class GuidePage(
    @Id
    val id: String? = null,
    val homeId: String,
    val sections: List<Section>
)

data class Section(
    val title: String,
    val cards: List<Card>
)

data class Card(
    val title: String,
    val content: String,
    val icon: String? = null,
    val details: Map<String, String>? = null
)
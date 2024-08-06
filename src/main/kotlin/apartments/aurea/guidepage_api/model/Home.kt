package apartments.aurea.guidepage_api.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "homes")
data class Home(
    @Id
    val id: String? = null,
    val name: String,
    val address: String,
    val guidePageId: String? = null,
    val userId: String
)
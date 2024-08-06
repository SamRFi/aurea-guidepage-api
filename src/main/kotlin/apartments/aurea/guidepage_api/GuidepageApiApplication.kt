package apartments.aurea.guidepage_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class GuidepageApiApplication

fun main(args: Array<String>) {
	runApplication<GuidepageApiApplication>(*args)
}

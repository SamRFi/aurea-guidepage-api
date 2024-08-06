package apartments.aurea.guidepage_api.service

import apartments.aurea.guidepage_api.model.User
import apartments.aurea.guidepage_api.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(accessCode: String): UserDetails {
        val user = userRepository.findByAccessCode(accessCode)
            ?: throw UsernameNotFoundException("User not found")
        return org.springframework.security.core.userdetails.User
            .withUsername(user.id)
            .password(user.accessCode)
            .roles("USER")
            .build()
    }

    fun validateUser(accessCode: String): User? {
        return userRepository.findByAccessCode(accessCode)
    }

    fun findById(id: String): Optional<User> {
        return userRepository.findById(id)
    }
}
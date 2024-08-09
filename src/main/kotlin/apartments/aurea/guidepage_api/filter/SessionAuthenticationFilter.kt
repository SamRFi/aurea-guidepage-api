package apartments.aurea.guidepage_api.filter

import apartments.aurea.guidepage_api.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Duration

@Component
class SessionAuthenticationFilter(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val sessionId = request.getHeader("Session-Id")
        if (sessionId != null) {
            val userId = redisTemplate.opsForValue().get(sessionId) as String?
            if (userId != null) {
                val user = userService.findById(userId)
                if (user != null) {
                    val authentication = UsernamePasswordAuthenticationToken(user, null, listOf(SimpleGrantedAuthority("ROLE_USER")))
                    SecurityContextHolder.getContext().authentication = authentication
                    // Set the user ID in the request attributes
                    request.setAttribute("userId", userId)
                    // Renew session
                    redisTemplate.expire(sessionId, Duration.ofMinutes(30))
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}

package com.sosastudios.nativespringsession

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession

@SpringBootApplication
@EnableRedisWebSession
//@RegisterReflectionForBinding(OAuth2AuthorizationRequest::class, AuthorizationGrantType::class, OAuth2AuthorizationResponseType::class)
class NativeSpringSessionApplication {
	@Bean
	fun configureMine(
		httpSecurity: ServerHttpSecurity,
	): SecurityWebFilterChain {
		httpSecurity
			.authorizeExchange {
				it
					.pathMatchers("/**").authenticated()
			}
			.oauth2Login {  }
		return httpSecurity.build()
	}
}

fun main(args: Array<String>) {
	runApplication<NativeSpringSessionApplication>(*args)
}

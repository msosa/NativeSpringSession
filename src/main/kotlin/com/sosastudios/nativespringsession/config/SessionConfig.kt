package com.sosastudios.nativespringsession.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.beans.factory.BeanClassLoaderAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.security.jackson2.SecurityJackson2Modules

@Configuration
class SessionConfig : BeanClassLoaderAware {

	private val logger = org.slf4j.LoggerFactory.getLogger(SessionConfig::class.java)
	private lateinit var loader: ClassLoader


	@Bean
	fun springSessionDefaultRedisSerializer(): RedisSerializer<Any> {
			return GenericJackson2JsonRedisSerializer(objectMapper())
	}


	private fun objectMapper() = ObjectMapper().apply {
		val modules = SecurityJackson2Modules.getModules(loader)
		logger.info("Using SecurityJackson2Modules: {}", modules)
		registerModules(modules + kotlinModule())
	}


	override fun setBeanClassLoader(classLoader: ClassLoader) {
		this.loader = classLoader
	}

}
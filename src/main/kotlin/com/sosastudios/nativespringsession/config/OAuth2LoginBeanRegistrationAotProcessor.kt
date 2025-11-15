package com.sosastudios.nativespringsession.config


import org.springframework.aot.generate.GenerationContext
import org.springframework.aot.hint.BindingReflectionHintsRegistrar
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.TypeHint
import org.springframework.aot.hint.TypeReference
import org.springframework.beans.factory.aot.BeanRegistrationAotContribution
import org.springframework.beans.factory.aot.BeanRegistrationAotProcessor
import org.springframework.beans.factory.aot.BeanRegistrationCode
import org.springframework.beans.factory.support.RegisteredBean
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.jackson2.CoreJackson2Module
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.jackson2.OAuth2ClientJackson2Module
import org.springframework.security.oauth2.core.AbstractOAuth2Token
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.jackson2.WebJackson2Module
import org.springframework.security.web.savedrequest.DefaultSavedRequest
import org.springframework.security.web.savedrequest.SavedCookie
import org.springframework.security.web.server.csrf.DefaultCsrfToken
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository
import org.springframework.security.web.server.jackson2.WebServerJackson2Module
import org.springframework.util.ClassUtils
import java.util.*


class OAuth2LoginBeanRegistrationAotProcessor : BeanRegistrationAotProcessor {
	private var jacksonContributed = false

	override fun processAheadOfTime(registeredBean: RegisteredBean): BeanRegistrationAotContribution? {
		val isRedisSerializer = RedisSerializer::class.java
			.isAssignableFrom(registeredBean.beanClass)

		// @formatter:off
		if (jackson2Present && isRedisSerializer && !this.jacksonContributed) {
			val jacksonContribution =
				JacksonConfigurationBeanRegistrationAotContribution()
			this.jacksonContributed = true
			return jacksonContribution
		}

		// @formatter:on
		return null
	}

	private class JacksonConfigurationBeanRegistrationAotContribution

		: BeanRegistrationAotContribution {
		private val reflectionHintsRegistrar = BindingReflectionHintsRegistrar()

		override fun applyTo(generationContext: GenerationContext, beanRegistrationCode: BeanRegistrationCode) {
			registerHints(generationContext.runtimeHints)
		}

		fun registerHints(hints: RuntimeHints) {
			// Collections -> UnmodifiableSet, UnmodifiableList, UnmodifiableMap,
			// UnmodifiableRandomAccessList, etc.
			hints.reflection().registerType(Collections::class.java, MemberCategory.DECLARED_CLASSES)

			// HashSet
			hints.reflection()
				.registerType(
					HashSet::class.java, MemberCategory.DECLARED_FIELDS,
					MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS
				)

			hints.reflection()
				.registerTypes(
					listOf(
						TypeReference.of(AbstractAuthenticationToken::class.java),
						TypeReference.of(DefaultSavedRequest.Builder::class.java),
						TypeReference.of(WebAuthenticationDetails::class.java),
						TypeReference.of(UsernamePasswordAuthenticationToken::class.java), TypeReference.of(User::class.java),
						TypeReference.of(DefaultOidcUser::class.java), TypeReference.of(DefaultOAuth2User::class.java),
						TypeReference.of(OidcUserAuthority::class.java), TypeReference.of(OAuth2UserAuthority::class.java),
						TypeReference.of(SimpleGrantedAuthority::class.java), TypeReference.of(OidcIdToken::class.java),
						TypeReference.of(AbstractOAuth2Token::class.java), TypeReference.of(OidcUserInfo::class.java),
						TypeReference.of(OAuth2AuthorizationRequest::class.java),
						TypeReference.of("jakarta.servlet.http.Cookie"),
						TypeReference.of(SavedCookie::class.java),
						TypeReference.of(DefaultCsrfToken::class.java),
						TypeReference.of(WebSessionServerCsrfTokenRepository::class.java),
						TypeReference.of(AuthorizationGrantType::class.java),
						TypeReference.of(OAuth2AuthorizationResponseType::class.java),
						TypeReference.of(OAuth2AuthenticationToken::class.java)
					)
				) { builder: TypeHint.Builder ->
					builder.withMembers(
						MemberCategory.DECLARED_FIELDS,
						MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS
					)
				}

			// Jackson Modules
			hints.reflection()
				.registerTypes(
					listOf(
						TypeReference.of(CoreJackson2Module::class.java),
						TypeReference.of(WebJackson2Module::class.java),
						TypeReference.of(WebServerJackson2Module::class.java),
						TypeReference.of(OAuth2ClientJackson2Module::class.java)
					)
				) { builder: TypeHint.Builder ->
					builder.withMembers(
						MemberCategory.DECLARED_FIELDS,
						MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
						MemberCategory.INVOKE_DECLARED_METHODS
					)
				}

			// Jackson Mixins
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.jackson2.UnmodifiableSetMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.jackson2.UnmodifiableListMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.jackson2.UnmodifiableMapMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.web.jackson2.DefaultSavedRequestMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.web.jackson2.WebAuthenticationDetailsMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.jackson2.UsernamePasswordAuthenticationTokenMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.jackson2.UserMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.jackson2.SimpleGrantedAuthorityMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(), loadClass(
					"org.springframework.security.oauth2.client.jackson2.OAuth2AuthenticationTokenMixin"
				)
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(), loadClass(
					"org.springframework.security.oauth2.client.jackson2.OAuth2AuthorizationRequestMixin"
				)
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.oauth2.client.jackson2.DefaultOidcUserMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.oauth2.client.jackson2.DefaultOAuth2UserMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.oauth2.client.jackson2.OidcUserAuthorityMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.oauth2.client.jackson2.OAuth2UserAuthorityMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.oauth2.client.jackson2.OidcIdTokenMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.oauth2.client.jackson2.OidcUserInfoMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.web.jackson2.CookieMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.web.jackson2.SavedCookieMixin")
			)
			this.reflectionHintsRegistrar.registerReflectionHints(
				hints.reflection(),
				loadClass("org.springframework.security.web.server.jackson2.DefaultCsrfServerTokenMixin")
			)
		}

		companion object {
			private fun loadClass(className: String?): Class<*> {
				try {
					return Class.forName(className)
				} catch (ex: ClassNotFoundException) {
					throw RuntimeException(ex)
				}
			}
		}
	}

	companion object {
		private val jackson2Present: Boolean

		init {
			val classLoader: ClassLoader? = ClassUtils.getDefaultClassLoader()
			jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader)
					&& ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader)
		}
	}
}
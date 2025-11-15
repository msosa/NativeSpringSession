package com.sosastudios.nativespringsession.config

import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.aot.hint.TypeReference
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient

import org.springframework.security.oauth2.client.jackson2.OAuth2ClientJackson2Module
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistration.ClientSettings
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails.UserInfoEndpoint
import org.springframework.security.oauth2.core.AbstractOAuth2Token
import org.springframework.security.oauth2.core.AuthenticationMethod
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2RefreshToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.jackson2.WebJackson2Module
import org.springframework.security.web.server.jackson2.WebServerJackson2Module
import java.time.Instant

class SessionHints : RuntimeHintsRegistrar {
	override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
		listOf(
			TypeReference.of(OAuth2ClientJackson2Module::class.java),
			TypeReference.of(WebJackson2Module::class.java),
			TypeReference.of(WebServerJackson2Module::class.java),
			TypeReference.of("org.springframework.security.oauth2.core.AbstractOAuth2Token"),
			TypeReference.of("org.springframework.security.oauth2.client.jackson2.OAuth2AuthorizationRequestMixin"),
			TypeReference.of("org.springframework.security.oauth2.client.jackson2.OAuth2AuthorizationRequestDeserializer"),
			TypeReference.of("org.springframework.security.oauth2.core.OAuth2AccessToken"),
			TypeReference.of("org.springframework.security.oauth2.core.OAuth2AccessToken\$TokenType"),
			TypeReference.of("org.springframework.security.oauth2.core.AuthenticationMethod"),
			TypeReference.of("org.springframework.security.oauth2.core.ClientAuthenticationMethod"),
			TypeReference.of("org.springframework.security.oauth2.core.AuthorizationGrantType"),
			TypeReference.of("org.springframework.security.oauth2.core.OAuth2RefreshToken"),
			TypeReference.of("org.springframework.security.oauth2.core.OAuth2AuthenticationException"),
			TypeReference.of("org.springframework.security.oauth2.core.user.OAuth2UserAuthority"),
			TypeReference.of("org.springframework.security.oauth2.core.user.DefaultOAuth2User"),
			TypeReference.of("org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority"),
			TypeReference.of("org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser"),
			TypeReference.of("org.springframework.security.oauth2.core.oidc.OidcIdToken"),
			TypeReference.of("org.springframework.security.oauth2.core.oidc.OidcUserInfo"),
			TypeReference.of("org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest"),
			TypeReference.of("org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType"),
			TypeReference.of("org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken"),
			TypeReference.of("org.springframework.security.oauth2.client.OAuth2AuthorizedClient"),
			TypeReference
				.of("org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken"),
			TypeReference
				.of("org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken"),
			TypeReference.of("org.springframework.security.oauth2.client.registration.ClientRegistration"),
			TypeReference.of("net.minidev.json.JSONObject"),
			TypeReference.of("com.nimbusds.oauth2.sdk.util.OrderedJSONObject"),
			TypeReference.of("org.springframework.security.core.context.SecurityContextImpl"),
			TypeReference.of("org.springframework.security.core.authority.SimpleGrantedAuthority"),
			TypeReference.of("org.springframework.security.core.userdetails.User"),
			TypeReference.of("org.springframework.security.authentication.AbstractAuthenticationToken"),
			TypeReference.of("org.springframework.security.authentication.UsernamePasswordAuthenticationToken"),
			TypeReference.of("org.springframework.security.core.AuthenticationException"),
			TypeReference.of("org.springframework.security.authentication.BadCredentialsException"),
			TypeReference.of("org.springframework.security.core.userdetails.UsernameNotFoundException"),
			TypeReference.of("org.springframework.security.authentication.AccountExpiredException"),
			TypeReference.of("org.springframework.security.authentication.ProviderNotFoundException"),
			TypeReference.of("org.springframework.security.authentication.DisabledException"),
			TypeReference.of("org.springframework.security.authentication.LockedException"),
			TypeReference.of("org.springframework.security.authentication.AuthenticationServiceException"),
			TypeReference.of("org.springframework.security.authentication.CredentialsExpiredException"),
			TypeReference.of("org.springframework.security.authentication.InsufficientAuthenticationException"),

			TypeReference
				.of("org.springframework.security.oauth2.client.registration.ClientRegistration\$ProviderDetails"),
			TypeReference
				.of("org.springframework.security.oauth2.client.registration.ClientRegistration\$ProviderDetails\$UserInfoEndpoint"),
			TypeReference
				.of("org.springframework.security.web.authentication.session.SessionAuthenticationException"),
			TypeReference
				.of("org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException"),
		)
			.forEach { hints.serialization().registerType(it) }
		hints.serialization() // The extra types
			.registerType(OAuth2AuthorizationRequest::class.java)
			.registerType(OAuth2AuthorizationResponseType::class.java)
			.registerType(DefaultOAuth2User::class.java)
			.registerType(Boolean::class.java)
			.registerType(SimpleGrantedAuthority::class.java)
			.registerType(OAuth2Error::class.java) // From the mentioned answer
			.registerType(OAuth2AuthorizedClient::class.java)
			.registerType(ClientRegistration::class.java)
			.registerType(ClientAuthenticationMethod::class.java)
			.registerType(AuthorizationGrantType::class.java)
			.registerType(ProviderDetails::class.java)
			.registerType(ClientSettings::class.java)
			.registerType(UserInfoEndpoint::class.java)
			.registerType(AuthenticationMethod::class.java)
			.registerType(OAuth2AccessToken::class.java)
			.registerType(OAuth2AccessToken.TokenType::class.java)
			.registerType(OAuth2RefreshToken::class.java)
			.registerType(AbstractOAuth2Token::class.java)
			.registerType(TypeReference.of("java.util.Collections\$EmptySet"))
			.registerType(TypeReference.of("java.util.Collections\$EmptyMap"))
			.registerType(TypeReference.of("java.util.Collections\$UnmodifiableMap"))
			.registerType(TypeReference.of("java.util.Collections\$UnmodifiableSet"))
			.registerType(TypeReference.of("java.time.Ser"))
			.registerType(HashMap::class.java)
			.registerType(Boolean::class.java)
			.registerType(HashSet::class.java)
			.registerType(LinkedHashSet::class.java)
			.registerType(LinkedHashMap::class.java)
			.registerType(Instant::class.java)
	}
}
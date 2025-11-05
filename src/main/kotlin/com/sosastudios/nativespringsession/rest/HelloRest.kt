package com.sosastudios.nativespringsession.rest

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class HelloRest {

	@GetMapping
	fun hello(@AuthenticationPrincipal principal: Principal) = "Hello World!"
}
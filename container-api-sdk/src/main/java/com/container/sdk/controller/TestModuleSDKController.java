package com.container.sdk.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test-sdk")
public class TestModuleSDKController {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTypes() {
		return "SDK";
	}
}
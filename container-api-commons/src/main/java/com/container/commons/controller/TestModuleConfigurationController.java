package com.container.commons.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test-commons")
public class TestModuleConfigurationController {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String getConfiguration() {
		return "Commons";
	}
}
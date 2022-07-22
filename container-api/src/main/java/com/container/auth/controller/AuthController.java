package com.container.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.container.auth.util.JwtUtil;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping(value = "/token/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*")
	public ResponseEntity<Boolean> performLogin(@RequestParam("jwt") String jwt) {
		return ResponseEntity.ok(jwtUtil.validateToken(jwt));
	}
}
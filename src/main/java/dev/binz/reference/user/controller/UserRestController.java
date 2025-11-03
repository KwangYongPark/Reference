package dev.binz.reference.user.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.binz.reference.user.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserRestController {

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody @Validated UserDTO userDTO) {
		log.debug("=====createUser");
		return ResponseEntity.ok("사용자 생성 완료: " + userDTO.getName());
	}

	@PostMapping("/validate")
	public ResponseEntity<String> validateUser(@Valid @RequestBody UserDTO userDTO) {
		log.debug("=====validateUser");
		return ResponseEntity.ok("검증 성공: " + userDTO.getName());
	}
}

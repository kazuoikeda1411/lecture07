package com.raisetech.lecture07;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Validated
@RestController
public class HelloController {

	private static final String template = " 登録氏名：%s 登録生年月日：%s user id：%d";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping ("/users")
	public String greeting(
		@RequestParam @Size (max = 20) @NotBlank String userName, @RequestParam ("userDateOfBirth") @Pattern (regexp = "^([MTSH]\\d{1,2}|\\d{2,4})/?(0?[1-9]|1[0-2])/?(0?[1-9]|[1-2][0-9]|3[0-1])$") @NotBlank String userDateOfBirth, @RequestParam ("userId") @Max (20) int userId
	) {
		return String.format(template, userName, userDateOfBirth, userId);
	}

	@PostMapping ("/users")
	public ResponseEntity<Map<String, String>> userCreate(
		@RequestBody CreateForm form
	) {
		URI url = UriComponentsBuilder.fromUriString("http://localhost:8080").path("/users/id").build().toUri();
		return ResponseEntity.created(url).body(Map.of("id：" + counter.incrementAndGet(), "userName successfully created"));
	}

	@PatchMapping ("/users/{id}")
	public ResponseEntity<Map<String, String>> userUpdate(@PathVariable ("id") @Max (20) int id, @RequestBody UpdateForm form) {
		return ResponseEntity.ok(Map.of("id:" + id, "userName successfully updated"));
	}

	@DeleteMapping ("/users/{id}")
	public ResponseEntity<Map<String, String>> userDelete(@PathVariable ("id") @Max (20) int id) {
		return ResponseEntity.ok(Map.of("id:" + id, "userName successfully deleted"));
	}
}

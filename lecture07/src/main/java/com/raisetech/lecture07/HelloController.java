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

	private static final String template = " 登録氏名：%s 登録生年月日：%s id：%d";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/users")
	public String greeting(
		@RequestParam @Size(max=20) @NotBlank String name, @RequestParam("dateOfBirth") @Pattern(regexp="^([MTSH]\\d{1,2}|\\d{2,4})/?(0?[1-9]|1[0-2])/?(0?[1-9]|[1-2][0-9]|3[0-1])$") @NotBlank String dateOfBirth, @RequestParam("id") @Max(20) int id
	) {
		return String.format(template, name, dateOfBirth, id);
	}

	@PostMapping("/users")
	public ResponseEntity userCreate(
		CreateResponse id, CreateResponse message, UriComponentsBuilder uriBuilder
	) {
		URI url = uriBuilder.path("/users/id").build().toUri();
		return ResponseEntity.created(url).body(Map.of("id", counter.incrementAndGet(), "message", "userName successfully created"));
	}

	@PatchMapping("/users/{id}")
	public ResponseEntity userUpdate(@PathVariable("id") @Max(20) int id, @RequestBody UpdateForm form) {
		return ResponseEntity.ok(Map.of("id", id, "message", "userName successfully updated"));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity userDelete(@PathVariable("id") @Max(20) int id) {
		return ResponseEntity.ok(Map.of("id", id, "message", "userName successfully deleted"));
	}
}

package io.xyzshop.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@GetMapping(value = "/healthz/ready")
	public String health() {
		return "ok";
	}
}

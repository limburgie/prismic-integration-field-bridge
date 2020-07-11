package be.webfactor.pifb.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.webfactor.pifb.domain.PrismicApiResponse;
import be.webfactor.pifb.dummy.DummyService;

@RestController
public class MainController {

	@Autowired private DummyService dummyService;

	@RequestMapping("/dummy")
	public ResponseEntity<PrismicApiResponse> dummy(@RequestParam(required = false, defaultValue = "1") int page) {
		return ResponseEntity.ok(dummyService.getResponse(page));
	}

	@RequestMapping("/dummy-auth")
	public ResponseEntity<PrismicApiResponse> dummyAuth(@RequestParam(required = false, defaultValue = "1") int page, HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
			String base64Credentials = authorization.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			final String[] creds = credentials.split(":", 2);

			if (creds[0].equals(DummyService.ACCESS_TOKEN)) {
				return dummy(page);
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}

package lgrimm1.Books.Languages;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/languages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LanguageController {

	private final LanguageService languageService;

	@Autowired
	public LanguageController(LanguageService languageService) {
		this.languageService = languageService;
	}
}

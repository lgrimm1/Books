package lgrimm1.Books.Languages;

import lgrimm1.Books.Series.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/languages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LanguageController {

	private final LanguageService languageService;

	@Autowired
	public LanguageController(LanguageService languageService) {
		this.languageService = languageService;
	}

	@PostMapping
	public LanguageEntity create(@RequestBody LanguageEntity languageEntity) {
		return languageService.createNewEntity(languageEntity);
	}

	@GetMapping
	public List<LanguageEntity> getAll() {
		return languageService.getAllEntities();
	}

	@GetMapping("/{id}")
	public LanguageEntity getById(@PathVariable long id) {
		return languageService.getEntityById(id);
	}

	@PutMapping
	public LanguageEntity update(@RequestBody LanguageEntity newLanguageEntity) {
		return languageService.updateEntity(newLanguageEntity);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable long id) {
		return languageService.deleteEntityById(id);
	}
	
}

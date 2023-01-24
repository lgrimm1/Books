package lgrimm1.Books.Authors;

import lgrimm1.Books.Series.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/authors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

	private final AuthorService authorService;
	//other fields

	@Autowired
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping
	public AuthorEntity create(@RequestBody AuthorEntity authorEntity) {
		return authorService.createNewEntity(authorEntity);
	}

	@GetMapping
	public List<AuthorEntity> getAll() {
		return authorService.getAllEntities();
	}

	@GetMapping("/{id}")
	public AuthorEntity getById(@PathVariable long id) {
		return authorService.getEntityById(id);
	}

	@PutMapping
	public AuthorEntity update(@RequestBody AuthorEntity newAuthorEntity) {
		return authorService.updateEntity(newAuthorEntity);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable long id) {
		return authorService.deleteEntityById(id);
	}

}

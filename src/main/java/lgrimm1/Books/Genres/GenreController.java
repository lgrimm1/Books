package lgrimm1.Books.Genres;

import lgrimm1.Books.Series.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/genres", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//@EnableJpaRepositories(value = "lgrimm1.Books.Genres.GenreRepository")
public class GenreController {

	private final GenreService genreService;

	@Autowired
	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}

	@PostMapping
	public GenreEntity create(@RequestBody GenreEntity genreEntity) {
		return genreService.createNewEntity(genreEntity);
	}

	@GetMapping
	public List<GenreEntity> getAll() {
		return genreService.getAllEntities();
	}

	@GetMapping("/{id}")
	public GenreEntity getById(@PathVariable long id) {
		return genreService.getEntityById(id);
	}

	@PutMapping
	public GenreEntity update(@RequestBody GenreEntity newGenreEntity) {
		return genreService.updateEntity(newGenreEntity);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable long id) {
		return genreService.deleteEntityById(id);
	}

}

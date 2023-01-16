package lgrimm1.Books.Genres;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/genres", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {

	private final GenreService genreService;

	@Autowired
	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}
}

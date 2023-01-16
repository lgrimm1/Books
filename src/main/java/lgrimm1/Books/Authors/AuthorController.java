package lgrimm1.Books.Authors;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/authors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

	private final AuthorService authorService;
	//other fields

	@Autowired
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}
}

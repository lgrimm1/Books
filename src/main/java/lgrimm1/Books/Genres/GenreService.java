package lgrimm1.Books.Genres;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class GenreService {

	private final GenreRepository genreRepository;

	@Autowired
	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}
}

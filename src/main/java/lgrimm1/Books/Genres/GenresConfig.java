package lgrimm1.Books.Genres;

import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

import java.util.*;

@Configuration
public class GenresConfig {

	CommandLineRunner commandLineRunner(GenreRepository genreRepository) {
		return args -> {
			GenreEntity ge1 = new GenreEntity("Genre Name 1", "Genre Remarks 1");
			GenreEntity ge2 = new GenreEntity("Genre Name 2", "Genre Remarks 2");
			genreRepository.saveAll(List.of(ge1, ge2));
		};
	}
}

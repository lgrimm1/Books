package lgrimm1.Books.Authors;

import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

import java.util.*;

@Configuration
//@EnableJpaRepositories(value = "lgrimm1.Books.Authors.AuthorRepository")
public class AuthorsConfig {

	CommandLineRunner commandLineRunner(AuthorRepository authorRepository) {
		return args -> {
			AuthorEntity ae1 = new AuthorEntity(
					"Author Family Name 1",
					"Author Given Name 1",
					List.of(1L),
					"Author Remarks 1");
			AuthorEntity ae2 = new AuthorEntity(
					"Author Family Name 2",
					"Author Given Name 2",
					List.of(2L),
					"Author Remarks 2");
			authorRepository.saveAll(List.of(ae1, ae2));
		};
	}
}

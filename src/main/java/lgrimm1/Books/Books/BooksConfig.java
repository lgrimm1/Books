package lgrimm1.Books.Books;

import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

import java.util.*;

@Configuration
public class BooksConfig {

	CommandLineRunner commandLineRunner(BookRepository bookRepository) {
		return args -> {
			BookEntity be1 = new BookEntity(
					"Book Title 1",
					List.of(1L),
					2000,
					1L,
					1L,
					List.of(1L),
					1L,
					0,
					"Book Remarks 1",
					Condition.EBOOK_GOOD,
					Status.UNREAD
			);
			BookEntity be2 = new BookEntity(
					"Book Title 2",
					List.of(2L),
					2010,
					2L,
					2L,
					List.of(2L),
					2L,
					1,
					"Book Remarks 2",
					Condition.PRINTED_GOOD,
					Status.FINISHED
			);
			bookRepository.saveAll(List.of(be1, be2));
		};
	}
}

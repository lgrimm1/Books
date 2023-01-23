package lgrimm1.Books.Books;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class BookService {

	private final BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Optional<BookEntity> findFirst1ByGenre(long genre) {
		return bookRepository.findAll().stream()
				.filter(bookEntity -> bookEntity.getGenres().contains(genre))
				.findFirst();
	}

	public Optional<BookEntity> findFirst1ByAuthor(long author) {
		return bookRepository.findAll().stream()
				.filter(bookEntity -> bookEntity.getAuthors().contains(author))
				.findFirst();
	}

}

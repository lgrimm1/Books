package lgrimm1.Books.Books;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class BookService {

	private final BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
}

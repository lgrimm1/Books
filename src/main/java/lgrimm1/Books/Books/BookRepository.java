package lgrimm1.Books.Books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

	Optional<BookEntity> findFirst1ByContainer(long container);
	Optional<BookEntity> findFirst1ByLanguage(long language);
	Optional<BookEntity> findFirst1BySeries(long series);
}

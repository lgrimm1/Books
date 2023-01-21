package lgrimm1.Books.Books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

	Optional<BookEntity> findBySeriesId(long id);
	Optional<BookEntity> findByLanguageId(long id);
	Optional<BookEntity> findByGenreId(long id);
	Optional<BookEntity> findByContainerId(long id);
	Optional<BookEntity> findByAuthorId(long id);
}

package lgrimm1.Books.Genres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

	Optional<GenreEntity> findFirst1ByName(String title);
}

package lgrimm1.Books.Genres;

import lgrimm1.Books.Authors.*;
import lgrimm1.Books.Languages.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}

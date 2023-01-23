package lgrimm1.Books.Series;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesEntity, Long> {

	Optional<SeriesEntity> findFirst1ByTitle(String title);
}

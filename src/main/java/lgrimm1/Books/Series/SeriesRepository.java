package lgrimm1.Books.Series;

import lgrimm1.Books.Authors.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesEntity, Long> {
}

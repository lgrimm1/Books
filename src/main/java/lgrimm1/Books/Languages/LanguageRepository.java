package lgrimm1.Books.Languages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

	Optional<LanguageEntity> findFirst1ByName(String title);
}

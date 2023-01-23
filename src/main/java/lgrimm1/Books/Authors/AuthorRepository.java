package lgrimm1.Books.Authors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

	Optional<AuthorEntity> findFirst1ByFamilyNameAndGivenName(String familyName, String givenName);
}

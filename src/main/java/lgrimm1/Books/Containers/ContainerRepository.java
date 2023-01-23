package lgrimm1.Books.Containers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ContainerRepository extends JpaRepository<ContainerEntity, Long> {

	Optional<ContainerEntity> findFirst1ByName(String title);
}

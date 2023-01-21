package lgrimm1.Books.Containers;

import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

import java.util.*;

@Configuration
public class ContainersConfig {

	CommandLineRunner commandLineRunner(ContainerRepository containerRepository) {
		return args -> {
			ContainerEntity ce1 = new ContainerEntity("Container Name 1", "Container Remarks 1");
			ContainerEntity ce2 = new ContainerEntity("Container Name 2", "Container Remarks 2");
			containerRepository.saveAll(List.of(ce1, ce2));
		};
	}
}

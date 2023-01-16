package lgrimm1.Books.Containers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ContainerService {

	private final ContainerRepository containerRepository;

	@Autowired
	public ContainerService(ContainerRepository containerRepository) {
		this.containerRepository = containerRepository;
	}
}

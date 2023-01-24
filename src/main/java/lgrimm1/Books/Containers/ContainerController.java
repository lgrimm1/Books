package lgrimm1.Books.Containers;

import lgrimm1.Books.Series.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/containers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//@EnableJpaRepositories(value = "lgrimm1.Books.Containers.ContainerRepository")
public class ContainerController {

	private final ContainerService containerService;

	@Autowired
	public ContainerController(ContainerService containerService) {
		this.containerService = containerService;
	}

	@PostMapping
	public ContainerEntity create(@RequestBody ContainerEntity containerEntity) {
		return containerService.createNewEntity(containerEntity);
	}

	@GetMapping
	public List<ContainerEntity> getAll() {
		return containerService.getAllEntities();
	}

	@GetMapping("/{id}")
	public ContainerEntity getById(@PathVariable long id) {
		return containerService.getEntityById(id);
	}

	@PutMapping
	public ContainerEntity update(@RequestBody ContainerEntity newContainerEntity) {
		return containerService.updateEntity(newContainerEntity);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable long id) {
		return containerService.deleteEntityById(id);
	}

}

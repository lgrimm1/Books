package lgrimm1.Books.Containers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/containers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ContainerController {

	private final ContainerService containerService;

	@Autowired
	public ContainerController(ContainerService containerService) {
		this.containerService = containerService;
	}
}

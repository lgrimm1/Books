package lgrimm1.Books.Series;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/series", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SeriesController {

	private final SeriesService seriesService;

	@Autowired
	public SeriesController(SeriesService seriesService) {
		this.seriesService = seriesService;
	}

	@PostMapping
	public SeriesEntity create(@RequestBody SeriesEntity seriesEntity) {
		return seriesService.createNewEntity(seriesEntity);
	}

	@GetMapping
	public List<SeriesEntity> getAll() {
		return seriesService.getAllEntities();
	}

	@GetMapping("/{id}")
	public SeriesEntity getById(@PathVariable long id) {
		return seriesService.getEntityById(id);
	}

	@PutMapping
	public SeriesEntity update(@RequestBody SeriesEntity newSeriesEntity) {
		return seriesService.updateEntity(newSeriesEntity);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable long id) {
		return seriesService.deleteEntityById(id);
	}
}

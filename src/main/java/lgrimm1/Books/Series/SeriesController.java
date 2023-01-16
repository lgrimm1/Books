package lgrimm1.Books.Series;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
//@RequestMapping(value = "/api/v1/series", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/api/v1/series")
public class SeriesController {

	private final SeriesService seriesService;

	@Autowired
	public SeriesController(SeriesService seriesService) {
		this.seriesService = seriesService;
	}

	@PostMapping
	public SeriesEntity create(@RequestParam SeriesEntity seriesEntity) {
		return seriesService.create(seriesEntity);
	}

	@GetMapping
	public List<SeriesEntity> getAll() {
		return seriesService.getAll();
	}

	@GetMapping("/{id}")
	public SeriesEntity getById(@PathVariable long id) {
		return seriesService.getById(id);
	}

	@PutMapping
	public SeriesEntity update(@RequestParam SeriesEntity newSeriesEntity) {
		return seriesService.update(newSeriesEntity);
	}

	@DeleteMapping("{id}")
	public void deleteById(@PathVariable long id) {
		seriesService.delete(id);
	}
}

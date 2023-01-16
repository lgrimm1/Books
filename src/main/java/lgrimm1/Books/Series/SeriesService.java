package lgrimm1.Books.Series;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SeriesService {

	private final SeriesRepository seriesRepository;

	@Autowired
	public SeriesService(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	public SeriesEntity create(SeriesEntity seriesEntity) {
		return seriesRepository.save(seriesEntity);
	}

	public List<SeriesEntity> getAll() {
		return seriesRepository.findAll();
	}

	public SeriesEntity getById(long id) {
		 Optional<SeriesEntity> optionalSeriesEntity = seriesRepository.findById(id);
		 return optionalSeriesEntity.orElse(null);
	}

	public SeriesEntity update(SeriesEntity newSeriesEntity) {
		long id = newSeriesEntity.getId();
		if (id == 0) {
			return null;
		}
		if (seriesRepository.findById(id).isEmpty()) {
			return null;
		}
		SeriesEntity oldSeriesEntity = seriesRepository.getReferenceById(id);
		oldSeriesEntity.setTitle(newSeriesEntity.getTitle());
		oldSeriesEntity.setRemarks(newSeriesEntity.getRemarks());
		return oldSeriesEntity;
	}

	public void delete(long id) {
		seriesRepository.deleteById(id);
	}
}

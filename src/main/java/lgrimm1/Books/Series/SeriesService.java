package lgrimm1.Books.Series;

import lgrimm1.Books.Books.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SeriesService {

	private final SeriesRepository seriesRepository;
	private final BookRepository bookRepository;

	@Autowired
	public SeriesService(SeriesRepository seriesRepository, BookRepository bookRepository) {
		this.seriesRepository = seriesRepository;
		this.bookRepository = bookRepository;
	}

	public SeriesEntity createNewEntity(SeriesEntity newSeriesEntity) {
		if (seriesRepository.findFirst1ByTitle(newSeriesEntity.getTitle()).isPresent()) {
			return null;
		}
		return seriesRepository.save(newSeriesEntity);
	}

	public List<SeriesEntity> getAllEntities() {
		return seriesRepository.findAll();
	}

	public SeriesEntity getEntityById(long id) {
		 Optional<SeriesEntity> optionalSeriesEntity = seriesRepository.findById(id);
		 return optionalSeriesEntity.orElse(null);
	}

	public SeriesEntity updateEntity(SeriesEntity modifiedSeriesEntity) {
		long id = modifiedSeriesEntity.getId();
		if (id == 0 || id == 1) {
			return null;
		}
		if (!seriesRepository.existsById(id)) {
			return null;
		}
		Optional<SeriesEntity> seriesEntityWithIdenticalTitle = seriesRepository.findFirst1ByTitle(modifiedSeriesEntity.getTitle());
		if (seriesEntityWithIdenticalTitle.isPresent() && seriesEntityWithIdenticalTitle.get().getId() != id) {
			return null;
		}
		SeriesEntity originalSeriesEntity = seriesRepository.getReferenceById(id);
		originalSeriesEntity.setTitle(modifiedSeriesEntity.getTitle());
		originalSeriesEntity.setRemarks(modifiedSeriesEntity.getRemarks());
		return originalSeriesEntity;
	}

	public boolean deleteEntityById(long id) {
		if (id == 0 || id == 1) {
			return false;
		}
		if (!seriesRepository.existsById(id)) {
			return false;
		}
		if (bookRepository.findFirst1BySeries(id).isPresent()) {
			return false;
		}
		seriesRepository.deleteById(id);
		return true;
	}
}

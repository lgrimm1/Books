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
		if (seriesRepository.findByTitle(newSeriesEntity.getTitle()).isPresent()) {
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
		if (id == 0) {
			return null;
		}
		if (!seriesRepository.existsById(id)) {
			return null;
		}
		Optional<SeriesEntity> seriesEntityWithIdenticalTitle = seriesRepository.findByTitle(modifiedSeriesEntity.getTitle());
		if (seriesEntityWithIdenticalTitle.isPresent() && seriesEntityWithIdenticalTitle.get().getId() != id) {
			return null;
		}
		SeriesEntity originalSeriesEntity = seriesRepository.getReferenceById(id);
		originalSeriesEntity.setTitle(modifiedSeriesEntity.getTitle());
		originalSeriesEntity.setRemarks(modifiedSeriesEntity.getRemarks());
		return originalSeriesEntity;
	}

	public boolean deleteEntityById(long id) {
		if (id == 0) {
			return false;
		}
		if (!seriesRepository.existsById(id)) {
			return false;
		}
		if (bookRepository.findBySeriesId(id).isPresent()) {
			return false;
		}
		seriesRepository.deleteById(id);
		return true;
	}
}

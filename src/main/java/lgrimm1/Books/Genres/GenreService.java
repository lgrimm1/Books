package lgrimm1.Books.Genres;

import lgrimm1.Books.Languages.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class GenreService {

	private final GenreRepository genreRepository;

	@Autowired
	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	public GenreEntity createNewEntity(GenreEntity newGenreEntity) {
		if (genreRepository.findByName(newGenreEntity.getName()).isPresent()) {
			return null;
		}
		return genreRepository.save(newGenreEntity);
	}

	public List<GenreEntity> getAllEntities() {
		return genreRepository.findAll();
	}

	public GenreEntity getEntityById(long id) {
		Optional<GenreEntity> optionalGenreEntity = genreRepository.findById(id);
		return optionalGenreEntity.orElse(null);
	}

	public GenreEntity updateEntity(GenreEntity modifiedGenreEntity) {
		long id = modifiedGenreEntity.getId();
		if (id == 0) {
			return null;
		}
		if (!genreRepository.existsById(id)) {
			return null;
		}
		Optional<GenreEntity> genreEntityWithIdenticalName = genreRepository.findByName(modifiedGenreEntity.getName());
		if (genreEntityWithIdenticalName.isPresent() && genreEntityWithIdenticalName.get().getId() != id) {
			return null;
		}
		GenreEntity oldGenreEntity = genreRepository.getReferenceById(id);
		oldGenreEntity.setName(modifiedGenreEntity.getName());
		oldGenreEntity.setRemarks(modifiedGenreEntity.getRemarks());
		return oldGenreEntity;
	}

	public boolean deleteEntityById(long id) {
		if (id == 0) {
			return false;
		}
		if (!genreRepository.existsById(id)) {
			return false;
		}
		genreRepository.deleteById(id);
		return true;
	}
}

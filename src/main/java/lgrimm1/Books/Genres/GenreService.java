package lgrimm1.Books.Genres;

import lgrimm1.Books.Books.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class GenreService {

	private final GenreRepository genreRepository;
	private final BookService bookService;

	@Autowired
	public GenreService(GenreRepository genreRepository, BookService bookService) {
		this.genreRepository = genreRepository;
		this.bookService = bookService;
	}

	public GenreEntity createNewEntity(GenreEntity newGenreEntity) {
		if (genreRepository.findFirst1ByName(newGenreEntity.getName()).isPresent()) {
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
		Optional<GenreEntity> genreEntityWithIdenticalName = genreRepository.findFirst1ByName(modifiedGenreEntity.getName());
		if (genreEntityWithIdenticalName.isPresent() && genreEntityWithIdenticalName.get().getId() != id) {
			return null;
		}
		GenreEntity originalGenreEntity = genreRepository.getReferenceById(id);
		originalGenreEntity.setName(modifiedGenreEntity.getName());
		originalGenreEntity.setRemarks(modifiedGenreEntity.getRemarks());
		return originalGenreEntity;
	}

	public boolean deleteEntityById(long id) {
		if (id == 0) {
			return false;
		}
		if (!genreRepository.existsById(id)) {
			return false;
		}
		if (bookService.findFirst1ByGenre(id).isPresent()) {
			return false;
		}
		genreRepository.deleteById(id);
		return true;
	}
}

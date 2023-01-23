package lgrimm1.Books.Languages;

import lgrimm1.Books.Books.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class LanguageService {

	private final LanguageRepository languageRepository;
	private final BookRepository bookRepository;

	@Autowired
	public LanguageService(LanguageRepository languageRepository, BookRepository bookRepository) {
		this.languageRepository = languageRepository;
		this.bookRepository = bookRepository;
	}

	public LanguageEntity createNewEntity(LanguageEntity newLanguageEntity) {
		if (languageRepository.findFirst1ByName(newLanguageEntity.getName()).isPresent()) {
			return null;
		}
		return languageRepository.save(newLanguageEntity);
	}

	public List<LanguageEntity> getAllEntities() {
		return languageRepository.findAll();
	}

	public LanguageEntity getEntityById(long id) {
		Optional<LanguageEntity> optionalLanguageEntity = languageRepository.findById(id);
		return optionalLanguageEntity.orElse(null);
	}

	public LanguageEntity updateEntity(LanguageEntity modifiedLanguageEntity) {
		long id = modifiedLanguageEntity.getId();
		if (id == 0) {
			return null;
		}
		if (!languageRepository.existsById(id)) {
			return null;
		}
		Optional<LanguageEntity> languageEntityWithIdenticalName = languageRepository.findFirst1ByName(modifiedLanguageEntity.getName());
		if (languageEntityWithIdenticalName.isPresent() && languageEntityWithIdenticalName.get().getId() != id) {
			return null;
		}
		LanguageEntity originalLanguageEntity = languageRepository.getReferenceById(id);
		originalLanguageEntity.setName(modifiedLanguageEntity.getName());
		originalLanguageEntity.setRemarks(modifiedLanguageEntity.getRemarks());
		return originalLanguageEntity;
	}

	public boolean deleteEntityById(long id) {
		if (id == 0) {
			return false;
		}
		if (!languageRepository.existsById(id)) {
			return false;
		}
		if (bookRepository.findFirst1ByLanguage(id).isPresent()) {
			return false;
		}
		languageRepository.deleteById(id);
		return true;
	}
}

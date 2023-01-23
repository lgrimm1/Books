package lgrimm1.Books.Authors;

import lgrimm1.Books.Books.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;
	private final BookService bookService;

	@Autowired
	public AuthorService(AuthorRepository authorRepository, BookService bookService) {
		this.authorRepository = authorRepository;
		this.bookService = bookService;
	}

	public AuthorEntity createNewEntity(AuthorEntity newAuthorEntity) {
		if (authorRepository.findFirst1ByFamilyNameAndGivenName(newAuthorEntity.getFamilyName(), newAuthorEntity.getGivenName()).isPresent()) {
			return null;
		}
		return authorRepository.save(newAuthorEntity);
	}

	public List<AuthorEntity> getAllEntities() {
		return authorRepository.findAll();
	}

	public AuthorEntity getEntityById(long id) {
		Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(id);
		return optionalAuthorEntity.orElse(null);
	}

	public AuthorEntity updateEntity(AuthorEntity modifiedAuthorEntity) {
		long id = modifiedAuthorEntity.getId();
		if (id == 0) {
			return null;
		}
		if (!authorRepository.existsById(id)) {
			return null;
		}
		Optional<AuthorEntity> authorEntityWithIdenticalNames = authorRepository.findFirst1ByFamilyNameAndGivenName(
				modifiedAuthorEntity.getFamilyName(),
				modifiedAuthorEntity.getGivenName());
		if (authorEntityWithIdenticalNames.isPresent() && authorEntityWithIdenticalNames.get().getId() != id) {
			return null;
		}
		AuthorEntity originalAuthorEntity = authorRepository.getReferenceById(id);
		originalAuthorEntity.setFamilyName(modifiedAuthorEntity.getFamilyName());
		originalAuthorEntity.setGivenName(modifiedAuthorEntity.getGivenName());
		originalAuthorEntity.setRemarks(modifiedAuthorEntity.getRemarks());
		return originalAuthorEntity;
	}

	public boolean deleteEntityById(long id) {
		if (id == 0) {
			return false;
		}
		if (!authorRepository.existsById(id)) {
			return false;
		}
		if (bookService.findFirst1ByAuthor(id).isPresent()) {
			return false;
		}
		authorRepository.deleteById(id);
		return true;
	}

}

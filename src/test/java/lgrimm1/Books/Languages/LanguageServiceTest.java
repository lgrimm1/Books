package lgrimm1.Books.Languages;

import lgrimm1.Books.Books.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.when;

class LanguageServiceTest {

	LanguageRepository languageRepository;
	BookRepository bookRepository;
	LanguageService languageService;

	@BeforeEach
	void setUp() {
		languageRepository = Mockito.mock(LanguageRepository.class);
		bookRepository = Mockito.mock(BookRepository.class);
		languageService = new LanguageService(languageRepository, bookRepository);
	}

	@Test
	void createEntityFromValidEntityButNameAlreadyExists() {
		LanguageEntity input = new LanguageEntity(0L, "Name", "Remarks");
		when(languageRepository.findByName("Name"))
				.thenReturn(Optional.of(new LanguageEntity(2L, "Name", "Remarks")));
		Assertions.assertNull(languageService.createNewEntity(input));
	}

	@Test
	void createEntityFromValidEntity() {
		LanguageEntity input = new LanguageEntity(0L, "Name", "Remarks");
		when(languageRepository.findByName("Name"))
				.thenReturn(Optional.empty());
		when(languageRepository.save(input))
				.thenReturn(new LanguageEntity(2L, "Name", "Remarks"));
		Assertions.assertEquals(new LanguageEntity(2L, "Name", "Remarks"), languageService.createNewEntity(input));
	}

	@Test
	void retrieveAllEntities() {
		when(languageRepository.findAll())
				.thenReturn(List.of(
						new LanguageEntity(2L, "Name", "Remarks"),
						new LanguageEntity(2L, "Name", "Remarks")));
		Assertions.assertIterableEquals(List.of(
						new LanguageEntity(2L, "Name", "Remarks"),
						new LanguageEntity(2L, "Name", "Remarks")),
				languageService.getAllEntities());
	}

	@Test
	void retrieveANonExistentEntity() {
		when(languageRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(languageService.getEntityById(2L));
	}

	@Test
	void retrieveAnExistingEntity() {
		when(languageRepository.findById(2L))
				.thenReturn(Optional.of(new LanguageEntity(2L, "Name", "Remarks")));
		Assertions.assertEquals(new LanguageEntity(2L, "Name", "Remarks"), languageService.getEntityById(2L));
	}

	@Test
	void updateAnEntityWithZeroId() {
		Assertions.assertNull(languageService.updateEntity(new LanguageEntity(0L, "NewName", "NewRemarks")));
	}

	@Test
	void updateANonExistentEntity() {
		when(languageRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(languageService.updateEntity(new LanguageEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntityButNameAlreadyExistsInOtherEntity() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(languageRepository.findByName("NewName"))
				.thenReturn(Optional.of(new LanguageEntity(6L, "NewName", "OtherRemarks")));
		when(languageRepository.getReferenceById(2L))
				.thenReturn(new LanguageEntity(2L, "Name", "Remarks"));
		Assertions.assertNull(languageService.updateEntity(new LanguageEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntity() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(languageRepository.findByName("NewTitle"))
				.thenReturn(Optional.empty());
		when(languageRepository.getReferenceById(2L))
				.thenReturn(new LanguageEntity(2L, "Name", "Remarks"));
		Assertions.assertEquals(new LanguageEntity(2L, "NewName", "NewRemarks"), languageService.updateEntity(new LanguageEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void deleteAnEntityWithZeroId() {
		Assertions.assertFalse(languageService.deleteEntityById(0));
	}

	@Test
	void deleteANonExistentEntity() {
		when(languageRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(languageService.deleteEntityById(2L));
	}

	@Test
	void deleteAnExistingEntityButReferencedInABook() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1ByLanguage(2L))
				.thenReturn(Optional.of(new BookEntity(
						6L,
						"Title",
						List.of(1L),
						2000,
						5L,
						2L,
						List.of(3L),
						4L,
						1,
						"Remarks",
						Condition.EBOOK_GOOD,
						Status.UNREAD)));
		Assertions.assertFalse(languageService.deleteEntityById(2L));
	}

	@Test
	void deleteAnExistingEntity() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1ByLanguage(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(languageService.deleteEntityById(2L));
	}

}
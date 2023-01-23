package lgrimm1.Books.Languages;

import lgrimm1.Books.Books.*;
import lgrimm1.Books.Series.*;
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
	void createWithNotUniqueName() {
		LanguageEntity input = new LanguageEntity(0L, "NewName", "NewRemarks");
		when(languageRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new LanguageEntity(2L, "NewName", "Remarks")));
		Assertions.assertNull(languageService.createNewEntity(input));
	}

	@Test
	void createWithUniqueName() {
		LanguageEntity input = new LanguageEntity(0L, "NewName", "NewRemarks");
		when(languageRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.empty());
		when(languageRepository.save(input))
				.thenReturn(new LanguageEntity(2L, "NewName", "NewRemarks"));
		Assertions.assertEquals(new LanguageEntity(2L, "NewName", "NewRemarks"), languageService.createNewEntity(input));
	}

	@Test
	void retrieveAll() {
		when(languageRepository.findAll())
				.thenReturn(List.of(
						new LanguageEntity(2L, "Name1", "Remarks1"),
						new LanguageEntity(3L, "Name2", "Remarks2")));
		Assertions.assertIterableEquals(List.of(
						new LanguageEntity(2L, "Name1", "Remarks1"),
						new LanguageEntity(3L, "Name2", "Remarks2")),
				languageService.getAllEntities());
	}

	@Test
	void retrieveNotExisting() {
		when(languageRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(languageService.getEntityById(2L));
	}

	@Test
	void retrieveExisting() {
		when(languageRepository.findById(2L))
				.thenReturn(Optional.of(new LanguageEntity(2L, "Name", "Remarks")));
		Assertions.assertEquals(new LanguageEntity(2L, "Name", "Remarks"), languageService.getEntityById(2L));
	}

	@Test
	void updateWithZeroId() {
		Assertions.assertNull(languageService.updateEntity(new LanguageEntity(0L, "NewName", "NewRemarks")));
	}

	@Test
	void updateNotExisting() {
		when(languageRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(languageService.updateEntity(new LanguageEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithNotUniqueTitle() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(languageRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new LanguageEntity(6L, "NewName", "OtherRemarks")));
		Assertions.assertNull(languageService.updateEntity(new LanguageEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithUniqueTitle() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(languageRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.empty());
		when(languageRepository.getReferenceById(2L))
				.thenReturn(new LanguageEntity(2L, "OldName", "OldRemarks"));
		Assertions.assertEquals(
				new LanguageEntity(2L, "NewName", "NewRemarks"),
				languageService.updateEntity(new LanguageEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithSameTitle() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(languageRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new LanguageEntity(2L, "NewName", "OtherRemarks")));
		when(languageRepository.getReferenceById(2L))
				.thenReturn(new LanguageEntity(2L, "NewName", "OtherRemarks"));
		Assertions.assertEquals(
				new LanguageEntity(2L, "NewName", "NewRemarks"),
				languageService.updateEntity(new LanguageEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void deleteWithZeroId() {
		Assertions.assertFalse(languageService.deleteEntityById(0));
	}

	@Test
	void deleteNotExisting() {
		when(languageRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(languageService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingReferencedInABook() {
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
	void deleteExistingNotReferencedInABook() {
		when(languageRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1ByLanguage(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(languageService.deleteEntityById(2L));
	}

}
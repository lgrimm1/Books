package lgrimm1.Books.Authors;

import lgrimm1.Books.Books.*;
import lgrimm1.Books.Genres.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthorServiceTest {

	AuthorRepository authorRepository;
	BookService bookService;
	AuthorService authorService;

	@BeforeEach
	void setUp() {
		authorRepository = Mockito.mock(AuthorRepository.class);
		bookService = Mockito.mock(BookService.class);
		authorService = new AuthorService(authorRepository, bookService);
	}

	@Test
	void createWithNotUniqueName() {
		AuthorEntity input = new AuthorEntity(0L, "NewFamilyName", "NewGivenName", "NewRemarks");
		when(authorRepository.findFirst1ByFamilyNameAndGivenName("NewFamilyName", "NewGivenName"))
				.thenReturn(Optional.of(new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "Remarks")));
		Assertions.assertNull(authorService.createNewEntity(input));
	}

	@Test
	void createWithUniqueName() {
		AuthorEntity input = new AuthorEntity(0L, "NewFamilyName", "NewGivenName", "NewRemarks");
		when(authorRepository.findFirst1ByFamilyNameAndGivenName("NewFamilyName", "NewGivenName"))
				.thenReturn(Optional.empty());
		when(authorRepository.save(input))
				.thenReturn(new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "NewRemarks"));
		Assertions.assertEquals(
				new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "NewRemarks"),
				authorService.createNewEntity(input));
	}

	@Test
	void retrieveAll() {
		when(authorRepository.findAll())
				.thenReturn(List.of(
						new AuthorEntity(2L, "FamilyName1", "GivenName1", "Remarks1"),
						new AuthorEntity(3L, "FamilyName2", "GivenName2", "Remarks2")));
		Assertions.assertIterableEquals(List.of(
						new AuthorEntity(2L, "FamilyName1", "GivenName1", "Remarks1"),
						new AuthorEntity(3L, "FamilyName2", "GivenName2", "Remarks2")),
				authorService.getAllEntities());
	}

	@Test
	void retrieveNotExisting() {
		when(authorRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(authorService.getEntityById(2L));
	}

	@Test
	void retrieveExisting() {
		when(authorRepository.findById(2L))
				.thenReturn(Optional.of(new AuthorEntity(2L, "FamilyName", "GivenName", "Remarks")));
		Assertions.assertEquals(new AuthorEntity(2L, "FamilyName", "GivenName", "Remarks"), authorService.getEntityById(2L));
	}

	@Test
	void updateWithZeroId() {
		Assertions.assertNull(authorService.updateEntity(new AuthorEntity(0L, "NewFamilyName", "NewGivenName", "NewRemarks")));
	}

	@Test
	void updateNotExisting() {
		when(authorRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(authorService.updateEntity(new AuthorEntity(0L, "NewFamilyName", "NewGivenName", "NewRemarks")));
	}

	@Test
	void updateExistingWithNotUniqueName() {
		when(authorRepository.existsById(2L))
				.thenReturn(true);
		when(authorRepository.findFirst1ByFamilyNameAndGivenName("NewFamilyName", "NewGivenName"))
				.thenReturn(Optional.of(new AuthorEntity(6L, "NewFamilyName", "NewGivenName", "OtherRemarks")));
		Assertions.assertNull(authorService.updateEntity(new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "NewRemarks")));
	}

	@Test
	void updateExistingWithUniqueName() {
		when(authorRepository.existsById(2L))
				.thenReturn(true);
		when(authorRepository.findFirst1ByFamilyNameAndGivenName("NewFamilyName", "NewGivenName"))
				.thenReturn(Optional.empty());
		when(authorRepository.getReferenceById(2L))
				.thenReturn(new AuthorEntity(2L, "OldFamilyName", "OldGivenName",  "OldRemarks"));
		Assertions.assertEquals(
				new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "NewRemarks"),
				authorService.updateEntity(new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "NewRemarks")));
	}

	@Test
	void updateExistingWithSameTitle() {
		when(authorRepository.existsById(2L))
				.thenReturn(true);
		when(authorRepository.findFirst1ByFamilyNameAndGivenName("NewFamilyName", "NewGivenName"))
				.thenReturn(Optional.of(new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "OtherRemarks")));
		when(authorRepository.getReferenceById(2L))
				.thenReturn(new AuthorEntity(2L, "NewFamilyName", "NewGivenName",  "OtherRemarks"));
		Assertions.assertEquals(
				new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "NewRemarks"),
				authorService.updateEntity(new AuthorEntity(2L, "NewFamilyName", "NewGivenName", "NewRemarks")));
	}

	@Test
	void deleteWithZeroId() {
		Assertions.assertFalse(authorService.deleteEntityById(0));
	}

	@Test
	void deleteNotExisting() {
		when(authorRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(authorService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingReferencedInABook() {
		when(authorRepository.existsById(2L))
				.thenReturn(true);
		when(bookService.findFirst1ByAuthor(2L))
				.thenReturn(Optional.of(new BookEntity(
						6L,
						"Title",
						List.of(2L, 4L),
						2000,
						5L,
						7L,
						List.of(3L),
						8L,
						10,
						"Remarks",
						Condition.EBOOK_GOOD,
						Status.UNREAD)));
		Assertions.assertFalse(authorService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingNotReferencedInABook() {
		when(authorRepository.existsById(2L))
				.thenReturn(true);
		when(bookService.findFirst1ByAuthor(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(authorService.deleteEntityById(2L));
	}

}
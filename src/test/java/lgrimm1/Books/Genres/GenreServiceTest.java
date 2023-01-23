package lgrimm1.Books.Genres;

import lgrimm1.Books.Books.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.when;

class GenreServiceTest {

	GenreRepository genreRepository;
	BookService bookService;
	GenreService genreService;

	@BeforeEach
	void setUp() {
		genreRepository = Mockito.mock(GenreRepository.class);
		bookService = Mockito.mock(BookService.class);
		genreService = new GenreService(genreRepository, bookService);
	}

	@Test
	void createWithNotUniqueName() {
		GenreEntity input = new GenreEntity(0L, "NewName", "NewRemarks");
		when(genreRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new GenreEntity(2L, "NewName", "Remarks")));
		Assertions.assertNull(genreService.createNewEntity(input));
	}

	@Test
	void createWithUniqueName() {
		GenreEntity input = new GenreEntity(0L, "NewName", "NewRemarks");
		when(genreRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.empty());
		when(genreRepository.save(input))
				.thenReturn(new GenreEntity(2L, "NewName", "NewRemarks"));
		Assertions.assertEquals(new GenreEntity(2L, "NewName", "NewRemarks"), genreService.createNewEntity(input));
	}

	@Test
	void retrieveAll() {
		when(genreRepository.findAll())
				.thenReturn(List.of(
						new GenreEntity(2L, "Name1", "Remarks1"),
						new GenreEntity(3L, "Name2", "Remarks2")));
		Assertions.assertIterableEquals(List.of(
						new GenreEntity(2L, "Name1", "Remarks1"),
						new GenreEntity(3L, "Name2", "Remarks2")),
				genreService.getAllEntities());
	}

	@Test
	void retrieveNotExisting() {
		when(genreRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(genreService.getEntityById(2L));
	}

	@Test
	void retrieveExisting() {
		when(genreRepository.findById(2L))
				.thenReturn(Optional.of(new GenreEntity(2L, "Name", "Remarks")));
		Assertions.assertEquals(new GenreEntity(2L, "Name", "Remarks"), genreService.getEntityById(2L));
	}

	@Test
	void updateWithZeroId() {
		Assertions.assertNull(genreService.updateEntity(new GenreEntity(0L, "NewName", "NewRemarks")));
	}

	@Test
	void updateNotExisting() {
		when(genreRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(genreService.updateEntity(new GenreEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithNotUniqueTitle() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		when(genreRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new GenreEntity(6L, "NewName", "OtherRemarks")));
		Assertions.assertNull(genreService.updateEntity(new GenreEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithUniqueTitle() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		when(genreRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.empty());
		when(genreRepository.getReferenceById(2L))
				.thenReturn(new GenreEntity(2L, "OldName", "OldRemarks"));
		Assertions.assertEquals(
				new GenreEntity(2L, "NewName", "NewRemarks"),
				genreService.updateEntity(new GenreEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithSameTitle() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		when(genreRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new GenreEntity(2L, "NewName", "OtherRemarks")));
		when(genreRepository.getReferenceById(2L))
				.thenReturn(new GenreEntity(2L, "NewName", "OtherRemarks"));
		Assertions.assertEquals(
				new GenreEntity(2L, "NewName", "NewRemarks"),
				genreService.updateEntity(new GenreEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void deleteWithZeroId() {
		Assertions.assertFalse(genreService.deleteEntityById(0));
	}

	@Test
	void deleteNotExisting() {
		when(genreRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(genreService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingReferencedInABook() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		when(bookService.findFirst1ByGenre(2L))
				.thenReturn(Optional.of(new BookEntity(
						6L,
						"Title",
						List.of(1L),
						2000,
						5L,
						7L,
						List.of(2L, 3L),
						4L,
						10,
						"Remarks",
						Condition.EBOOK_GOOD,
						Status.UNREAD)));
		Assertions.assertFalse(genreService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingNotReferencedInABook() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		when(bookService.findFirst1ByGenre(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(genreService.deleteEntityById(2L));
	}

}
package lgrimm1.Books.Series;

import lgrimm1.Books.Books.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.when;

class SeriesServiceTest {

	SeriesRepository seriesRepository;
	BookRepository bookRepository;
	SeriesService seriesService;

	@BeforeEach
	void setUp() {
		seriesRepository = Mockito.mock(SeriesRepository.class);
		bookRepository = Mockito.mock(BookRepository.class);
		seriesService = new SeriesService(seriesRepository, bookRepository);
	}

	@Test
	void createWithNotUniqueTitle() {
		SeriesEntity input = new SeriesEntity(0L, "NewTitle", "NewRemarks");
		when(seriesRepository.findFirst1ByTitle("NewTitle"))
				.thenReturn(Optional.of(new SeriesEntity(2L, "NewTitle", "Remarks")));
		Assertions.assertNull(seriesService.createNewEntity(input));
	}

	@Test
	void createWithUniqueTitle() {
		SeriesEntity input = new SeriesEntity(0L, "NewTitle", "NewRemarks");
		when(seriesRepository.findFirst1ByTitle("NewTitle"))
				.thenReturn(Optional.empty());
		when(seriesRepository.save(input))
				.thenReturn(new SeriesEntity(2L, "NewTitle", "NewRemarks"));
		Assertions.assertEquals(new SeriesEntity(2L, "NewTitle", "NewRemarks"), seriesService.createNewEntity(input));
	}

	@Test
	void retrieveAll() {
		when(seriesRepository.findAll())
				.thenReturn(List.of(
						new SeriesEntity(2L, "Title1", "Remarks1"),
						new SeriesEntity(3L, "Title2", "Remarks2")));
		Assertions.assertIterableEquals(List.of(
				new SeriesEntity(2L, "Title1", "Remarks1"),
				new SeriesEntity(3L, "Title2", "Remarks2")),
				seriesService.getAllEntities());
	}

	@Test
	void retrieveNotExisting() {
		when(seriesRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(seriesService.getEntityById(2L));
	}

	@Test
	void retrieveExisting() {
		when(seriesRepository.findById(2L))
				.thenReturn(Optional.of(new SeriesEntity(2L, "Title", "Remarks")));
		Assertions.assertEquals(new SeriesEntity(2L, "Title", "Remarks"), seriesService.getEntityById(2L));
	}

	@Test
	void updateWithZeroId() {
		Assertions.assertNull(seriesService.updateEntity(new SeriesEntity(0L, "NewTitle", "NewRemarks")));
	}

	@Test
	void updateNotExisting() {
		when(seriesRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(seriesService.updateEntity(new SeriesEntity(2L, "NewTitle", "NewRemarks")));
	}

	@Test
	void updateExistingWithNotUniqueTitle() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(seriesRepository.findFirst1ByTitle("NewTitle"))
				.thenReturn(Optional.of(new SeriesEntity(6L, "NewTitle", "OtherRemarks")));
		Assertions.assertNull(seriesService.updateEntity(new SeriesEntity(2L, "NewTitle", "NewRemarks")));
	}

	@Test
	void updateExistingWithUniqueTitle() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(seriesRepository.findFirst1ByTitle("NewTitle"))
				.thenReturn(Optional.empty());
		when(seriesRepository.getReferenceById(2L))
				.thenReturn(new SeriesEntity(2L, "OldTitle", "OldRemarks"));
		Assertions.assertEquals(
				new SeriesEntity(2L, "NewTitle", "NewRemarks"),
				seriesService.updateEntity(new SeriesEntity(2L, "NewTitle", "NewRemarks")));
	}

	@Test
	void updateExistingWithSameTitle() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(seriesRepository.findFirst1ByTitle("NewTitle"))
				.thenReturn(Optional.of(new SeriesEntity(2L, "NewTitle", "OtherRemarks")));
		when(seriesRepository.getReferenceById(2L))
				.thenReturn(new SeriesEntity(2L, "NewTitle", "OtherRemarks"));
		Assertions.assertEquals(
				new SeriesEntity(2L, "NewTitle", "NewRemarks"),
				seriesService.updateEntity(new SeriesEntity(2L, "NewTitle", "NewRemarks")));
	}

	@Test
	void deleteWithZeroId() {
		Assertions.assertFalse(seriesService.deleteEntityById(0));
	}

	@Test
	void deleteNotExisting() {
		when(seriesRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(seriesService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingReferencedInABook() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1BySeries(2L))
				.thenReturn(Optional.of(new BookEntity(
						6L,
						"Title",
						List.of(1L),
						2000,
						5L,
						4L,
						List.of(3L),
						2L,
						1,
						"Remarks",
						Condition.EBOOK_GOOD,
						Status.UNREAD)));
		Assertions.assertFalse(seriesService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingNotReferencedInABook() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1BySeries(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(seriesService.deleteEntityById(2L));
	}
}

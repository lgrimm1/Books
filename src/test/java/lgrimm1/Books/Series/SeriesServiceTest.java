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
	void createEntityFromValidEntityButTitleAlreadyExists() {
		SeriesEntity input = new SeriesEntity(0L, "Title", "Remarks");
		when(seriesRepository.findByTitle("Title"))
				.thenReturn(Optional.of(new SeriesEntity(2L, "Title", "Remarks")));
		Assertions.assertNull(seriesService.createNewEntity(input));
	}

	@Test
	void createEntityFromValidEntity() {
		SeriesEntity input = new SeriesEntity(0L, "Title", "Remarks");
		when(seriesRepository.findByTitle("Title"))
				.thenReturn(Optional.empty());
		when(seriesRepository.save(input))
				.thenReturn(new SeriesEntity(2L, "Title", "Remarks"));
		Assertions.assertEquals(new SeriesEntity(2L, "Title", "Remarks"), seriesService.createNewEntity(input));
	}

	@Test
	void retrieveAllEntities() {
		when(seriesRepository.findAll())
				.thenReturn(List.of(
						new SeriesEntity(2L, "Title", "Remarks"),
						new SeriesEntity(2L, "Title", "Remarks")));
		Assertions.assertIterableEquals(List.of(
				new SeriesEntity(2L, "Title", "Remarks"),
				new SeriesEntity(2L, "Title", "Remarks")),
				seriesService.getAllEntities());
	}

	@Test
	void retrieveANonExistentEntity() {
		when(seriesRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(seriesService.getEntityById(2L));
	}

	@Test
	void retrieveAnExistingEntity() {
		when(seriesRepository.findById(2L))
				.thenReturn(Optional.of(new SeriesEntity(2L, "Title", "Remarks")));
		Assertions.assertEquals(new SeriesEntity(2L, "Title", "Remarks"), seriesService.getEntityById(2L));
	}

	@Test
	void updateAnEntityWithZeroId() {
		Assertions.assertNull(seriesService.updateEntity(new SeriesEntity(0L, "NewTitle", "NewRemarks")));
	}

	@Test
	void updateANonExistentEntity() {
		when(seriesRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(seriesService.updateEntity(new SeriesEntity(2L, "NewTitle", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntityButTitleAlreadyExistsInOtherEntity() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(seriesRepository.findByTitle("NewTitle"))
				.thenReturn(Optional.of(new SeriesEntity(6L, "NewTitle", "OtherRemarks")));
		when(seriesRepository.getReferenceById(2L))
				.thenReturn(new SeriesEntity(2L, "Title", "Remarks"));
		Assertions.assertNull(seriesService.updateEntity(new SeriesEntity(2L, "NewTitle", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntity() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(seriesRepository.findByTitle("NewTitle"))
				.thenReturn(Optional.empty());
		when(seriesRepository.getReferenceById(2L))
				.thenReturn(new SeriesEntity(2L, "Title", "Remarks"));
		Assertions.assertEquals(
				new SeriesEntity(2L, "NewTitle", "NewRemarks"),
				seriesService.updateEntity(new SeriesEntity(2L, "NewTitle", "NewRemarks")));
	}

	@Test
	void deleteAnEntityWithZeroId() {
		Assertions.assertFalse(seriesService.deleteEntityById(0));
	}

	@Test
	void deleteANonExistentEntity() {
		when(seriesRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(seriesService.deleteEntityById(2L));
	}

	@Test
	void deleteAnExistingEntityButReferencedInABook() {
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
	void deleteAnExistingEntity() {
		when(seriesRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1BySeries(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(seriesService.deleteEntityById(2L));
	}
}

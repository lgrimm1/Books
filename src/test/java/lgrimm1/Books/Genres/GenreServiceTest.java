package lgrimm1.Books.Genres;

import lgrimm1.Books.Languages.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GenreServiceTest {

	GenreRepository genreRepository;
	GenreService genreService;

	@BeforeEach
	void setUp() {
		genreRepository = Mockito.mock(GenreRepository.class);
		genreService = new GenreService(genreRepository);
	}

	@Test
	void createEntityFromValidEntityButNameAlreadyExists() {
		GenreEntity input = new GenreEntity(0L, "Name", "Remarks");
		when(genreRepository.findByName("Name"))
				.thenReturn(Optional.of(new GenreEntity(2L, "Name", "Remarks")));
		Assertions.assertNull(genreService.createNewEntity(input));
	}

	@Test
	void createEntityFromValidEntity() {
		GenreEntity input = new GenreEntity(6L, "Name", "Remarks");
		when(genreRepository.save(input))
				.thenReturn(new GenreEntity(2L, "Name", "Remarks"));
		Assertions.assertEquals(new GenreEntity(2L, "Name", "Remarks"), genreService.createNewEntity(input));
	}

	@Test
	void retrieveAllEntities() {
		when(genreRepository.findAll())
				.thenReturn(List.of(
						new GenreEntity(2L, "Name", "Remarks"),
						new GenreEntity(2L, "Name", "Remarks")));
		Assertions.assertIterableEquals(List.of(
						new GenreEntity(2L, "Name", "Remarks"),
						new GenreEntity(2L, "Name", "Remarks")),
				genreService.getAllEntities());
	}

	@Test
	void retrieveANonExistentEntity() {
		when(genreRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(genreService.getEntityById(2L));
	}

	@Test
	void retrieveAnExistingEntity() {
		when(genreRepository.findById(2L))
				.thenReturn(Optional.of(new GenreEntity(2L, "Name", "Remarks")));
		Assertions.assertEquals(new GenreEntity(2L, "Name", "Remarks"), genreService.getEntityById(2L));
	}

	@Test
	void updateAnEntityWithZeroId() {
		Assertions.assertNull(genreService.updateEntity(new GenreEntity(0L, "NewName", "NewRemarks")));
	}

	@Test
	void updateANonExistentEntity() {
		when(genreRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(genreService.updateEntity(new GenreEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntityButNameAlreadyExistsInOtherEntity() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		when(genreRepository.findByName("NewName"))
				.thenReturn(Optional.of(new GenreEntity(6L, "NewName", "OtherRemarks")));
		when(genreRepository.getReferenceById(2L))
				.thenReturn(new GenreEntity(2L, "Name", "Remarks"));
		Assertions.assertNull(genreService.updateEntity(new GenreEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntity() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		when(genreRepository.findByName("NewTitle"))
				.thenReturn(Optional.empty());
		when(genreRepository.getReferenceById(2L))
				.thenReturn(new GenreEntity(2L, "Name", "Remarks"));
		Assertions.assertEquals(new GenreEntity(2L, "NewName", "NewRemarks"), genreService.updateEntity(new GenreEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void deleteAnEntityWithZeroId() {
		Assertions.assertFalse(genreService.deleteEntityById(0));
	}

	@Test
	void deleteANonExistentEntity() {
		when(genreRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(genreService.deleteEntityById(2L));
	}

	@Test
	void deleteAnExistingEntity() {
		when(genreRepository.existsById(2L))
				.thenReturn(true);
		Assertions.assertTrue(genreService.deleteEntityById(2L));
	}

}
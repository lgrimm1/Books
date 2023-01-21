package lgrimm1.Books.Containers;

import lgrimm1.Books.Books.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.when;

class ContainerServiceTest {

	ContainerRepository containerRepository;
	BookRepository bookRepository;
	ContainerService containerService;

	@BeforeEach
	void setUp() {
		containerRepository = Mockito.mock(ContainerRepository.class);
		bookRepository = Mockito.mock(BookRepository.class);
		containerService = new ContainerService(containerRepository, bookRepository);
	}

	@Test
	void createEntityFromValidEntityButNameAlreadyExists() {
		ContainerEntity input = new ContainerEntity(0L, "Name", "Remarks");
		when(containerRepository.findByName("Name"))
				.thenReturn(Optional.of(new ContainerEntity(2L, "Name", "Remarks")));
		Assertions.assertNull(containerService.createNewEntity(input));
	}

	@Test
	void createEntityFromValidEntity() {
		ContainerEntity input = new ContainerEntity(6L, "Name", "Remarks");
		when(containerRepository.save(input))
				.thenReturn(new ContainerEntity(2L, "Name", "Remarks"));
		Assertions.assertEquals(new ContainerEntity(2L, "Name", "Remarks"), containerService.createNewEntity(input));
	}

	@Test
	void retrieveAllEntities() {
		when(containerRepository.findAll())
				.thenReturn(List.of(
						new ContainerEntity(2L, "Name", "Remarks"),
						new ContainerEntity(2L, "Name", "Remarks")));
		Assertions.assertIterableEquals(List.of(
						new ContainerEntity(2L, "Name", "Remarks"),
						new ContainerEntity(2L, "Name", "Remarks")),
				containerService.getAllEntities());
	}

	@Test
	void retrieveANonExistentEntity() {
		when(containerRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(containerService.getEntityById(2L));
	}

	@Test
	void retrieveAnExistingEntity() {
		when(containerRepository.findById(2L))
				.thenReturn(Optional.of(new ContainerEntity(2L, "Name", "Remarks")));
		Assertions.assertEquals(new ContainerEntity(2L, "Name", "Remarks"), containerService.getEntityById(2L));
	}

	@Test
	void updateAnEntityWithZeroId() {
		Assertions.assertNull(containerService.updateEntity(new ContainerEntity(0L, "NewName", "NewRemarks")));
	}

	@Test
	void updateANonExistentEntity() {
		when(containerRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(containerService.updateEntity(new ContainerEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntityButNameAlreadyExistsInOtherEntity() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(containerRepository.findByName("NewName"))
				.thenReturn(Optional.of(new ContainerEntity(6L, "NewName", "OtherRemarks")));
		when(containerRepository.getReferenceById(2L))
				.thenReturn(new ContainerEntity(2L, "Name", "Remarks"));
		Assertions.assertNull(containerService.updateEntity(new ContainerEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateAnExistingEntity() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(containerRepository.findByName("NewTitle"))
				.thenReturn(Optional.empty());
		when(containerRepository.getReferenceById(2L))
				.thenReturn(new ContainerEntity(2L, "Name", "Remarks"));
		Assertions.assertEquals(new ContainerEntity(2L, "NewName", "NewRemarks"), containerService.updateEntity(new ContainerEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void deleteAnEntityWithZeroId() {
		Assertions.assertFalse(containerService.deleteEntityById(0));
	}

	@Test
	void deleteANonExistentEntity() {
		when(containerRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(containerService.deleteEntityById(2L));
	}

	@Test
	void deleteAnExistingEntityButReferencedInABook() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findByContainerId(2L))
				.thenReturn(Optional.of(new BookEntity(
						6L,
						"Title",
						List.of(1L),
						2000,
						2L,
						4L,
						List.of(3L),
						5L,
						1,
						"Remarks",
						Condition.EBOOK_GOOD,
						Status.UNREAD)));
		Assertions.assertFalse(containerService.deleteEntityById(2L));
	}

	@Test
	void deleteAnExistingEntity() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findByContainerId(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(containerService.deleteEntityById(2L));
	}

}
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
	void createWithNotUniqueName() {
		ContainerEntity input = new ContainerEntity(0L, "NewName", "NewRemarks");
		when(containerRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new ContainerEntity(2L, "NewName", "Remarks")));
		Assertions.assertNull(containerService.createNewEntity(input));
	}

	@Test
	void createWithUniqueName() {
		ContainerEntity input = new ContainerEntity(0L, "NewName", "NewRemarks");
		when(containerRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.empty());
		when(containerRepository.save(input))
				.thenReturn(new ContainerEntity(2L, "NewName", "NewRemarks"));
		Assertions.assertEquals(new ContainerEntity(2L, "NewName", "NewRemarks"), containerService.createNewEntity(input));
	}

	@Test
	void retrieveAll() {
		when(containerRepository.findAll())
				.thenReturn(List.of(
						new ContainerEntity(2L, "Name1", "Remarks1"),
						new ContainerEntity(3L, "Name2", "Remarks2")));
		Assertions.assertIterableEquals(List.of(
						new ContainerEntity(2L, "Name1", "Remarks1"),
						new ContainerEntity(3L, "Name2", "Remarks2")),
				containerService.getAllEntities());
	}

	@Test
	void retrieveNotExisting() {
		when(containerRepository.findById(2L))
				.thenReturn(Optional.empty());
		Assertions.assertNull(containerService.getEntityById(2L));
	}

	@Test
	void retrieveExisting() {
		when(containerRepository.findById(2L))
				.thenReturn(Optional.of(new ContainerEntity(2L, "Name", "Remarks")));
		Assertions.assertEquals(new ContainerEntity(2L, "Name", "Remarks"), containerService.getEntityById(2L));
	}

	@Test
	void updateWithZeroId() {
		Assertions.assertNull(containerService.updateEntity(new ContainerEntity(0L, "NewName", "NewRemarks")));
	}

	@Test
	void updateNotExisting() {
		when(containerRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertNull(containerService.updateEntity(new ContainerEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithNotUniqueTitle() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(containerRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new ContainerEntity(6L, "NewName", "OtherRemarks")));
		Assertions.assertNull(containerService.updateEntity(new ContainerEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithUniqueTitle() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(containerRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.empty());
		when(containerRepository.getReferenceById(2L))
				.thenReturn(new ContainerEntity(2L, "OldName", "OldRemarks"));
		Assertions.assertEquals(
				new ContainerEntity(2L, "NewName", "NewRemarks"),
				containerService.updateEntity(new ContainerEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void updateExistingWithSameTitle() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(containerRepository.findFirst1ByName("NewName"))
				.thenReturn(Optional.of(new ContainerEntity(2L, "NewName", "OtherRemarks")));
		when(containerRepository.getReferenceById(2L))
				.thenReturn(new ContainerEntity(2L, "NewName", "OtherRemarks"));
		Assertions.assertEquals(
				new ContainerEntity(2L, "NewName", "NewRemarks"),
				containerService.updateEntity(new ContainerEntity(2L, "NewName", "NewRemarks")));
	}

	@Test
	void deleteWithZeroId() {
		Assertions.assertFalse(containerService.deleteEntityById(0));
	}

	@Test
	void deleteNotExisting() {
		when(containerRepository.existsById(2L))
				.thenReturn(false);
		Assertions.assertFalse(containerService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingReferencedInABook() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1ByContainer(2L))
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
		Assertions.assertFalse(containerService.deleteEntityById(2L));
	}

	@Test
	void deleteExistingNotReferencedInABook() {
		when(containerRepository.existsById(2L))
				.thenReturn(true);
		when(bookRepository.findFirst1ByContainer(2L))
				.thenReturn(Optional.empty());
		Assertions.assertTrue(containerService.deleteEntityById(2L));
	}

}
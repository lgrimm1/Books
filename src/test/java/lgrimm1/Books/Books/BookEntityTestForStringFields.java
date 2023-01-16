package lgrimm1.Books.Books;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.*;

class BookEntityTestForStringFields {

	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private void validateBean(Object bean) throws AssertionError {
		Optional<ConstraintViolation<Object>> violation = validator.validate(bean).stream().findFirst();
		if (violation.isPresent()) {
			throw new ValidationException(violation.get().getMessage());
		}
	}

	@Test
	void nullTitle() {
		BookEntity be = new BookEntity(
				1,
				null,
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Title must exist.", e.getMessage());
	}

	@Test
	void emptyTitle() {
		BookEntity be = new BookEntity(
				1,
				"",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Title must exist.", e.getMessage());
	}

	@Test
	void blankTitle() {
		BookEntity be = new BookEntity(
				1,
				" ",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Title must exist.", e.getMessage());
	}

	@Test
	void existingTitle() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Assertions.assertDoesNotThrow(() -> validateBean(be));
	}

	@Test
	void nullRemarks() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				null,
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Assertions.assertDoesNotThrow(() -> validateBean(be));
	}

	@Test
	void emptyRemarks() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Assertions.assertDoesNotThrow(() -> validateBean(be));
	}

	@Test
	void blankRemarks() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				" ",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Assertions.assertDoesNotThrow(() -> validateBean(be));
	}

	@Test
	void existingRemarks() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Assertions.assertDoesNotThrow(() -> validateBean(be));
	}
}

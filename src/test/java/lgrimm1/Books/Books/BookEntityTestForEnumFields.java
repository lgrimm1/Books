package lgrimm1.Books.Books;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.*;

class BookEntityTestForEnumFields {

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
	void nullCondition() {
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
				null,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Condition must exist.", e.getMessage());
	}

	@Test
	void existingCondition() {
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
	void nullStatus() {
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
				null);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Status must exist.", e.getMessage());
	}

	@Test
	void existingStatus() {
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

package lgrimm1.Books.Books;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.*;

class BookEntityTestForNumberFields {

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
	void negativeId() {
		BookEntity be = new BookEntity(
				-1,
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
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("ID must be minimum 0.", e.getMessage());
	}

	@Test
	void minimumId() {
		BookEntity be = new BookEntity(
				0,
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
	void maximumId() {
		BookEntity be = new BookEntity(
				Long.MAX_VALUE,
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
	void nullYear() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				null,
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
	void publishedBeforeLowestValidYear() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				-100000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("If defined, minimum year is -99,999.", e.getMessage());
	}

	@Test
	void publishedOnLowestValidYear() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				-99999,
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
	void publishedOnHighestValidYear() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2500,
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
	void publishedAfterHighestValidYear() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2501,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("If defined, maximum year is 2500.", e.getMessage());
	}

	@Test
	void zeroContainerId() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				0L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Container ID must be minimum 1.", e.getMessage());
	}

	@Test
	void positiveContainerId() {
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
	void zeroLanguageId() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				0L,
				List.of(1L, 2L, 3L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Language ID must be minimum 1.", e.getMessage());
	}

	@Test
	void positiveLanguageId() {
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
	void zeroSeriesId() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				0L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("ID of series must be minimum 1.", e.getMessage());
	}

	@Test
	void positiveSeriesId() {
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
	void negativeBookNumber() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L),
				1L,
				-1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Minimum number is 0.", e.getMessage());
	}

	@Test
	void nonNegativeBookNumber() {
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

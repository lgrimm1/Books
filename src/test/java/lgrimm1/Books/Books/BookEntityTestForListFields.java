package lgrimm1.Books.Books;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.*;

class BookEntityTestForListFields {

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
	void nullListOfAuthorIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				null,
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
		Assertions.assertEquals("Minimum 1 author or editor is needed.", e.getMessage());
	}

	@Test
	void emptyListOfAuthorIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				new ArrayList<>(),
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
		Assertions.assertEquals("Minimum 1, maximum 10 authors or editors are needed.", e.getMessage());
	}

	@Test
	void minimumNumberOfAuthorIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L),
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
	void maximumNumberOfAuthorIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L),
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
	void tooHighNumberOfAuthorIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L),
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
		Assertions.assertEquals("Minimum 1, maximum 10 authors or editors are needed.", e.getMessage());
	}

	@Test
	void nullListOfGenreIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				null,
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Minimum 1 genre is needed.", e.getMessage());
	}

	@Test
	void emptyListOfGenreIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				new ArrayList<>(),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Minimum 1, maximum 10 genre is needed.", e.getMessage());
	}

	@Test
	void minimumNumberOfGenreIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Assertions.assertDoesNotThrow(() -> validateBean(be));
	}

	@Test
	void maximumNumberOfGenreIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Assertions.assertDoesNotThrow(() -> validateBean(be));
	}

	@Test
	void tooHighNumberOfGenreIds() {
		BookEntity be = new BookEntity(
				1,
				"title",
				List.of(1L, 2L, 3L),
				2000,
				1L,
				1L,
				List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L),
				1L,
				1,
				"remarks",
				Condition.EBOOK_GOOD,
				Status.UNREAD);
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(be));
		Assertions.assertEquals("Minimum 1, maximum 10 genre is needed.", e.getMessage());
	}
}

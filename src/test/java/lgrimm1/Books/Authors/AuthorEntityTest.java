package lgrimm1.Books.Authors;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.*;

class AuthorEntityTest {

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
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new AuthorEntity(
						-1,
						"familyname",
						"givenname",
						List.of(1L, 2L, 3L),
						"remarks")));
		Assertions.assertEquals("ID must be minimum 0.", e.getMessage());
	}

	@Test
	void minimumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				0,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void maximumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				Long.MAX_VALUE,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void nullFamilyName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new AuthorEntity(
						1,
						null,
						"givenname",
						List.of(1L, 2L, 3L),
						"remarks")));
		Assertions.assertEquals("Family (company) name must exist.", e.getMessage());
	}

	@Test
	void emptyFamilyName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new AuthorEntity(
						1,
						"",
						"givenname",
						List.of(1L, 2L, 3L),
						"remarks")));
		Assertions.assertEquals("Family (company) name must exist.", e.getMessage());
	}

	@Test
	void blankFamilyName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new AuthorEntity(
						1,
						" ",
						"givenname",
						List.of(1L, 2L, 3L),
						"remarks")));
		Assertions.assertEquals("Family (company) name must exist.", e.getMessage());
	}

	@Test
	void existingFamilyName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void nullGivenName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				null,
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void emptyGivenName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"",
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void blankGivenName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				" ",
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void existingGivenName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void nullListOfBookIds() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				null,
				"remarks")));
	}

	@Test
	void emptyListOfBookIds() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				new ArrayList<Long>(),
				"remarks")));
	}

	@Test
	void existingIdsInListOfBookIds() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				"remarks")));
	}

	@Test
	void nullRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				null)));
	}

	@Test
	void emptyRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				"")));
	}

	@Test
	void blankRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				" ")));
	}

	@Test
	void existingRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				List.of(1L, 2L, 3L),
				"remarks")));
	}
}

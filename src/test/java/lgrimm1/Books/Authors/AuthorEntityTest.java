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
						"remarks")));
		Assertions.assertEquals("ID must be minimum 0.", e.getMessage());
	}

	@Test
	void minimumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				0,
				"familyname",
				"givenname",
				"remarks")));
	}

	@Test
	void maximumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				Long.MAX_VALUE,
				"familyname",
				"givenname",
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
						"remarks")));
		Assertions.assertEquals("Family (company) name must exist.", e.getMessage());
	}

	@Test
	void existingFamilyName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				"remarks")));
	}

	@Test
	void nullGivenName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new AuthorEntity(
						1,
						"familyname",
						null,
						"remarks")));
		Assertions.assertEquals("Given name must exist, use - for none.", e.getMessage());
	}

	@Test
	void emptyGivenName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new AuthorEntity(
						1,
						"familyname",
						"",
						"remarks")));
		Assertions.assertEquals("Given name must exist, use - for none.", e.getMessage());
	}

	@Test
	void blankGivenName() {
		Exception e = Assertions.assertThrows(
				ValidationException.class,
				() -> validateBean(new AuthorEntity(
						1,
						"familyname",
						" ",
						"remarks")));
		Assertions.assertEquals("Given name must exist, use - for none.", e.getMessage());
	}

	@Test
	void existingGivenName() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				"remarks")));
	}

	@Test
	void nullRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				null)));
	}

	@Test
	void emptyRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				"")));
	}

	@Test
	void blankRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				" ")));
	}

	@Test
	void existingRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new AuthorEntity(
				1,
				"familyname",
				"givenname",
				"remarks")));
	}
}

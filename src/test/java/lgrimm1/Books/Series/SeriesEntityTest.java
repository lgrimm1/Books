package lgrimm1.Books.Series;

import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.*;

class SeriesEntityTest {

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
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(new SeriesEntity(-1, "title", "remarks")));
		Assertions.assertEquals("ID must be minimum 0.", e.getMessage());
	}

	@Test
	void minimumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new SeriesEntity(0, "title", "remarks")));
	}

	@Test
	void maximumId() {
		Assertions.assertDoesNotThrow(() -> validateBean(new SeriesEntity(Long.MAX_VALUE, "title", "remarks")));
	}

	@Test
	void nullTitle() {
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(new SeriesEntity(1, null, "remarks")));
		Assertions.assertEquals("Series title must exist.", e.getMessage());
	}

	@Test
	void emptyTitle() {
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(new SeriesEntity(1, "", "remarks")));
		Assertions.assertEquals("Series title must exist.", e.getMessage());
	}

	@Test
	void blankTitle() {
		Exception e = Assertions.assertThrows(ValidationException.class, () -> validateBean(new SeriesEntity(1, " ", "remarks")));
		Assertions.assertEquals("Series title must exist.", e.getMessage());
	}

	@Test
	void existingTitle() {
		Assertions.assertDoesNotThrow(() -> validateBean(new SeriesEntity(1, "title", "remarks")));
	}

	@Test
	void nullRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new SeriesEntity(1, "title", null)));
	}

	@Test
	void emptyRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new SeriesEntity(1, "title", "")));
	}

	@Test
	void blankRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new SeriesEntity(1, "title", " ")));
	}

	@Test
	void existingRemarks() {
		Assertions.assertDoesNotThrow(() -> validateBean(new SeriesEntity(1, "title", "remarks")));
	}
}
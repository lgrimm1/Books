package lgrimm1.Books;

import lgrimm1.Books.Series.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BooksApplicationTests {

	@Autowired
	private SeriesController seriesController;

	@Test
	public void contextLoads() throws Exception {
		Assertions.assertNotNull(seriesController);
	}

}

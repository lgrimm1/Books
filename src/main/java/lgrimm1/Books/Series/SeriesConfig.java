package lgrimm1.Books.Series;

import org.springframework.boot.*;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
public class SeriesConfig {

	@Bean
	CommandLineRunner commandLineRunner(SeriesRepository seriesRepository) {
		return args -> {
			SeriesEntity se1 = new SeriesEntity("Test Title 1", "TEST Remarks 1");
			SeriesEntity se2 = new SeriesEntity("Test Title 2", "TEST Remarks 2");
			seriesRepository.saveAll(List.of(se1, se2));
		};
	}
}

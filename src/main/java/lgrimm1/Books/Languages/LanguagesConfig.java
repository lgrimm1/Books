package lgrimm1.Books.Languages;

import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

import java.util.*;

@Configuration
public class LanguagesConfig {

	CommandLineRunner commandLineRunner(LanguageRepository languageRepository) {
		return args -> {
			LanguageEntity le1 = new LanguageEntity("Language Name 1", "Language Remarks 1");
			LanguageEntity le2 = new LanguageEntity("Language Name 2", "Language Remarks 2");
			languageRepository.saveAll(List.of(le1, le2));
		};
	}
}

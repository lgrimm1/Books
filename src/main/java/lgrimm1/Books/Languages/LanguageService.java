package lgrimm1.Books.Languages;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class LanguageService {

	private final LanguageRepository languageRepository;

	@Autowired
	public LanguageService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}
}

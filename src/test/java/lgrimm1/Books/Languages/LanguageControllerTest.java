package lgrimm1.Books.Languages;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

@WebMvcTest(LanguageController.class)
@AutoConfigureDataJpa
class LanguageControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LanguageService languageService;

	@Test
	public void createEntity() throws Exception {
		when(languageService.createNewEntity(new LanguageEntity(
				2L,
				"Name",
				"Remarks")))
				.thenReturn(new LanguageEntity(
						5L,
						"Name",
						"Remarks"));
		mockMvc.perform(post("/api/v1/languages")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.content("{" +
								"\"id\":2," +
								"\"name\":\"Name\"," +
								"\"remarks\":\"Remarks\"" +
								"}"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"{\"id\":5," +
								"\"name\":\"Name\"," +
								"\"remarks\":\"Remarks\"}"
				));
	}

	@Test
	public void getAllEntities() throws Exception {
		when(languageService.getAllEntities())
				.thenReturn(List.of(
						new LanguageEntity(
								5L,
								"Name 5",
								"Remarks 5"),
						new LanguageEntity(
								6L,
								"Name 6",
								"Remarks 6")
				));
		mockMvc.perform(get("/api/v1/languages")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"[{\"id\":5," +
								"\"name\":\"Name 5\"," +
								"\"remarks\":\"Remarks 5\"}," +
								"{\"id\":6," +
								"\"name\":\"Name 6\"," +
								"\"remarks\":\"Remarks 6\"}]"
				));
	}

	@Test
	public void getEntityById() throws Exception {
		when(languageService.getEntityById(2))
				.thenReturn(new LanguageEntity(
						2L,
						"Name",
						"Remarks"
				));
		mockMvc.perform(get("/api/v1/languages/2")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"{\"id\":2," +
								"\"name\":\"Name\"," +
								"\"remarks\":\"Remarks\"}"
				));
	}

	@Test
	void updateEntity() throws Exception {
		when(languageService.updateEntity(new LanguageEntity(
				5L,
				"New Name",
				"New Remarks")))
				.thenReturn(new LanguageEntity(
						5L,
						"New Name",
						"New Remarks"));
		mockMvc.perform(put("/api/v1/languages")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.content(
								"{\"id\":5," +
										"\"name\":\"New Name\"," +
										"\"remarks\":\"New Remarks\"}"
						))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"{\"id\":5," +
								"\"name\":\"New Name\"," +
								"\"remarks\":\"New Remarks\"}"
				));
	}

	@Test
	void deleteEntity() throws Exception {
		when(languageService.deleteEntityById(2L))
				.thenReturn(true);
		mockMvc.perform(delete("/api/v1/languages/2")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("true"));
	}
	
}
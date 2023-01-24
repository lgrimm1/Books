package lgrimm1.Books.Authors;

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

@WebMvcTest(AuthorController.class)
@AutoConfigureDataJpa
class AuthorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorService authorService;

	@Test
	public void createEntity() throws Exception {
		when(authorService.createNewEntity(new AuthorEntity(
				2L,
				"Family Name",
				"Given Name",
				"Remarks")))
				.thenReturn(new AuthorEntity(
						5L,
						"Family Name",
						"Given Name",
						"Remarks"));
		mockMvc.perform(post("/api/v1/authors")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.content("{" +
								"\"id\":2," +
								"\"familyName\":\"Family Name\"," +
								"\"givenName\":\"Given Name\"," +
								"\"remarks\":\"Remarks\"" +
								"}"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{" +
						"\"id\":5," +
						"\"familyName\":\"Family Name\"," +
						"\"givenName\":\"Given Name\"," +
						"\"remarks\":\"Remarks\"" +
						"}"));
	}

	@Test
	public void getAllEntities() throws Exception {
		when(authorService.getAllEntities())
				.thenReturn(List.of(
						new AuthorEntity(
								5L,
								"Family Name 5",
								"Given Name 5",
								"Remarks 5"),
						new AuthorEntity(
								6L,
								"Family Name 6",
								"Given Name 6",
								"Remarks 6")
				));
		mockMvc.perform(get("/api/v1/authors")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"[{" +
								"\"id\":5," +
								"\"familyName\":\"Family Name 5\"," +
								"\"givenName\":\"Given Name 5\"," +
								"\"remarks\":\"Remarks 5\"" +
								"},{" +
								"\"id\":6," +
								"\"familyName\":\"Family Name 6\"," +
								"\"givenName\":\"Given Name 6\"," +
								"\"remarks\":\"Remarks 6\"" +
								"}]"
				));
	}

	@Test
	public void getEntityById() throws Exception {
		when(authorService.getEntityById(2))
				.thenReturn(new AuthorEntity(
						2L,
						"Family Name",
						"Given Name",
						"Remarks"
				));
		mockMvc.perform(get("/api/v1/authors/2")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{" +
						"\"id\":2," +
						"\"familyName\":\"Family Name\"," +
						"\"givenName\":\"Given Name\"," +
						"\"remarks\":\"Remarks\"" +
						"}"
				));
	}

	@Test
	void updateEntity() throws Exception {
		when(authorService.updateEntity(new AuthorEntity(
				5L,
				"New Family Name",
				"New Given Name",
				"New Remarks")))
				.thenReturn(new AuthorEntity(
						5L,
						"New Family Name",
						"New Given Name",
						"New Remarks"));
		mockMvc.perform(put("/api/v1/authors")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.content("{" +
								"\"id\":5," +
								"\"familyName\":\"New Family Name\"," +
								"\"givenName\":\"New Given Name\"," +
								"\"remarks\":\"New Remarks\"" +
								"}"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{" +
						"\"id\":5," +
						"\"familyName\":\"New Family Name\"," +
						"\"givenName\":\"New Given Name\"," +
						"\"remarks\":\"New Remarks\"" +
						"}"
				));
	}

	@Test
	void deleteEntity() throws Exception {
		when(authorService.deleteEntityById(2L))
				.thenReturn(true);
		mockMvc.perform(delete("/api/v1/authors/2")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("true"));
	}

}
package lgrimm1.Books.Genres;

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

@WebMvcTest(GenreController.class)
@AutoConfigureDataJpa
class GenreControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GenreService genreService;

	@Test
	public void createEntity() throws Exception {
		when(genreService.createNewEntity(new GenreEntity(
				2L,
				"Name",
				"Remarks")))
				.thenReturn(new GenreEntity(
						5L,
						"Name",
						"Remarks"));
		mockMvc.perform(post("/api/v1/genres")
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
		when(genreService.getAllEntities())
				.thenReturn(List.of(
						new GenreEntity(
								5L,
								"Name 5",
								"Remarks 5"),
						new GenreEntity(
								6L,
								"Name 6",
								"Remarks 6")
				));
		mockMvc.perform(get("/api/v1/genres")
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
		when(genreService.getEntityById(2))
				.thenReturn(new GenreEntity(
						2L,
						"Name",
						"Remarks"
				));
		mockMvc.perform(get("/api/v1/genres/2")
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
		when(genreService.updateEntity(new GenreEntity(
				5L,
				"New Name",
				"New Remarks")))
				.thenReturn(new GenreEntity(
						5L,
						"New Name",
						"New Remarks"));
		mockMvc.perform(put("/api/v1/genres")
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
		when(genreService.deleteEntityById(2L))
				.thenReturn(true);
		mockMvc.perform(delete("/api/v1/genres/2")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("true"));
	}
}
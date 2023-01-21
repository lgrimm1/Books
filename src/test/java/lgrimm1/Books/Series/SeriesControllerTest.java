package lgrimm1.Books.Series;

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

@WebMvcTest(SeriesController.class)
@AutoConfigureDataJpa
class SeriesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SeriesService seriesService;

	@Test
	public void createEntity() throws Exception {
		when(seriesService.createNewEntity(new SeriesEntity(
				2L,
				"Title",
				"Remarks")))
				.thenReturn(new SeriesEntity(
						5L,
						"Title",
						"Remarks"));
		mockMvc.perform(post("/api/v1/series")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.content("{" +
								"\"id\":2," +
								"\"title\":\"Title\"," +
								"\"remarks\":\"Remarks\"" +
								"}"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"{\"id\":5," +
						"\"title\":\"Title\"," +
						"\"remarks\":\"Remarks\"}"
				));
	}

	@Test
	public void getAllEntities() throws Exception {
		when(seriesService.getAllEntities())
				.thenReturn(List.of(
						new SeriesEntity(
						5L,
						"Title 5",
						"Remarks 5"),
						new SeriesEntity(
						6L,
						"Title 6",
						"Remarks 6")
				));
		mockMvc.perform(get("/api/v1/series")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"[{\"id\":5," +
						"\"title\":\"Title 5\"," +
						"\"remarks\":\"Remarks 5\"}," +
						"{\"id\":6," +
						"\"title\":\"Title 6\"," +
						"\"remarks\":\"Remarks 6\"}]"
				));
	}

	@Test
	public void getEntityById() throws Exception {
		when(seriesService.getEntityById(2))
				.thenReturn(new SeriesEntity(
						2L,
						"Title",
						"Remarks"
				));
		mockMvc.perform(get("/api/v1/series/2")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"{\"id\":2," +
								"\"title\":\"Title\"," +
								"\"remarks\":\"Remarks\"}"
				));
	}

	@Test
	void updateEntity() throws Exception {
		when(seriesService.updateEntity(new SeriesEntity(
				5L,
				"New Title",
				"New Remarks")))
				.thenReturn(new SeriesEntity(
						5L,
						"New Title",
						"New Remarks"));
		mockMvc.perform(put("/api/v1/series")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(
						"{\"id\":5," +
						"\"title\":\"New Title\"," +
						"\"remarks\":\"New Remarks\"}"
				))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(
						"{\"id\":5," +
								"\"title\":\"New Title\"," +
								"\"remarks\":\"New Remarks\"}"
				));
	}

	@Test
	void deleteEntity() throws Exception {
		when(seriesService.deleteEntityById(2L))
				.thenReturn(true);
		mockMvc.perform(delete("/api/v1/series/2")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("true"));
	}
}

package lgrimm1.Books.Books;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.lang.*;
import org.springframework.validation.annotation.*;

import java.util.*;

/**
 * This class represents the Book entity.<p>
 * Connection towards Authors: 1:n
 * Connection towards Containers: 1:1
 * Connection towards Languages: 1:1
 * Connection towards Genres: 1:n
 * Connection towards Series: 1:1
 */
@Entity
@Table(name = "books")
@Validated
public class BookEntity {

	@Id
	@SequenceGenerator(name = "books_id_seq", sequenceName = "books_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_id_seq")
	@Column(name = "id")
	@Min(value = 0, message = "ID must be minimum 0.")
	private long id;

	@Column(name = "title", nullable = false)
	@NotBlank(message = "Title must exist.")
	private String title;

	/**
	 * List of authors or editors who wrote or edited the book.
	 */
	@Column(name = "author", nullable = false)
	@NotNull(message = "Minimum 1 author or editor is needed.")
	@Size(min = 1, max = 10, message = "Minimum 1, maximum 10 authors or editors are needed.")
	private List<Long> authors;

	@Column(name = "year")
	@Min(value = -99999, message = "If defined, minimum year is -99,999.")
	@Max(value = 2500, message = "If defined, maximum year is 2500.")
	private Integer year;

	@Column(name = "container", nullable = false)
	@Min(value = 1, message = "Container ID must be minimum 1.")
	private long container;

	@Column(name = "language", nullable = false)
	@Min(value = 1, message = "Language ID must be minimum 1.")
	private long language;

	/**
	 * List of genres of the book.
	 */
	@Column(name = "genre", nullable = false)
	@NotNull(message = "Minimum 1 genre is needed.")
	@Size(min = 1, max = 10, message = "Minimum 1, maximum 10 genre is needed.")
	private List<Long> genres;

	/**
	 * The series the book belongs to (for solo books there must be a default series name on 1st series ID).
	 */
	@Column(name = "series", nullable = false)
	@Min(value = 1, message = "ID of series must be minimum 1.")
	private long series;

	/**
	 * Book number in the series, zero if the book is solo.
	 */
	@Column(name = "book_number", nullable = false)
	@Min(value = 0, message = "Minimum number is 0.")
	private int bookNumber;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "condition", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Condition must exist.")
	private Condition condition;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Status must exist.")
	private Status status;

	public BookEntity() {
	}

	public BookEntity(@NonNull String title,
					  @NonNull List<Long> authors,
					  Integer year,
					  long container,
					  long language,
					  @NonNull List<Long> genres,
					  long series,
					  int bookNumber,
					  String remarks,
					  @NonNull Condition condition,
					  @NonNull Status status) {
		this.title = title;
		this.authors = authors;
		this.year = year;
		this.container = container;
		this.language = language;
		this.genres = genres;
		this.series = series;
		this.bookNumber = bookNumber;
		this.remarks = remarks;
		this.condition = condition;
		this.status = status;
	}

	public BookEntity(long id,
					  @NonNull String title,
					  @NonNull List<Long> authors,
					  Integer year,
					  long container,
					  long language,
					  @NonNull List<Long> genres,
					  long series,
					  int bookNumber,
					  String remarks,
					  @NonNull Condition condition,
					  @NonNull Status status) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.year = year;
		this.container = container;
		this.language = language;
		this.genres = genres;
		this.series = series;
		this.bookNumber = bookNumber;
		this.remarks = remarks;
		this.condition = condition;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(@NonNull String title) {
		this.title = title;
	}

	public List<Long> getAuthors() {
		return authors;
	}

	public void setAuthors(@NonNull List<Long> authors) {
		this.authors = authors;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public long getContainer() {
		return container;
	}

	public void setContainer(long container) {
		this.container = container;
	}

	public long getLanguage() {
		return language;
	}

	public void setLanguage(long language) {
		this.language = language;
	}

	public List<Long> getGenres() {
		return genres;
	}

	public void setGenres(@NonNull List<Long> genres) {
		this.genres = genres;
	}

	public long getSeries() {
		return series;
	}

	public void setSeries(long series) {
		this.series = series;
	}

	public int getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(@NonNull Condition condition) {
		this.condition = condition;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(@NonNull Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookEntity that = (BookEntity) o;
		return id == that.id &&
				container == that.container &&
				language == that.language &&
				series == that.series &&
				bookNumber == that.bookNumber &&
				title.equals(that.title) &&
				authors.equals(that.authors) &&
				Objects.equals(year, that.year) &&
				genres.equals(that.genres) &&
				Objects.equals(remarks, that.remarks) &&
				condition == that.condition &&
				status == that.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id,
				title,
				authors,
				year,
				container,
				language,
				genres,
				series,
				bookNumber,
				remarks,
				condition,
				status);
	}

	@Override
	public String toString() {
		return "BookEntity{" +
				"id=" + id +
				", title='" + title + '\'' +
				", authors=" + authors +
				", year=" + year +
				", container=" + container +
				", language=" + language +
				", genres=" + genres +
				", series=" + series +
				", bookNumber=" + bookNumber +
				", remarks='" + remarks + '\'' +
				", condition=" + condition +
				", status=" + status +
				'}';
	}
}

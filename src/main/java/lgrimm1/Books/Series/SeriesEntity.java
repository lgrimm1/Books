package lgrimm1.Books.Series;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.lang.*;
import org.springframework.validation.annotation.*;

import java.util.*;

/**
 * This class represents the Series entity.<p>
 * There must be a default series with ID 1 which is for marking solo books.
 */
@Entity
@Table(name = "series")
@Validated
public class SeriesEntity {

	@Id
	@SequenceGenerator(name = "series_id_seq", sequenceName = "series_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_id_seq")
	@Column(name = "id")
	@Min(value = 0, message = "ID must be minimum 0.")
	private long id;

	@Column(name = "title", unique = true)
	@NotBlank(message = "Series title must exist.")
	private String title;

	@Column(name = "remarks")
	private String remarks;

	public SeriesEntity() {
	}

	public SeriesEntity(@NonNull String title, String remarks) {
		this.title = title;
		this.remarks = remarks;
	}

	public SeriesEntity(long id, @NonNull String title, String remarks) {
		this.id = id;
		this.title = title;
		this.remarks = remarks;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SeriesEntity that = (SeriesEntity) o;
		return id == that.id && title.equals(that.title) && Objects.equals(remarks, that.remarks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, remarks);
	}

	@Override
	public String toString() {
		return "SeriesEntity{" +
				"id=" + id +
				", title='" + title + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}

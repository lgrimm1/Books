package lgrimm1.Books.Genres;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.lang.*;
import org.springframework.validation.annotation.*;

import java.util.*;

/**
 * This class represents the Genre entity.<p>
 */
@Entity
@Table(name = "genres")
@Validated
public class GenreEntity {

	@Id
	@SequenceGenerator(name = "genres_id_seq", sequenceName = "genres_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genres_id_seq")
	@Column(name = "id")
	@Min(value = 0, message = "ID must be minimum 0.")
	private long id;

	@Column(name = "name", nullable = false, unique = true)
	@NotBlank(message = "Genre name must exist.")
	private String name;

	@Column(name = "remarks")
	private String remarks;

	public GenreEntity() {
	}

	public GenreEntity(@NonNull String name, String remarks) {
		this.name = name;
		this.remarks = remarks;
	}

	public GenreEntity(long id, @NonNull String name, String remarks) {
		this.id = id;
		this.name = name;
		this.remarks = remarks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(@NonNull String name) {
		this.name = name;
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
		GenreEntity that = (GenreEntity) o;
		return id == that.id && name.equals(that.name) && Objects.equals(remarks, that.remarks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, remarks);
	}

	@Override
	public String toString() {
		return "GenreEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}

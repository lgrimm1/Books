package lgrimm1.Books.Languages;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.lang.*;
import org.springframework.validation.annotation.*;

import java.util.*;

/**
 * This class represents the Language entity.<p>
 */
@Entity
@Table(name = "languages")
@Validated
public class LanguageEntity {

	@Id
	@SequenceGenerator(name = "languages_id_seq", sequenceName = "languages_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languages_id_seq")
	@Column(name = "id")
	@Min(value = 0, message = "ID must be minimum 0.")
	private long id;

	@Column(name = "name", nullable = false, unique = true)
	@NotBlank(message = "Language name must exist.")
	private String name;

	@Column(name = "remarks")
	private String remarks;

	public LanguageEntity() {
	}

	public LanguageEntity(@NonNull String name, String remarks) {
		this.name = name;
		this.remarks = remarks;
	}

	public LanguageEntity(long id, @NonNull String name, String remarks) {
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
		LanguageEntity that = (LanguageEntity) o;
		return id == that.id && name.equals(that.name) && Objects.equals(remarks, that.remarks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, remarks);
	}

	@Override
	public String toString() {
		return "LanguageEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}

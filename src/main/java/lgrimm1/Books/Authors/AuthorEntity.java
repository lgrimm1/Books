package lgrimm1.Books.Authors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.*;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.*;

import java.util.*;

/**
 * This class represents the Author entity.<p>
 */
@Entity
@Table(name = "authors")
@Validated
public class AuthorEntity {

	@Id
	@SequenceGenerator(name = "authors_id_seq", sequenceName = "authors_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authors_id_seq")
	@Column(name = "id")
	@Min(value = 0, message = "ID must be minimum 0.")
	private long id;

	/**
	 * Family name or company name of the author or editor.
	 */
	@Column(name = "family_name", nullable = false)
	@NotBlank(message = "Family (company) name must exist.")
	private String familyName;

	/**
	 * Given name(s) of author or editor, optional.
	 */
	@Column(name = "given_name")
	private String givenName;

	/**
	 * List of books the author or editor wrote or edited.
	 */
	@Column(name = "books")
	private List<Long> books;

	@Column(name = "remarks")
	private String remarks;

	public AuthorEntity() {
	}

	public AuthorEntity(@NonNull String familyName, String givenName, List<Long> books, String remarks) {
		this.familyName = familyName;
		this.givenName = givenName;
		this.books = books;
		this.remarks = remarks;
	}

	public AuthorEntity(long id, @NonNull String familyName, String givenName, List<Long> books, String remarks) {
		this.id = id;
		this.familyName = familyName;
		this.givenName = givenName;
		this.books = books;
		this.remarks = remarks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(@NonNull String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public List<Long> getBooks() {
		return books;
	}

	public void setBooks(List<Long> books) {
		this.books = books;
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
		AuthorEntity that = (AuthorEntity) o;
		return id == that.id && familyName.equals(that.familyName) && Objects.equals(givenName, that.givenName) && Objects.equals(books, that.books) && Objects.equals(remarks, that.remarks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, familyName, givenName, books, remarks);
	}

	@Override
	public String toString() {
		return "AuthorEntity{" +
				"id=" + id +
				", lastName='" + familyName + '\'' +
				", firstName='" + givenName + '\'' +
				", books=" + books +
				", remarks='" + remarks + '\'' +
				'}';
	}
}

package net.bolbat.snippets.serialization.protobuf;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.google.protobuf.InvalidProtocolBufferException;

import net.bolbat.snippets.serialization.protobuf.library.Author;
import net.bolbat.snippets.serialization.protobuf.library.Book;
import net.bolbat.snippets.serialization.protobuf.library.Category;

public class LibraryTest {

	@Test
	public void exampleUsage() throws InvalidProtocolBufferException {
		final Author author = Author.newBuilder()
				.setId(randomId())
				.setName("Yuval Noah Harari")
				.build();
		final Category category = Category.newBuilder()
				.setId(randomId())
				.setName("Anthropology")
				.build();
		final Book book = Book.newBuilder()
				.setId(randomId())
				.setName("Sapiens: A Brief History of Humankind")
				.addAuthors(author.getId())
				.addCategories(category.getId())
				.build();

		final byte[] serializedBook = book.toByteArray();
		final Book restoredBook = Book.parseFrom(serializedBook);

		assertThat(restoredBook, equalTo(book));
		assertThat(restoredBook.getAuthorsList().size(), equalTo(1));
		assertThat(restoredBook.getAuthorsList(), contains(author.getId()));
		assertThat(restoredBook.getCategoriesList().size(), equalTo(1));
		assertThat(restoredBook.getCategoriesList(), contains(category.getId()));
	}

	private String randomId() {
		return UUID.randomUUID().toString();
	}

}

import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {

    private static BookRepository bookRepository;
    private static Connection connection= DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();
    @BeforeAll
    public static void setup()
    {
         bookRepository=new BookRepositoryMySQL(connection);
    }

    @Test
    public void findAll()
    {
        bookRepository.deleteAll();
        List<Book> books=bookRepository.findAll();
        assertEquals(0,books.size());
    }

    @Test
    public void findById()
    {
        bookRepository.deleteAll();
        final Optional<Book> book=bookRepository.findById(1L);
        assertTrue(book.isEmpty());
    }
    @Test
    public void save()
    {
        //assertTrue(bookRepository.save(new BookBuilder().setAuthor("Ana").setPublishedDate(LocalDate.of(2020,11,30)).setTitle("Viata").build()));
    }
    @Test
    public void delete()
    {
        int id=bookRepository.save(new BookBuilder().setAuthor("Ana").setPublishedDate(LocalDate.of(2020,11,30)).setTitle("Viata").build());
        assertTrue(bookRepository.delete(new BookBuilder().setId((long) id).build()));
    }


}

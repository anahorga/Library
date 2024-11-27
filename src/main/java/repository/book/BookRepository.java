package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    int save(Book book);
    boolean delete(Book book);
    void deleteAll();
    boolean sell(Book book);

}

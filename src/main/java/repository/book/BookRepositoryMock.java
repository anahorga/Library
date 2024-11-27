package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {

    private  final List<Book>books;
    public BookRepositoryMock()
    {
        books=new ArrayList<>();
    }
    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public int save(Book book) {

        books.add(book);
        return 1;
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void deleteAll() {
        books.clear();
    }

    @Override
    public boolean sell(Book book) {
        return false;
    }
}

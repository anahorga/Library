import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {

    public static void main(String[]args)
    {
        //System.out.println("Hello world");

        Book book=new BookBuilder()
                .setAuthor("Ioan Slavici")
                .setTitle("Moara cu noroc")
                .setPublishedDate( LocalDate.of(1940,10,10))
                .build();

        Book book2=new BookBuilder()
                .setAuthor("Liviu Rebreanu")
                .setTitle("Ion")
                .setPublishedDate( LocalDate.of(1919,10,20))
                .build();
        //System.out.println(book);
//
//        BookRepository bookRepo=new BookRepositoryMock();
//
//        bookRepo.save(book);
//        bookRepo.save(book2);
//
//        System.out.println(bookRepo.findAll());
//
//        bookRepo.delete(book);
//
//
//        System.out.println(bookRepo.findAll());
//         bookRepo.deleteAll();
//        System.out.println(bookRepo.findAll());

        Connection connection= DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository=new BookRepositoryMySQL(connection);
         BookService bookService=new BookServiceImpl(bookRepository);

        bookRepository.deleteAll();

        bookService.save(book);
        System.out.println(bookService.findAll());
        bookService.save(book2);
        System.out.println(bookService.findAll());

        bookService.delete(book);

        bookService.save(book);
        System.out.println(bookService.findAll());



    }
}

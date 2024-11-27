package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static volatile EmployeeComponentFactory instance;
    private static Stage stage;
    private static Boolean componentsForTest;

    public static EmployeeComponentFactory getInstance(Boolean aComponentsForTest, Stage aPrimaryStage){

        if (instance == null) {
            synchronized(EmployeeComponentFactory.class) {
                if (instance == null) {
                    stage=aPrimaryStage;
                    componentsForTest=aComponentsForTest;
                    instance = new EmployeeComponentFactory(componentsForTest, stage);
                }
            }
        }
        return instance;
    }

    private EmployeeComponentFactory(Boolean componentsForTest, Stage primaryStage)
    {
        Connection connection= DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository= new BookRepositoryMySQL(connection);
        this.bookService=new BookServiceImpl(bookRepository);
        List<BookDTO> bookDTOs= BookMapper.covertBookListToBookDTOList(bookService.findAll());
        this.bookView=new BookView(primaryStage,bookDTOs);
        this.bookController=new BookController(bookView,bookService);

    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static Stage getStage() {
        return stage;
    }

    public static Boolean getComponentsForTest() {
        return componentsForTest;
    }
}

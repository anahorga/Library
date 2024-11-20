package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.CustomerComponentFactory;
import launcher.LoginComponentFactory;
import mapper.BookMapper;
import service.book.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class BookController {

    private final BookView bookView;
    private final BookService bookService;

    public BookController(BookView bookView,BookService bookService)
    {
        this.bookService=bookService;
        this.bookView=bookView;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addLogOutButtonListener(new LogOutButtonListener());
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {

            BookDTO bookDTO=(BookDTO)bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO!=null)
            {
                boolean deletionSuccessful=bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if(deletionSuccessful)
                {
                    bookView.addDisplayAlertMessage("Delete successful","Book deleted","Book was successfully deleted from the database");
                    bookView.removeBookFromObservableList(bookDTO);
                }else {
                    bookView.addDisplayAlertMessage("Delete error","Problem at deleting book","There was a problem with the database. Please try again");
                }

            }else {
                bookView.addDisplayAlertMessage("Delete error","Problem at deleting book","You must select a book before pressing the delete button");
            }

        }
    }
    private class SaveButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {

            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            if(title.isEmpty()||author.isEmpty())
            {
                bookView.addDisplayAlertMessage("Save error","Problem at Author or Title fields","Can not have an empty Title or Author field");
            }else {
                BookDTO bookDTO=new BookDTOBuilder().setAuthor(author).setTitle(title).build();
                boolean saveBook=bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                if(saveBook)
                {
                    bookView.addDisplayAlertMessage("Save successful","Added book","Book was successfully added to the database");
                    bookView.addBookToObservableList(bookDTO);
                    bookView.getTitleTextField().setText("");
                    bookView.getAuthorTextField().setText("");
                }
                else
                {
                    bookView.addDisplayAlertMessage("Save error","Problem at adding Book to","There was a problem at adding the book to the database. Please try again.");
                }
            }

        }
    }
    private class LogOutButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {

            //trebe implementat si logica din spate din service (logout din AuthenticationServiceImpl)

            bookView.addDisplayAlertMessage(
                    "Logout successful",
                    "You have been logged out",
                    "Redirecting to login screen..."
            );

            LoginComponentFactory.getStage().setScene(LoginComponentFactory.getInstance(CustomerComponentFactory.getComponentsForTest(),CustomerComponentFactory.getStage())
                    .getLoginView().getScene());

        }
    }



}

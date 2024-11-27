package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.EmployeeComponentFactory;
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
        this.bookView.addSellButtonListener(new SellButtonListener());
    }
    private class SellButtonListener implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {

            BookDTO bookDTO=(BookDTO)bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO!=null) {
                //aici verific si daca am destul stock

                if (bookDTO.getStock() == 0) {
                    bookView.addDisplayAlertMessage("Sell error", "Problem at selling the book", "There book is out of stock. Thank you for understanding!");
                } else {
                    boolean sellSuccessful = bookService.sell(BookMapper.convertBookDTOToBook(bookDTO));
                    if (sellSuccessful) {
                        bookDTO.setStock(bookDTO.getStock()-1);
                         bookView.addDisplayAlertMessage("Sell successful","Book sold","Book was successfully sold");
                        bookView.editObservableList(bookDTO);

                    } else {
                        bookView.addDisplayAlertMessage("Sell error","Problem at selling book","There was a problem with the database. Please try again");
                    }
                }


            }
            else {
                bookView.addDisplayAlertMessage("Sell error","Problem at selling book","You must select a book before pressing the sell button");

            }

        }
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
    public boolean isInteger(String input) {
        if (input == null || input.isEmpty()) {
            return false; // Șirul este gol sau null
        }
        try {
            Integer.parseInt(input);
            return true; // Conversia a reușit, este un int
        } catch (NumberFormatException e) {
            return false; // Conversia a eșuat, nu este un int
        }
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {

            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String stock = bookView.getStock();
            String price = bookView.getPrice();

            if(title.isEmpty()||author.isEmpty()||stock.isEmpty()||price.isEmpty())
            {
                bookView.addDisplayAlertMessage("Save error","Problem at book fields","Can not have an empty Title, Author, Stock or Price field");
            }
            else if(!isInteger(stock)||!isInteger(price))
            {
                bookView.addDisplayAlertMessage("Save error","Problem at Price and Stock fields","Price and Stock fields must numbers");

            }
            else {
                BookDTO bookDTO=new BookDTOBuilder().setAuthor(author).setTitle(title)
                        .setStock(Integer.parseInt(stock))
                        .setPrice(Integer.parseInt(price)).build();
                int saveBook=bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                if(saveBook>0)
                {
                    bookView.addDisplayAlertMessage("Save successful","Added book","Book was successfully added to the database");
                    bookDTO.setId((long) saveBook);
                    bookView.addBookToObservableList(bookDTO);
                    bookView.getTitleTextField().setText("");
                    bookView.getAuthorTextField().setText("");
                    bookView.getPriceTextField().setText("");
                    bookView.getStockTextField().setText("");
                }
                else
                {
                    bookView.addDisplayAlertMessage("Save error","Problem at adding Book to the database","There was a problem at adding the book to the database. Please try again.");
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

            LoginComponentFactory.getStage().setScene(LoginComponentFactory.getInstance(EmployeeComponentFactory.getComponentsForTest(), EmployeeComponentFactory.getStage())
                    .getLoginView().getScene());

        }
    }



}

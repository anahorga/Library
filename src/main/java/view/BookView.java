package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.BookDTO;


import java.util.List;


public class BookView {

    private TableView bookTableView;

    private final ObservableList<BookDTO> booksObservableList;

    private TextField authorTextField;
    private TextField titleTextField;
    private TextField stockTextField;
    private TextField priceTextField;

    private Label authorLabel;
    private Label titleLabel;
    private Label stockLabel;
    private Label priceLabel;

    private Button saveButton;
    private Button deleteButton;
    private Button logOutButton;
    private Button sellButton;
    private final Scene scene;
    public BookView(Stage primaryStage, List<BookDTO> books) {

        primaryStage.setTitle("Library");
        GridPane gridPane=new GridPane();
        initializedGridPane(gridPane);

         scene=new Scene(gridPane,720,480);
        primaryStage.setScene(scene);

        //nu avem voie sa facem alta atribuire
        //aici legam de ObservableList
        booksObservableList= FXCollections.observableList(books);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    public TextField getStockTextField() {
        return stockTextField;
    }

    public TextField getPriceTextField() {
        return priceTextField;
    }

    public TextField getAuthorTextField() {
        return authorTextField;
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    private void initSaveOptions(GridPane gridPane) {

        titleLabel=new Label("Title");
        gridPane.add(titleLabel,1,1);

        titleTextField=new TextField();
        gridPane.add(titleTextField,2,1);

        authorLabel=new Label("Author");
        gridPane.add(authorLabel,3,1);

        authorTextField=new TextField();
        gridPane.add(authorTextField,4,1);

        stockLabel=new Label("Stock");
        gridPane.add(stockLabel,1,2);

        stockTextField=new TextField();
        gridPane.add(stockTextField,2,2);

        priceLabel=new Label("Price");
        gridPane.add(priceLabel,3,2);

        priceTextField=new TextField();
        gridPane.add(priceTextField,4,2);

        saveButton=new Button("Save");
        gridPane.add(saveButton,5,2);

        deleteButton=new Button("Delete");
        gridPane.add(deleteButton,6,2);

        logOutButton=new Button("LogOut");
        gridPane.add(logOutButton,7,2);

        sellButton=new Button("Sell Book");
        gridPane.add(sellButton,8,2);
    }

    private void initTableView(GridPane gridPane) {
        bookTableView=new TableView<BookDTO>();

        bookTableView.setPlaceholder(new Label("No books to display"));


        //data binding
        TableColumn<BookDTO,String> titleColumn=new TableColumn<BookDTO,String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO,String> authorColumn=new TableColumn<BookDTO,String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO,Long> idColumn=new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BookDTO,Integer>stockColumn=new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<BookDTO,Integer>priceColumn=new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        bookTableView.getColumns().addAll(idColumn,titleColumn,authorColumn,priceColumn,stockColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView,0,0,5,1);
    }

    private void initializedGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener)
    {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener)
    {
        deleteButton.setOnAction(deleteButtonListener);
    }
    public void addLogOutButtonListener(EventHandler<ActionEvent> logOutButtonListener)
    {
        logOutButton.setOnAction(logOutButtonListener);
    }
    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener)
    {
        sellButton.setOnAction(sellButtonListener);
    }
  public void addDisplayAlertMessage(String title,String header, String content)
  {
      Alert alert=new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(title);
      alert.setHeaderText(header);
      alert.setContentText(content);

      alert.showAndWait();
  }

  public String getTitle()
  {
      return titleTextField.getText();
  }

    public String getAuthor()
    {
        return authorTextField.getText();
    }

    public String getStock()
    {
        return stockTextField.getText();
    }
    public String getPrice()
    {
        return priceTextField.getText();
    }

    public void addBookToObservableList(BookDTO bookDTO)
    {
        this.booksObservableList.add(bookDTO);
    }
    public void removeBookFromObservableList(BookDTO bookDTO)
    {
        this.booksObservableList.remove(bookDTO);
    }

    public void editObservableList(BookDTO updatedBookDTO)
    {
        for (int i = 0; i < booksObservableList.size(); i++) {
            BookDTO book = booksObservableList.get(i);
            if (book.getId().equals(updatedBookDTO.getId())) {
                book.setStock(updatedBookDTO.getStock());
                booksObservableList.set(i, book);
                break;
            }
        }

    }
    public TableView getBookTableView()
    {
        return bookTableView;
    }

    public Scene getScene() {
        return scene;
    }

}

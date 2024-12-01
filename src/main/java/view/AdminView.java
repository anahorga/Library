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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.model.UserDTO;

import java.util.List;

public class AdminView {

        private TableView userTableView;

        private final ObservableList<UserDTO> userObservableList;

        private TextField passwordTextField;

        private TextField usernameTextField;

        private Label usernameLabel;
        private Label passwordLabel;

        private Button addEmployeeButton;

        private Button logOutButton;
        private Button generatePDFButton;
        private final Scene scene;
         private Text actiontarget;
        public AdminView(Stage primaryStage, List<UserDTO> users) {

            primaryStage.setTitle("Library");
            GridPane gridPane=new GridPane();
            initializedGridPane(gridPane);

            scene=new Scene(gridPane,720,480);
            primaryStage.setScene(scene);

            //nu avem voie sa facem alta atribuire
            //aici legam de ObservableList
            userObservableList= FXCollections.observableList(users);
            initTableView(gridPane);

            initSaveOptions(gridPane);

            primaryStage.show();
        }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    private void initSaveOptions(GridPane gridPane) {

            usernameLabel=new Label("Username");
            gridPane.add(usernameLabel,1,1);

            usernameTextField=new TextField();
            gridPane.add(usernameTextField,3,1);

            passwordLabel=new Label("Password");
            gridPane.add(passwordLabel,4,1);

            passwordTextField=new TextField();
            gridPane.add(passwordTextField,6,1);

            addEmployeeButton=new Button("Add employee");
            gridPane.add(addEmployeeButton,1,2);


            logOutButton=new Button("LogOut");
            gridPane.add(logOutButton,5,2);

            generatePDFButton=new Button("Generate Report");
            gridPane.add(generatePDFButton,3,2);
        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget, 3, 3);
        }

        private void initTableView(GridPane gridPane) {
            userTableView=new TableView<UserDTO>();

            userTableView.setPlaceholder(new Label("No users to display"));


            //data binding
            TableColumn<UserDTO,String> usernameColumn=new TableColumn<>("Username");
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

            TableColumn<UserDTO,String> idColumn=new TableColumn<>("Id");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<UserDTO,String> roleColumn=new TableColumn<>("Role");
            roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));


            userTableView.getColumns().addAll(idColumn,usernameColumn,roleColumn);

            userTableView.setItems(userObservableList);

            gridPane.add(userTableView,0,0,5,1);
        }

        private void initializedGridPane(GridPane gridPane) {
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(25,25,25,25));
        }

        public void addAddEmployeeButtonListener(EventHandler<ActionEvent> addEmployeeButtonListener)
        {
           addEmployeeButton.setOnAction(addEmployeeButtonListener);
        }

        public void addLogOutButtonListener(EventHandler<ActionEvent> logOutButtonListener)
        {
            logOutButton.setOnAction(logOutButtonListener);
        }
        public void addGeneratePDFButtonListener(EventHandler<ActionEvent> generatePDFButtonListener)
        {
            generatePDFButton.setOnAction(generatePDFButtonListener);
        }
        public void addDisplayAlertMessage(String title,String header, String content)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);

            alert.showAndWait();
        }


        public void addUserToObservableList(UserDTO userDTO)
        {
            this.userObservableList.add(userDTO);
        }
        public void setActionTargetText(String text){ this.actiontarget.setText(text);}


        public TableView getUserTableView()
        {
            return userTableView;
        }

        public Scene getScene() {
            return scene;
        }
        public String getUsername()
        {
            return usernameTextField.getText();
        }
        public String getPassword()
        {
            return passwordTextField.getText();
        }


}

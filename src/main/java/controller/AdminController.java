package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.LoginComponentFactory;
import model.validation.Notification;
import service.user.UserService;
import view.AdminView;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

public class AdminController {
    private final AdminView adminView;
    private final UserService userService;

    public AdminController(AdminView adminView, UserService userService)
    {
        this.adminView=adminView;
        this.userService=userService;

        this.adminView.addLogOutButtonListener(new AdminController.LogOutButtonListener());
        this.adminView.addAddEmployeeButtonListener(new AdminController.AddEmployeeButtonListener());
        this.adminView.addGeneratePDFButtonListener(new AdminController.GeneratePDFButtonListener());
    }

    private class AddEmployeeButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {

            String username=adminView.getUsername();
            String password=adminView.getPassword();

            if(username.isEmpty()||password.isEmpty())
            {
                adminView.addDisplayAlertMessage("Add employee error","Problem at username and password fields","Can not have an empty Username or Password field");
            }
            else {
                UserDTO userDTO=new UserDTOBuilder().setUsername(username).setRole("employee")
                        .build();
                Notification<Boolean> registerNotification=userService.registerEmployee(username,password);
                if (registerNotification.hasErrors()) {
                    adminView.setActionTargetText(registerNotification.getFormattedErrors());
                } else {
                    adminView.addDisplayAlertMessage("Add successful","Added employee","Employee was successfully added to the database");
                    adminView.addUserToObservableList(userDTO);
                    adminView.getPasswordTextField().setText("");
                    adminView.getUsernameTextField().setText("");
                    adminView.setActionTargetText("");
                }
            }
        }
    }
    private class GeneratePDFButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {

        }
    }


    private class LogOutButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {

            adminView.addDisplayAlertMessage(
                    "Logout successful",
                    "You have been logged out",
                    "Redirecting to login screen..."
            );

            LoginComponentFactory.getStage().setScene(LoginComponentFactory.getInstance(AdminComponentFactory.getComponentsForTest(), AdminComponentFactory.getStage())
                    .getLoginView().getScene());

        }
    }


}

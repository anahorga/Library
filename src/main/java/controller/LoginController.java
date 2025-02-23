package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import launcher.EmployeeComponentFactory;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import launcher.LoginComponentFactory;
import model.Role;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.LoginView;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private User user;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                user=loginNotification.getResult();
                Role role=user.getRoles().get(0);
                if(role.getRole().equals("employee")) {
                    EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage()).getBookController().setUser(user);
                    EmployeeComponentFactory.getStage().setScene(EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage())
                            .getBookView().getScene());
                }
                else
                    if(role.getRole().equals("administrator"))
                    {
                       AdminComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(),LoginComponentFactory.getStage());
                        AdminComponentFactory.getStage().setScene(AdminComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage())
                                .getAdminView().getScene());
                   }
                    else System.out.println("de implementat customer view");
                loginView.setActionTargetText("LogIn Successfull!");
                loginView.setActionTargetText("");
                loginView.getPasswordField().setText("");
                loginView.getUserTextField().setText("");
                //System.out.println(loginNotification.getResult().getId());
            }
        }
    }


    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }
}
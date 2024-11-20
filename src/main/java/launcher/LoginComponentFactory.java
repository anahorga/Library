package launcher;


import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;


import java.sql.Connection;
import java.util.List;

public class LoginComponentFactory {

    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthenticationService authenticationService;
     private final UserRepository userRepository;

     private final RightsRolesRepository rightsRolesRepository;
     private static Stage stage;
     private static Boolean componentsForTest;


    private static volatile LoginComponentFactory instance;

    public static LoginComponentFactory getInstance(Boolean aComponentsForTest, Stage aStage){

        if (instance == null) {
            synchronized(LoginComponentFactory.class) {
                if (instance == null) {
                    stage=aStage;
                    componentsForTest=aComponentsForTest;
                    instance = new LoginComponentFactory(componentsForTest, stage);
                }
            }
        }
        return instance;
    }

    private LoginComponentFactory(Boolean componentsForTest, Stage primaryStage)
    {
        Connection connection= DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
         this.rightsRolesRepository=new RightsRolesRepositoryMySQL(connection);
         this.userRepository=new UserRepositoryMySQL(connection,rightsRolesRepository);
         this.authenticationService=new AuthenticationServiceImpl(userRepository,rightsRolesRepository);
         this.loginView=new LoginView(primaryStage);
         this.loginController=new LoginController(loginView,authenticationService);

    }

    public AuthenticationService getAuthenticationService()
    {
        return authenticationService;
    }
    public UserRepository getUserRepository()
    {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository()
    {
        return rightsRolesRepository;
    }

    public LoginView getLoginView()
    {
        return loginView;

    }
    public LoginController getLoginController()
    {
        return loginController;
    }
     public static Stage getStage()
     {
         return stage;
     }
     public static Boolean getComponentsForTests()
     {
         return componentsForTest;
     }

}

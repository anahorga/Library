package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.UserMapper;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.AdminView;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {

    private final UserService userService;
    private final UserRepository userRepository;

    private final RightsRolesRepository rightsRolesRepository;
    private static Stage stage;
    private static Boolean componentsForTest;

    private static volatile AdminComponentFactory instance;

    private final AdminView adminView;
    private final AdminController adminController;

    public static AdminComponentFactory getInstance(Boolean aComponentsForTest, Stage aStage) {

        if (instance == null) {
            synchronized(AdminComponentFactory.class) {
                if (instance == null) {
                    stage=aStage;
                    componentsForTest=aComponentsForTest;
                    instance = new AdminComponentFactory(componentsForTest, stage);
                }
            }
        }
        return instance;
    }

    private AdminComponentFactory(Boolean componentsForTest, Stage stage)
    {
        Connection connection= DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository=new RightsRolesRepositoryMySQL(connection);
        this.userRepository=new UserRepositoryMySQL(connection,rightsRolesRepository);
        this.userService=new UserServiceImpl(userRepository,rightsRolesRepository);

        List<UserDTO> userDTOs= UserMapper.convertUserListToUserDTOList(userService.findAll());
        this.adminView=new AdminView(stage,userDTOs);
        this.adminController=new AdminController(adminView,userService);

    }

    public static Stage getStage() {
        return stage;
    }

    public static Boolean getComponentsForTest() {
        return componentsForTest;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public AdminController getAdminController() {
        return adminController;
    }
}

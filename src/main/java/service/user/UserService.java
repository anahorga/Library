package service.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserService {

    Notification<Boolean> registerEmployee(String username, String password);

    List<User> findAll();
}

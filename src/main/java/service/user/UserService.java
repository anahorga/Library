package service.user;

import model.Report;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserService {

    Notification<Integer> registerEmployee(String username, String password);

    List<User> findAll();

    Notification<Report> generateReport(User user);

}

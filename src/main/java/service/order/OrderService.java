package service.order;

import model.Book;
import model.User;

public interface OrderService {

    boolean makeOrder(Book book, User user);
}

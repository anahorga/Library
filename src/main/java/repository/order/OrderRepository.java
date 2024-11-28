package repository.order;

import model.Book;
import model.User;

public interface OrderRepository {

    boolean makeOrder(Book book, User user);
}

package service.order;

import model.Book;
import model.User;
import repository.order.OrderRepository;

public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public boolean makeOrder(Book book, User user) {
        return orderRepository.makeOrder(book,user);
    }
}

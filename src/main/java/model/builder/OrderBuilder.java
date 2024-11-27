package model.builder;

import model.Order;

import java.time.LocalDateTime;

public class OrderBuilder {


        private Order order;

        public OrderBuilder()
        {
            order=new Order();
        }
        public OrderBuilder setId(Long id)
        {
            order.setId(id);
            return this;
        }

        public OrderBuilder setUser_id(Long user_id)
        {
            order.setUser_id(user_id);
            return this;
        }
        public OrderBuilder setBook_id(Long book_id)
        {
            order.setBook_id(book_id);
            return this;
        }
        public OrderBuilder setPrice(int price)
        {
            order.setPrice(price);
            return this;
        }
        public OrderBuilder setStock(int stock)
        {
            order.setStock(stock);
            return this;
        }
        public OrderBuilder setDate(LocalDateTime date)
        {
            order.setOrder_date(date);
            return this;

        }

        public Order build()
        {
            return order;
        }

}

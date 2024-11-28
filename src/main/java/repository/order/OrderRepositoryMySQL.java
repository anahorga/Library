package repository.order;

import model.Book;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderRepositoryMySQL implements OrderRepository{

    private final Connection connection;

    public OrderRepositoryMySQL(Connection conn)
    {
        this.connection=conn;
    }
    @Override
    public boolean makeOrder(Book book, User user) {
        String sql="INSERT INTO `order`(order_date,user_id,book_id,stock,price) values(?,?,?,?,?);";

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2,user.getId().intValue());
            preparedStatement.setInt(3,book.getId().intValue());
            preparedStatement.setInt(4,1);
            preparedStatement.setInt(5,book.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted>0;


        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }
}

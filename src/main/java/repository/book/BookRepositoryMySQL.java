package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository{

    private final Connection connection;

    public BookRepositoryMySQL(Connection conn)
    {
        this.connection=conn;
    }
    @Override
    public List<Book> findAll() {

        String sql= "SELECT * FROM BOOK";
       List <Book> books=new ArrayList<>();

       try {
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sql);
           while(resultSet.next())
           {
               books.add(getBookFromResultSet(resultSet));
           }

       }
       catch(SQLException e)
       {
           e.printStackTrace();
       }

        return books;
    }
    private Book getBookFromResultSet (ResultSet resultSet) throws SQLException
    {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setAuthor(resultSet.getString("author"))
                .setTitle(resultSet.getString("title"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setPrice(resultSet.getInt("price"))
                .setStock(resultSet.getInt("stock")).build();
    }

    @Override
    public Optional<Book> findById(Long id) {

        Optional<Book> book=Optional.empty();
        String sql= "SElECT * FROM book WHERE id="+ id +";";

        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);

            if(resultSet.next())
            {
                book=Optional.of(getBookFromResultSet(resultSet));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public int save(Book book) {
        //String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() +"\', \'" + book.getTitle()+"\', \'" + book.getPublishedDate() + "\' );";

        //pt a preveni atacurile SQL Injection
        String newSql = "INSERT INTO book (author, title, publishedDate, stock, price) VALUES (?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));

            preparedStatement.setInt(4, book.getStock());
            preparedStatement.setFloat(5, book.getPrice());
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // ID-ul generat
                    }
                }
            }

        } catch (SQLException e){
            e.printStackTrace();

        }return -1;
    }

    @Override
    public boolean delete(Book book) {


        String newSql = "DELETE FROM book WHERE id = ?";

        try{

            PreparedStatement statement = connection.prepareStatement(newSql);
            statement.setLong(1,book.getId());
            //statement.setString(2,book.getAuthor());
            //statement.setString(3,book.getTitle());

            int rowsAffected=statement.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean sell(Book book)
    {
        String sql = "UPDATE book SET stock = ? WHERE id = ?";
        try{

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,book.getStock()-1);
            statement.setLong(2,book.getId());

            int rowsAffected=statement.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

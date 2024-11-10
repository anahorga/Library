package repository;

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
                .build();
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
    public boolean save(Book book) {
        //String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() +"\', \'" + book.getTitle()+"\', \'" + book.getPublishedDate() + "\' );";

        //pt a preveni atacurile SQL Injection
        String newSql = "INSERT INTO book VALUES(null, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));

            int rowsInserted = preparedStatement.executeUpdate();

            return (rowsInserted != 1) ? false : true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Book book) {


        String newSql = "DELETE FROM book WHERE author = ? AND title = ?";

        try{

            PreparedStatement statement = connection.prepareStatement(newSql);
            statement.setString(1,book.getAuthor());
            statement.setString(2,book.getTitle());

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

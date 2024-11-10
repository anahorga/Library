package repository;

import model.Book;
import model.builder.BookBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String sql= "SEECT * FROM book WHERE id="+ id +";";

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
        String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() +"\', \'" + book.getTitle()+"\', \'" + book.getPublishedDate() + "\' );";


        try{
            Statement statement=connection.createStatement();
             statement.executeUpdate(newSql);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM book WHERE author=\'" + book.getAuthor() +"\' AND title=\'" + book.getTitle()+"\';";


        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
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

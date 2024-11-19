package database;

import java.sql.*;

public class JDBConnectionWrapper {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    private static final String USER = "root";
    private static final String PASSWORD="Nerodul8#";

    private static final int TIMEOUT = 5;

    private String Schema;
    private Connection connection;

    public JDBConnectionWrapper(String schema) {

        try{
            Class.forName(JDBC_DRIVER);
            Schema=schema;
            connection= DriverManager.getConnection(DB_URL + schema,USER,PASSWORD);
            createTables();
        } catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private boolean verifyColumn(String column_name, String table_name, String table_schema) throws SQLException {
        String sql = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = ? AND TABLE_NAME = ? AND TABLE_SCHEMA = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // Set the values for the placeholders
        preparedStatement.setString(1, column_name);
        preparedStatement.setString(2, table_name);
        preparedStatement.setString(3, table_schema);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next(); // Returns true if a row is found, otherwise false
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS book(" +
                " id BIGINT NOT NULL AUTO_INCREMENT," +
                " author VARCHAR(500) NOT NULL," +
                " title VARCHAR(500) NOT NULL," +
                " publishedDate DATETIME DEFAULT NULL," +
                " PRIMARY KEY (id)," +
                " UNIQUE KEY id_UNIQUE (id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        statement.execute(createTableSQL);

        // Adaugă coloanele `stock` și `price` dacă lipsesc
        if(!verifyColumn("stock","book",Schema))
        {
            String addColumnsSQL = "ALTER TABLE book " +
                    "ADD COLUMN stock INT DEFAULT 0; " ;
            statement.execute(addColumnsSQL);
        }
        if(!verifyColumn("price","book",Schema))
        {
            String addColumnsSQL = "ALTER TABLE book " +
                    "ADD COLUMN price INT DEFAULT 0; " ;
            statement.execute(addColumnsSQL);
        }
    }

    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }
    public Connection getConnection()
    {
        return connection;
    }
}

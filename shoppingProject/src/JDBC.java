import java.sql.*;

public class JDBC
{

    //Set up JDBC
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/shop";

    static final String USER = "root";
    static final String PASS = "password";

    Connection connection = null;
    Statement statement = null;


    //When querying the database run this
    public ResultSet runSQLQuery(String sql)
    {

        ResultSet resultSet = null;

        try
        {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();


            resultSet = statement.executeQuery(sql);


            return resultSet;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException c)
        {
            c.printStackTrace();
        }

        return resultSet;

    }


    //When making changes to the database run this
    public void runSQLUpdate(String sql)
    {

        ResultSet resultSet = null;

        try
        {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            statement.executeUpdate(sql);

            statement.close();
            connection.close();


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException c)
        {
            c.printStackTrace();
        }


    }


}







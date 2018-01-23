import java.sql.*;
import java.util.*;

public class Orders
{

    private static int NUMBER_OF_ORDERS;
    private int orderNumber = 0;
    Customers customer;
    HashMap<String, Integer> productsBought = new HashMap<>();


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/shop";

    static final String USER = "root";
    static final String PASS = "password1";



    public Orders(Customers customer, Products... products)
    {

        Connection connection = null;
        Statement statement = null;

        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            //Execute a query
            System.out.println("Creating statement...");
            statement = connection.createStatement();
            String sql;
            sql = "SELECT name FROM products WHERE stock = 20";
            ResultSet resultSet = statement.executeQuery(sql);

            //Extract data
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
            }

            //clean up environment
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        this.customer = customer;
        NUMBER_OF_ORDERS++;
        this.orderNumber = NUMBER_OF_ORDERS;
        for(Products product : products) {
            if(productsBought.containsKey(product.getProductName()))
            {
                productsBought.put(product.getProductName(), productsBought.get(product.getProductName()) + 1);
            } else {
                productsBought.put(product.getProductName(), 1);
            }
        }

        System.out.println(productsBought);
        System.out.println(NUMBER_OF_ORDERS);

    }








}

import java.sql.*;
import java.util.*;

public class Orders
{

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


            statement = connection.createStatement();
            String sql;

            this.customer = customer;
            sql = String.format("INSERT INTO orders (CustomerName, postCode, number) VALUES (\'%s\', \'%s\', \'%s\');",
                    customer.getName(), customer.getPostcode(), customer.getHouseNumber());

            //Execute a query
            statement.executeUpdate(sql);

            for(Products product : products)
            {
                 if(productsBought.containsKey(product.getProductName()))
                {
                    productsBought.put(product.getProductName(), productsBought.get(product.getProductName()) + 1);
                } else {
                    productsBought.put(product.getProductName(), 1);
                }

            }

            for(Products product1 : products)
            {
                if(productsBought.get(product1.getProductName()) == null)
                {
                    continue;
                }
                statement = connection.createStatement();



               String sql1 = "INSERT INTO order_product (id_order, quantity, product_id) VALUES ((select MAX(orderNumber) FROM orders), '" + productsBought.get(product1.getProductName()) + "', (select id FROM products WHERE name = '" + product1.getProductName() + "'));";

                statement.executeUpdate(sql1);
                productsBought.remove(product1.getProductName());

            }






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




    }








}

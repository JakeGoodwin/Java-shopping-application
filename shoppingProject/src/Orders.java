import java.sql.*;
import java.util.*;

public class Orders
{

    Customers customer;
    HashMap<String, Integer> productsBought = new HashMap<>();


    //set up JDBC
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/shop";

    //this is something that if made live would need to be removed from the code - possibly a future task
    static final String USER = "root";
    static final String PASS = "password1";


    //constructor, does all of the work since we have 1 task to complete
    public Orders(Customers customer, ArrayList<Products> productsArrayList)
    {

        Connection connection = null;
        Statement statement = null;

        this.customer = customer;

        try
        {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            statement = connection.createStatement();
            String sqlInsertOrder;

            //Database update for adding order
            sqlInsertOrder = String.format("INSERT INTO orders (CustomerName, postCode, number) VALUES (\'%s\', \'%s\', \'%s\');",
                    customer.getName(), customer.getPostcode(), customer.getHouseNumber());

            //Execute the update
            statement.executeUpdate(sqlInsertOrder);

            //Here we calculate the quantity of each product customer has ordered
            for (Products product : productsArrayList)
            {
                if (productsBought.containsKey(product.getProductName()))
                {
                    productsBought.put(product.getProductName(), productsBought.get(product.getProductName()) + 1);
                }
                else
                {
                    productsBought.put(product.getProductName(), 1);
                }

            }

            //make sure that the correct quantity of product is input into order_product and deduct that from the stock
            for (Products product1 : productsArrayList)
            {
                //we remove product after being put in database to stop duplication, this checks so no erorrs are thrown
                if (productsBought.get(product1.getProductName()) == null)
                {
                    continue;
                }

                statement = connection.createStatement();


                //put order in databse and link with product ID and order ID (id_order)
                String sqlInsertOrder_Product = "INSERT INTO order_product (id_order, quantity, product_id) VALUES ((select MAX(orderNumber) FROM orders), '" + productsBought.get(product1.getProductName()) + "', (select id FROM products WHERE name = '" + product1.getProductName() + "'));";

                //Once order is placed update stock
                String sqlUpdateStock = "UPDATE products SET stock  = stock - " + productsBought.get(product1.getProductName()) + " WHERE name = '" + product1.getProductName() + "';";

                //run updates
                statement.executeUpdate(sqlInsertOrder_Product);
                statement.executeUpdate(sqlUpdateStock);

                //remove product from hashmap to stop duplicated
                productsBought.remove(product1.getProductName());

            }

            //close connections
            statement.close();
            connection.close();


        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}

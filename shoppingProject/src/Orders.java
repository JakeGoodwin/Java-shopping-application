import java.sql.*;
import java.util.*;

public class Orders
{

    Customers customer;
    HashMap<String, Integer> productsBought = new HashMap<>();




    //constructor, does all of the work since we have 1 task to complete
    public Orders(Customers customer, ArrayList<Products> productsArrayList)
    {

        JDBC jdbc = new JDBC();
        this.customer = customer;

        String sqlInsertOrder = String.format("INSERT INTO orders (customerName, postCode, number) VALUES (\'%s\', \'%s\', \'%s\');",
                    customer.getName(), customer.getPostcode(), customer.getHouseNumber());

        jdbc.runSQLUpdate(sqlInsertOrder);


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



                //put order in databse and link with product ID and order ID (id_order)
                String sqlInsertOrder_Product = "INSERT INTO order_product (id_order, quantity, product_id) VALUES ((select MAX(orderNumber) FROM orders), '" + productsBought.get(product1.getProductName()) + "', (select id FROM products WHERE name = '" + product1.getProductName() + "'));";
                jdbc.runSQLUpdate(sqlInsertOrder_Product);

                //Once order is placed update stock
                String sqlUpdateStock = "UPDATE products SET stock  = stock - " + productsBought.get(product1.getProductName()) + " WHERE name = '" + product1.getProductName() + "';";
                jdbc.runSQLUpdate(sqlUpdateStock);


                //remove product from hashmap to stop duplicated
                productsBought.remove(product1.getProductName());

            }




    }


}

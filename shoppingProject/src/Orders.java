import java.util.*;

public class Orders
{

    private static int NUMBER_OF_ORDERS;
    private int orderNumber = 0;
    Customers customer;
    HashMap<String, Integer> productsBought = new HashMap<>();




    public Orders(Customers customer, Products... products)
    {
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

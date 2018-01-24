import java.sql.*;

public class Main
{

    public static void main(String args[])
    {

        UI userInterface = new UI();
        userInterface.setUpUserInterface();

        //create customers to make orders
        Customers customersJake = new Customers("Jake Goodwin", "TK19 QMN", 13);
        Customers customersMichael = new Customers("Michael Smith", "MNS5 2TP", 9);

        //Add products (if not already in database - two products of same name cannot be added)
        Products samsungTv = new Products("Samsung TV", 200, 799.99);
        Products iphoneX = new Products("Iphone X", 11, 999.99);
        Products left4Dead = new Products("Left 4 Dead", 55, 39.99);
        Products dellLaptop = new Products("DELL Laptop", 11, 285.99);
        Products thCoat = new Products("TH Coat", 3, 69.99);

        //the order the required parameters are (customer, product1...)
        Orders orders = new Orders(customersJake, samsungTv, samsungTv, left4Dead, thCoat);
        Orders orders1 = new Orders(customersMichael, dellLaptop, samsungTv, dellLaptop);

    }


}

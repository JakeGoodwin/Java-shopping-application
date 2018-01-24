import java.sql.*;

public class Main
{

    public static void main(String args[])
    {
        Customers customers = new Customers("Jake Goodwin", "TK19 QMN", 13);
        Customers customers1 = new Customers("Michael Smith", "MNS5 2TP", 9);
        Products products = new Products("Samsung TV", 200, 799.99);
        Products products1 = new Products("Iphone X", 11, 999.99);
        Products products2 = new Products("Left 4 Dead", 55, 39.99);
        Products products3 = new Products("DELL Laptop", 11, 285.99);
        Products products4 = new Products("TH Coat", 3, 69.99);
        Orders orders = new Orders(customers, products, products, products, products1);
        Orders orders1 = new Orders(customers1, products3, products4, products, products2);

    }



}

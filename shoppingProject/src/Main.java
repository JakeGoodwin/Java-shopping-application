import java.sql.*;

public class Main
{

    public static void main(String args[])
    {
        Customers customers = new Customers("Jake Goodwin", "TK19 QMN", 13);
        Products products = new Products("4k tv", 200, 799.99);
        Products products1 = new Products("Toaster", 11, 32.95);
        Orders orders = new Orders(customers, products, products, products, products1);

    }



}

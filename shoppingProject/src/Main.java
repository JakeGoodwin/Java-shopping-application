import java.sql.*;
import java.util.ArrayList;

public class Main
{

    public static void main(String args[])
    {

        ArrayList<Customers> customersArrayList = new ArrayList<>();
        UI userInterface = new UI();


        customersArrayList.add(new Customers("Jake Goodwin", "TK19 QMN", 13));
        customersArrayList.add(new Customers("Michael Smith", "MN22 LNS", 66));
        customersArrayList.add(new Customers("David Schmok", "XM4 LS4", 123));


        userInterface.customerInterface(customersArrayList);
        //userInterface.setUpUserInterface();

        //create customers to make orders
        //Customers customersMichael = new Customers("Michael Smith", "MNS5 2TP", 9);

    }


}

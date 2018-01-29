import java.sql.ResultSet;
import java.sql.SQLException;

public class Products
{

    private String productName;
    private int numberInStock;
    private double price;



    //Constructor does all the work at the moment
    public Products(String productName, int numberInStock, double price)
    {
        this.productName = productName;
        this.numberInStock = numberInStock;
        this.price = price;
        String existingProduct = "";
        JDBC jdbc = new JDBC();


            //retreives data of any product we are trying to add that may already be within databse
            String sqlTestExistance = "SELECT name FROM products WHERE name = '" + this.getProductName() + "';";
            ResultSet resultSet = jdbc.runSQLQuery(sqlTestExistance);


            try
            {

                while (resultSet.next())
                {
                    existingProduct = resultSet.getString("name");
                }

            }catch (SQLException e)
            {
                e.printStackTrace();
            }


            //Check if product already exists
            if (!existingProduct.equals(this.getProductName()))
            {
                //Insert product to DB if they do not already exist
                String sqlInsertProducts = String.format("INSERT INTO products (name, stock, price) VALUES (\'%s\', \'%s\', \'%s\');",
                        this.getProductName(), this.getNumberInStock(), String.format("%.2f",this.getPrice()));

                //Execute a query
                jdbc.runSQLUpdate(sqlInsertProducts);
            }


    }


    public void addStock(int n)
    {
        this.numberInStock += n;
    }

    public void removeStock(int quantity)
    {
        this.numberInStock -= quantity;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public int getNumberInStock()
    {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock)
    {
        this.numberInStock = numberInStock;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}

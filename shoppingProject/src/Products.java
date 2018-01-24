import java.sql.*;

public class Products
{

    private String productName;
    private int numberInStock;
    private double price;

    //Set up JDBC
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/shop";

    static final String USER = "root";
    static final String PASS = "password1";

    //Constructor does all the work at the moment
    public Products(String productName, int numberInStock, double price)
    {
        this.productName = productName;
        this.numberInStock = numberInStock;
        this.price = price;


        Connection connection = null;
        Statement statement = null;

        try
        {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            statement = connection.createStatement();

            String sqlInsertProducts;
            String sqlTestExistance;

            //retreives data of any product we are trying to add that may already be within databse
            sqlTestExistance = "SELECT name FROM products WHERE name = '" + this.getProductName() + "';";

            ResultSet resultSet = statement.executeQuery(sqlTestExistance);

            String existingProduct = "";

            while (resultSet.next())
            {
                existingProduct = resultSet.getString("name");
            }


            //Check if product already exists
            if (!existingProduct.equals(this.getProductName()))
            {
                //Insert product to DB if they do not already exist
                sqlInsertProducts = String.format("INSERT INTO products (name, stock) VALUES (\'%s\', \'%s\');",
                        this.getProductName(), this.getNumberInStock());

                //Execute a query
                statement.executeUpdate(sqlInsertProducts);
            }

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

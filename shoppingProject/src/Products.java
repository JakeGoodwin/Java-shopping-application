import java.sql.*;

public class Products
{

    private String productName;
    private int numberInStock;
    private double price;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/shop";

    static final String USER = "root";
    static final String PASS = "password1";

    public Products(String productName, int numberInStock, double price) {
        this.productName = productName;
        this.numberInStock = numberInStock;
        this.price = price;


        Connection connection = null;
        Statement statement = null;

        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            statement = connection.createStatement();
            String sql;
            String sqlTest;

            sqlTest = "SELECT name FROM products WHERE name = '" + this.getProductName() + "';";

            ResultSet resultSet = statement.executeQuery(sqlTest);

            String pn = "";

            while(resultSet.next()) {
                 pn = resultSet.getString("name");

            }


            //Extract data
            if (!pn.equals(this.getProductName())) {

                sql = String.format("INSERT INTO products (name, stock) VALUES (\'%s\', \'%s\');",
                        this.getProductName(), this.getNumberInStock());

                //Execute a query
                statement.executeUpdate(sql);
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

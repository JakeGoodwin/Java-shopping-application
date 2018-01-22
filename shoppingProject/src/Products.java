public class Products
{

    private String productName;
    private int numberInStock;
    private double price;

    public Products(String productName, int numberInStock, double price)
    {
        this.productName = productName;
        this.numberInStock = numberInStock;
        this.price = price;
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

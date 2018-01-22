public class Customers
{
    private String name;
    private String postcode;
    private int houseNumber;

    public Customers(String name, String postcode, int houseNumber)
    {
        this.name = name;
        this.postcode = postcode;
        this.houseNumber = houseNumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
}


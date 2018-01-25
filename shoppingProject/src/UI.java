

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

public class UI
{

    Customers customersJake = new Customers("Jake Goodwin", "TK19 QMN", 13);

    ArrayList<JButton> buttonArrayList = new ArrayList<>();
    ArrayList<Products> selectedProducts = new ArrayList<>();

    //set up JDBC
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/shop";

    //this is something that if made live would need to be removed from the code - possibly a future task
    static final String USER = "root";
    static final String PASS = "password1";

    Connection connection = null;
    Statement statement = null;


    ArrayList<Products> productsArrayList = new ArrayList<>();


    //load up user interface
    public void setUpUserInterface()
    {

        productsArrayList.add(new Products("Samsung TV", 200, 799.99));
        productsArrayList.add(new Products("Iphone X", 11, 999.99));
        productsArrayList.add(new Products("Left 4 Dead", 55, 39.99));
        productsArrayList.add(new Products("DELL Laptop", 11, 285.99));
        productsArrayList.add(new Products("TH Coat", 3, 69.99));


        JFrame frame = new JFrame("Online Shop");
        JPanel buttonPanel = new JPanel();
        JPanel orderPanel = new JPanel();



        buttonPanel.setLayout(new GridLayout(0,2));

        orderPanel.setLayout(new GridLayout(0,3));


        frame.getContentPane().add(BorderLayout.CENTER, buttonPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, orderPanel);

        //frame.setLayout(new GridLayout(0,3));
        frame.setSize(700, 600);
        frame.setLocation(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        final JButton product1 = new JButton("Product 1");
        final JButton product2 = new JButton("Product 2");
        final JButton product3 = new JButton("Product 3");
        final JButton product4 = new JButton("Product 4");
        final JButton product5 = new JButton("Product 5");
        final JButton product6 = new JButton("Product 6");
        final JButton product7 = new JButton("Product 7");
        final JButton product8 = new JButton("Product 8");

        buttonArrayList.add(product1);
        buttonArrayList.add(product2);
        buttonArrayList.add(product3);
        buttonArrayList.add(product4);
        buttonArrayList.add(product5);
        buttonArrayList.add(product6);
        buttonArrayList.add(product7);
        buttonArrayList.add(product8);

        final JLabel price = new JLabel("£");

        final JButton cancel = new JButton("Cancel and clear");
        final JButton order = new JButton("Place order");



        //final JTextArea textArea1 = new JTextArea(10, 20);

        getButtonData();
        buttonPanel.add(BorderLayout.NORTH, product1);
        buttonPanel.add(BorderLayout.NORTH, product2);
        buttonPanel.add(BorderLayout.NORTH, product3);
        buttonPanel.add(BorderLayout.NORTH, product4);
        buttonPanel.add(BorderLayout.NORTH, product5);
        buttonPanel.add(BorderLayout.NORTH, product6);
        buttonPanel.add(BorderLayout.NORTH, product7);
        buttonPanel.add(BorderLayout.NORTH, product8);

        orderPanel.add(BorderLayout.NORTH, price);
        orderPanel.add(BorderLayout.WEST, cancel);
        orderPanel.add(BorderLayout.EAST, order);




        //frame.getContentPane().add(BorderLayout.EAST, textArea1);
        final JButton button = new JButton("Click me");
        //frame.getContentPane().add(BorderLayout.SOUTH, button);

        //Lambda expression - this detects the button was pressed and actions it.
        product1.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product1.getToolTipText()))));

            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product1.getText()))
                {
                    selectedProducts.add(products);
                }
            }

        });

        product2.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product2.getToolTipText()))));
            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product2.getText()))
                {
                    selectedProducts.add(products);
                }
            }

        });

        product3.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product3.getToolTipText()))));

            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product3.getText()))
                {
                    selectedProducts.add(products);
                }
            }

        });

        product4.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product4.getToolTipText()))));

            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product4.getText()))
                {
                    selectedProducts.add(products);
                }
            }

        });

        product5.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product5.getToolTipText()))));

            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product5.getText()))
                {
                    selectedProducts.add(products);
                }
            }
        });

        product6.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product6.getToolTipText()))));

            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product6.getText()))
                {
                    selectedProducts.add(products);
                }
            }

        });

        product7.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product7.getToolTipText()))));

            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product7.getText()))
                {
                    selectedProducts.add(products);
                }
            }

        });

        product8.addActionListener((ActionEvent e) ->
        {
            price.setText("£" + Integer.toString(Integer.parseInt(price.getText().replace('£', '0'))
                    + (Integer.parseInt(product8.getToolTipText()))));

            for (Products products : productsArrayList)
            {
                if(products.getProductName().equals(product8.getText()))
                {
                    selectedProducts.add(products);
                }
            }

        });

        cancel.addActionListener((ActionEvent e) ->
                {
                    price.setText("£");
                    selectedProducts.clear();
                });

        order.addActionListener((ActionEvent e) ->
        {
            //the order the required parameters are (customer, product1...)
            if(!selectedProducts.isEmpty())
            {
                Orders orders = new Orders(customersJake, selectedProducts);
            }
            price.setText("£");
            selectedProducts.clear();

        });







        frame.setVisible(true);

    }

    public String getButtonData()
    {


        try
        {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            statement = connection.createStatement();
            String sqlRetreiveData;

            //Database update for adding order
            sqlRetreiveData = "SELECT * FROM products;";

            ResultSet resultSet = statement.executeQuery(sqlRetreiveData);

            int i = 0;
            while (resultSet.next())
            {
                //We will use the text from the tooltip for the price, this stops us needing to make a second query
                buttonArrayList.get(i).setToolTipText(resultSet.getString("price"));
                buttonArrayList.get(i).setText(resultSet.getString("name"));
                buttonArrayList.get(i).setOpaque(true);
                buttonArrayList.get(i).setBackground(Color.ORANGE);
                i++;
            }
            for(JButton button: buttonArrayList)
            {
                if (button.getText().startsWith("Product"))
                {
                    button.setOpaque(true);
                    button.setBackground(Color.BLACK);
                    button.setEnabled(false);
                }
            }

            //close connections
            statement.close();
            connection.close();

        }catch (Exception e)
        {

        }






        return "";
    }




}

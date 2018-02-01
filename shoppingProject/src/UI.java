

import javafx.scene.control.PasswordField;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class UI
{

    ArrayList<JButton> buttonArrayList = new ArrayList<>();
    ArrayList<Products> selectedProducts = new ArrayList<>();
    ArrayList<Products> productsArrayList = new ArrayList<>();
    ArrayList<Customers> customersArrayList = new ArrayList<>();
    Customers selectedCustomer = null;
    JDBC jdbc = new JDBC();





    public void load()
    {
        JFrame frame = new JFrame("Sign in");

        JPanel middle = new JPanel();

        middle.setLayout(new GridLayout(4, 2));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton signIn = new JButton();
        JLabel usernameLabel = new JLabel();
        JLabel passwordLabel = new JLabel();
        JTextArea usernameField = new JTextArea();
        JPasswordField passwordField = new JPasswordField();


        usernameLabel.setText("Username:");
        passwordLabel.setText("Password:");
        signIn.setText("Sign in");

        middle.add(BorderLayout.NORTH, usernameLabel);
        middle.add(BorderLayout.NORTH, usernameField);
        middle.add(BorderLayout.NORTH, passwordLabel);
        middle.add(BorderLayout.NORTH, passwordField);
        frame.getContentPane().add(BorderLayout.NORTH, middle);
        frame.getContentPane().add(BorderLayout.SOUTH, signIn);

        frame.setSize(200, 200);
        frame.setLocation(400, 300);

        frame.setVisible(true);


        //This method used is not greatly secure but works perfectly for my means
        signIn.addActionListener((ActionEvent e) ->
        {

            if(usernameField.getText().contains("\'") || usernameField.getText().contains("\\") || usernameField.getText().contains(";"))
            {
                usernameField.setText("");
            }

            String passwordToHash = new String(passwordField.getPassword());
            String generatedPassword = null;
            try {
                // Create MessageDigest instance for MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                //Add password bytes to digest
                md.update(passwordToHash.getBytes());
                //Get the hash's bytes
                byte[] bytes = md.digest();
                //This bytes[] has bytes in decimal format;
                //Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                //Get complete hashed password in hex format
                generatedPassword = sb.toString();
            }
            catch (NoSuchAlgorithmException k)
            {
                k.printStackTrace();
            }

            String sqlPword = "SELECT password FROM users WHERE username = '" + usernameField.getText() + "';";
            ResultSet resultSet = jdbc.runSQLQuery(sqlPword);

            try
            {
                while (resultSet.next())
                {
                    if(resultSet.getString("password").equals(generatedPassword))
                    {
                        frame.setVisible(false);
                        customerInterface();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Username or Password is incorrect");
                    }
                }
            }
            catch (SQLException m)
            {
                m.printStackTrace();
            }




        });



    }



    public void customerInterface()
    {

        JFrame frame = new JFrame("Select customer");
        JPanel top = new JPanel();
        JPanel middle = new JPanel();
        JPanel bottom = new JPanel();

        top.setLayout(new GridLayout(0, 1));
        middle.setLayout(new GridLayout(0, 1));
        bottom.setLayout(new GridLayout(0, 3));


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton selectCustomer = new JButton();
        JButton addCustomer = new JButton();
        JButton addProduct = new JButton();

        selectCustomer.setText("Select Customer");
        addCustomer.setText("Add Customer");
        addProduct.setText("Add product");

        JTextArea address = new JTextArea();
        address.setEditable(false);

        selectCustomer.setBackground(Color.darkGray);
        address.setBackground(Color.LIGHT_GRAY);

        JComboBox customerList = new JComboBox();

        ArrayList<Customers> customers = new ArrayList<>();
        selectCustomer.setEnabled(false);

        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                for (Customers selected : customers)
                {
                    if (customerList.getSelectedItem().equals(selected.getName()))
                    {
                        selectedCustomer = selected;
                        address.setFont(new Font("Serif", Font.BOLD, 30));
                        address.setText("    " + selected.getName() + "\n    " + selected.getHouseNumber() + "\n    " + selected.getPostcode());
                        selectCustomer.setEnabled(true);
                    }
                }


            }
        };

        top.add(BorderLayout.CENTER, customerList);
        middle.add(BorderLayout.CENTER, address);
        bottom.add(BorderLayout.CENTER, selectCustomer);
        bottom.add(BorderLayout.CENTER, addCustomer);
        bottom.add(BorderLayout.CENTER, addProduct);


        frame.getContentPane().add(BorderLayout.NORTH, top);
        frame.getContentPane().add(BorderLayout.CENTER, middle);
        frame.getContentPane().add(BorderLayout.SOUTH, bottom);


        customerList.addActionListener(listener);

        String customerDataSQL = "Select * FROM customers;";
        JDBC jdbc = new JDBC();
        ResultSet resultSet = jdbc.runSQLQuery(customerDataSQL);

        String customerToAdd = "";
        try
        {
            while (resultSet.next())
            {
                customerToAdd = resultSet.getString("name");
                customerList.addItem(customerToAdd);
                customers.add(new Customers(resultSet.getString("name"), resultSet.getString("postCode"), Integer.parseInt(resultSet.getString("houseNumber"))));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


        selectCustomer.addActionListener((ActionEvent e) ->
        {
            frame.setVisible(false);
            setUpUserInterface();

        });

        addCustomer.addActionListener((ActionEvent e) ->
        {
            frame.setVisible(false);
            addCustomerInterface();

        });

        addProduct.addActionListener((ActionEvent e) ->
        {
            frame.setVisible(false);
            addProductInterface();

        });


        frame.setSize(300, 300);
        frame.setLocation(400, 300);

        frame.setVisible(true);

    }


    //load up user interface
    public void setUpUserInterface()
    {

        JFrame frame = new JFrame("Online Shop");
        JPanel buttonPanel = new JPanel();
        JPanel orderPanel = new JPanel();


        buttonPanel.setLayout(new GridLayout(0, 2));

        orderPanel.setLayout(new GridLayout(0, 3));


        frame.getContentPane().add(BorderLayout.CENTER, buttonPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, orderPanel);

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


        final JLabel price = new JLabel("£");


        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {


                int position = e.getActionCommand().lastIndexOf("£");


                price.setText("£" + String.format("%.2f", Double.parseDouble(price.getText().replace('£', '0'))
                        + (Double.parseDouble(e.getActionCommand().substring(position + 1)))));

                for (Products products : productsArrayList)
                {
                    if (products.getProductName().equals(e.getActionCommand().substring(0, position - 1)))
                    {
                        selectedProducts.add(products);
                    }
                }

            }
        };


        //Stops us needing to have multiple listeners for a very similar action
        product1.addActionListener(listener);
        product2.addActionListener(listener);
        product3.addActionListener(listener);
        product4.addActionListener(listener);
        product5.addActionListener(listener);
        product6.addActionListener(listener);
        product7.addActionListener(listener);
        product8.addActionListener(listener);

        buttonArrayList.add(product1);
        buttonArrayList.add(product2);
        buttonArrayList.add(product3);
        buttonArrayList.add(product4);
        buttonArrayList.add(product5);
        buttonArrayList.add(product6);
        buttonArrayList.add(product7);
        buttonArrayList.add(product8);


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


        //Cancel button
        cancel.addActionListener((ActionEvent e) ->
        {
            price.setText("£");
            selectedProducts.clear();
        });


        //Order button
        order.addActionListener((ActionEvent e) ->
        {
            //the order the required parameters are (customer, product1...)
            if (!selectedProducts.isEmpty())
            {
                Orders orders = new Orders(selectedCustomer, selectedProducts);
                String listedOrders = "";

                for (Products products : selectedProducts)
                {
                    listedOrders += products.getProductName() + " £" + products.getPrice() + "\n";
                }


                JOptionPane.showMessageDialog(null, "Order Successful: " + selectedCustomer.getName() + "\n"
                        + listedOrders + "\n" + "Grand total: " + price.getText());
            }
            price.setText("£");
            selectedProducts.clear();

        });


        frame.setVisible(true);

    }

    public String getButtonData()
    {


        String sqlRetreiveData = "SELECT * FROM products;";

        ResultSet resultSet = jdbc.runSQLQuery(sqlRetreiveData);

        int i = 0;

        try
        {
            while (resultSet.next())
            {

                //Allows products to be added dynamically
                productsArrayList.add(new Products(resultSet.getString("name"),
                        Integer.parseInt(resultSet.getString("stock")),
                        Double.parseDouble(resultSet.getString("price"))));

                buttonArrayList.get(i).setText(resultSet.getString("name"));
                buttonArrayList.get(i).setText(buttonArrayList.get(i).getText() + " £" + resultSet.getString("price"));
                buttonArrayList.get(i).setOpaque(true);
                buttonArrayList.get(i).setBackground(Color.ORANGE);
                String stock = resultSet.getString("stock");
                buttonArrayList.get(i).setToolTipText(stock);
                if (Integer.parseInt(stock) < 1)
                {
                    buttonArrayList.get(i).setBackground(Color.RED);
                    buttonArrayList.get(i).setEnabled(false);
                }


                i++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        for (JButton button : buttonArrayList)
        {
            if (button.getText().startsWith("Product"))
            {
                button.setOpaque(true);
                button.setBackground(Color.BLACK);
                button.setEnabled(false);
            }
        }

        return "";
    }


    public void addCustomerInterface()
    {
        JFrame frame = new JFrame("Select customer");
        JPanel top = new JPanel();
        JPanel buttons = new JPanel();

        top.setLayout(new GridLayout(6, 0));
        buttons.setLayout(new GridLayout(0, 1));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton addCustomer = new JButton();
        addCustomer.setText("Add customer");

        JButton returnPrevious = new JButton();
        returnPrevious.setText("Go back");

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Customer Name:");
        JTextArea nameText = new JTextArea();
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 20));


        JLabel postCodeLabel = new JLabel();
        postCodeLabel.setText("Customer Postcode:");
        JTextArea postCodeText = new JTextArea();
        postCodeLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel numberLabel = new JLabel();
        numberLabel.setText("Customer house number:");
        JTextArea numberText = new JTextArea();
        numberLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        top.add(BorderLayout.NORTH, nameLabel);
        top.add(BorderLayout.NORTH, nameText);

        top.add(BorderLayout.CENTER, postCodeLabel);
        top.add(BorderLayout.CENTER, postCodeText);

        top.add(BorderLayout.SOUTH, numberLabel);
        top.add(BorderLayout.SOUTH, numberText);

        buttons.add(BorderLayout.CENTER, addCustomer);
        buttons.add(BorderLayout.CENTER, returnPrevious);

        frame.getContentPane().add(BorderLayout.NORTH, top);
        frame.getContentPane().add(BorderLayout.SOUTH, buttons);


        frame.setSize(350, 250);
        frame.setLocation(400, 300);
        frame.setVisible(true);


        //Cancel button
        addCustomer.addActionListener((ActionEvent e) ->
        {
            //Very crude way to sanitise text boxes to prevent sql injection
            if(nameText.getText().contains("\'") || nameText.getText().contains("\\") || nameText.getText().contains(";"))
            {
                nameText.setText("");
            }
            if(numberText.getText().contains("\"")  || numberText.getText().contains("\\") || numberText.getText().contains(";"))
            {
                numberText.setText("");
            }
            if(postCodeText.getText().contains("\"")  || postCodeText.getText().contains("\\") || postCodeText.getText().contains(";"))
            {
                postCodeText.setText("");

            }
            else if(!nameText.getText().isEmpty() && !numberText.getText().isEmpty() && !postCodeText.getText().isEmpty())
            {
                String sql = String.format("INSERT INTO customers (name, postCode, houseNumber) VALUES (\'%s\', \'%s\', \'%s\');",
                        nameText.getText(), postCodeText.getText(), numberText.getText());
                jdbc.runSQLUpdate(sql);
                frame.setVisible(false);
                customerInterface();
            }

        });

        returnPrevious.addActionListener((ActionEvent e) ->
        {
            frame.setVisible(false);
            customerInterface();
        });


    }

    public void addProductInterface()
    {
        JFrame frame = new JFrame("Add product");
        JPanel top = new JPanel();
        JPanel buttons = new JPanel();

        top.setLayout(new GridLayout(6, 0));
        buttons.setLayout(new GridLayout(0, 1));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton addProduct = new JButton();
        addProduct.setText("Add product");

        JButton returnPrevious = new JButton();
        returnPrevious.setText("Go back");

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Product Name:");
        JTextArea nameText = new JTextArea();
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 20));


        JLabel stockLabel = new JLabel();
        stockLabel.setText("Stock to add:");
        JTextArea stockText = new JTextArea();
        stockText.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price:");
        JTextArea priceText = new JTextArea();
        priceText.setFont(new Font("Serif", Font.PLAIN, 20));

        top.add(BorderLayout.NORTH, nameLabel);
        top.add(BorderLayout.NORTH, nameText);

        top.add(BorderLayout.CENTER, stockLabel);
        top.add(BorderLayout.CENTER, stockText);

        top.add(BorderLayout.SOUTH, priceLabel);
        top.add(BorderLayout.SOUTH, priceText);

        buttons.add(BorderLayout.CENTER, addProduct);
        buttons.add(BorderLayout.CENTER, returnPrevious);

        frame.getContentPane().add(BorderLayout.NORTH, top);
        frame.getContentPane().add(BorderLayout.SOUTH, buttons);


        frame.setSize(350, 250);
        frame.setLocation(400, 300);
        frame.setVisible(true);


        //Cancel button
        addProduct.addActionListener((ActionEvent e) ->
        {
            //Very crude way to sanitise text boxes to prevent sql injection
            if(nameText.getText().contains("\'") || nameText.getText().contains("\\") || nameText.getText().contains(";"))
            {
                nameText.setText("");
            }
            if(priceText.getText().contains("\"")  || priceText.getText().contains("\\") || priceText.getText().contains(";"))
            {
                priceText.setText("");
            }
            if(priceText.getText().contains("£"))
            {
                //does this actually work? it could cause problems getting the value out in the future
                priceText.setText(priceText.getText().replace('£', ' '));
            }
            if(stockText.getText().contains("\"")  || stockText.getText().contains("\\") || stockText.getText().contains(";"))
            {
                stockText.setText("");

            }
            else if(!nameText.getText().isEmpty() && !priceText.getText().isEmpty() && !stockText.getText().isEmpty())
            {
                String sql = String.format("INSERT INTO products (name, stock, price) VALUES (\'%s\', \'%s\', \'%s\');",
                        nameText.getText(), stockText.getText(), priceText.getText());
                jdbc.runSQLUpdate(sql);
                frame.setVisible(false);
                customerInterface();
            }

        });

        returnPrevious.addActionListener((ActionEvent e) ->
        {
            frame.setVisible(false);
            customerInterface();
        });


    }


}

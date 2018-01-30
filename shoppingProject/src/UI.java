

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

public class UI
{



    ArrayList<JButton> buttonArrayList = new ArrayList<>();
    ArrayList<Products> selectedProducts = new ArrayList<>();
    ArrayList<Products> productsArrayList = new ArrayList<>();
    Customers selectedCustomer = null;


    public void customerInterface(ArrayList<Customers> customers)
    {

        JFrame frame= new JFrame("Select customer");
        JPanel top = new JPanel();
        JPanel middle = new JPanel();
        JPanel bottom = new JPanel();

        top.setLayout(new GridLayout(0, 1));
        middle.setLayout(new GridLayout(0, 1));
        bottom.setLayout(new GridLayout(0, 1));


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton selectCustomer = new JButton();
        JLabel address = new JLabel();
        JComboBox customerList = new JComboBox();


        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                for( Customers selected : customers)
                {
                    if(customerList.getSelectedItem().equals(selected.getName()))
                    {
                        selectedCustomer = selected;
                        address.setFont(new Font("Serif", Font.BOLD, 16));
                        address.setText(selected.getName() + "\n " + selected.getHouseNumber() + "\n "  + selected.getPostcode());
                    }
                }


            }
        };

        top.add(BorderLayout.CENTER, customerList);
        middle.add(BorderLayout.CENTER, address );
        bottom.add(BorderLayout.CENTER, selectCustomer);



        frame.getContentPane().add(BorderLayout.NORTH, top);
        frame.getContentPane().add(BorderLayout.CENTER, middle);
        frame.getContentPane().add(BorderLayout.SOUTH, bottom);


        customerList.addActionListener(listener);



        for (Customers customer : customers)
        {
            customerList.addItem(customer.getName());

        }

        selectCustomer.addActionListener((ActionEvent e) ->
        {
            frame.setVisible(false);
            setUpUserInterface();

        });








        frame.setSize(300, 300);
        frame.setLocation(400, 300);

        frame.setVisible(true);

    }


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


                price.setText("£" + String.format("%.2f",Double.parseDouble(price.getText().replace('£', '0'))
                        + (Double.parseDouble(e.getActionCommand().substring(position+1)))));

                for (Products products : productsArrayList)
                {
                    if(products.getProductName().equals(e.getActionCommand().substring(0, position-1)))
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
            if(!selectedProducts.isEmpty())
            {
                Orders orders = new Orders(selectedCustomer, selectedProducts);
                String listedOrders = "";

                for(Products products: selectedProducts)
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
        JDBC jdbc = new JDBC();


            String sqlRetreiveData = "SELECT * FROM products;";

            ResultSet resultSet = jdbc.runSQLQuery(sqlRetreiveData);

            int i = 0;

            try
            {
                while (resultSet.next())
                {
                    //We will use the text from the tooltip for the price, this stops us needing to make a second query

                    buttonArrayList.get(i).setText(resultSet.getString("name"));
                    buttonArrayList.get(i).setText(buttonArrayList.get(i).getText() + " £" + resultSet.getString("price"));
                    buttonArrayList.get(i).setOpaque(true);
                    buttonArrayList.get(i).setBackground(Color.ORANGE);
                    String stock = resultSet.getString("stock");
                    buttonArrayList.get(i).setToolTipText(stock);
                    if(Integer.parseInt(stock) < 1)
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
            for(JButton button: buttonArrayList)
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




}

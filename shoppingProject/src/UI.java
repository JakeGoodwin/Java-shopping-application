import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class UI
{

    //load up user interface
    public void setUpUserInterface()
    {
        JFrame frame = new JFrame("Online Shop");

        frame.setSize(250, 250);
        frame.setLocation(300, 200);

        final JTextArea textArea = new JTextArea(10, 40);
        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        final JButton button = new JButton("Click me");
        frame.getContentPane().add(BorderLayout.SOUTH, button);

        //Lambda expression - this detects the button was pressed and actions it.
        button.addActionListener((ActionEvent e) ->
        {
            textArea.append("Button was clicked");
        });

        frame.setVisible(true);

    }


}

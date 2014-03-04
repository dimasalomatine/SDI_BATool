//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;

//~--- classes ----------------------------------------------------------------

public class AdvancedOptions extends JPanel {
    JTextField comments;
    JTextField name;
    JTextArea  ta;
    JTextField user;

    //~--- constructors -------------------------------------------------------

    public AdvancedOptions() {
        JPanel  p1 = new JPanel(new BorderLayout(10, 10));
        JButton b1 = new JButton("1");

        p1.add(b1, BorderLayout.SOUTH);
        ta = new JTextArea("               ");
        ta.setEditable(false);
        p1.add(ta, BorderLayout.CENTER);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();

//              CommentsClient cc = new CommentsClient (p.myHostName, 0, bao);
                ta.setText(bao.toString());
            }
        });
        add(p1);

        JPanel  p2 = new JPanel(new BorderLayout(10, 10));
        JButton b2 = new JButton("Run Cmd");

        p2.add(b2, BorderLayout.SOUTH);

        JPanel p3 = new JPanel();

        name     = new JTextField("", 10);
        user     = new JTextField("", 10);
        comments = new JTextField("", 50);
        p3.add(name);
        p3.add(user);
        p3.add(comments);
        p2.add(p3, BorderLayout.CENTER);
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();

//              CommentsClient cc = new CommentsClient (p.myHostName, 0, bao, 
                // name.getText(), user.getText(), comments.getText());
                ta.setText(bao.toString());
            }
        });
        add(p2);

        JPanel  p4 = new JPanel(new BorderLayout(10, 10));
        JButton b3 = new JButton("SyS cmd");

        p4.add(b3, BorderLayout.NORTH);
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();

//              CommentsClient cc = new CommentsClient (p.myHostName, 0, bao,
//      "s      ys",name.getText(), user.getText(), comments.getText(),"");
                ta.setText(bao.toString());
            }
        });
        add(p4);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com


package formexample;

import java.awt.*;
import java.awt.event.*;

public class FormExample {

    Frame frame;
    Label Lbl_fname, Lbl_lname, Lbl_email, Lbl_pwd, Lbl_cpwd, Lbl_title;
    TextField TF_fname, TF_lname, TF_email, TF_pwd, TF_cpwd;
    Checkbox Cb_agreement;
    Button Btn_register;
    Panel pnl_center, pnl_lbl_fname, pnl_lbl_lname, pnl_tf_fname, pnl_tf_lname;
    
    public static final Color earth_1 = new Color(5, 25, 250);
    public static final Color earth_2 = new Color(5, 250, 25);
    Font sansserif_bold_24 = new Font("SansSerif", Font.BOLD, 24);
    Font serif_bold_16 = new Font("serif", Font.BOLD, 16);
    
    FormExample(){
        frame = new Frame("Registration Form");
        //frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());
        
        Lbl_fname = new Label("First Name");
        Lbl_lname = new Label("Last Name");
        Lbl_email = new Label("Email");
        Lbl_pwd = new Label("Password");
        Lbl_cpwd = new Label("Confirm Password");
        Lbl_title = new Label("Registration Form");
        
        TF_fname = new TextField(25);
        TF_lname = new TextField(25);
        TF_email = new TextField(25);
        TF_pwd = new TextField(25);
        TF_cpwd = new TextField(25);
        
        Cb_agreement = new Checkbox("I agree to the terms & conditions");
        Btn_register = new Button("Register");
        
        pnl_center = new Panel();
        pnl_center.setLayout(new GridLayout(2,2));
        
        pnl_lbl_fname = new Panel();
        pnl_lbl_lname = new Panel();
        pnl_tf_fname = new Panel();
        pnl_tf_lname = new Panel();
        
        pnl_lbl_fname.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnl_lbl_lname.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnl_tf_fname.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnl_tf_lname.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Lbl_title.setFont(sansserif_bold_24);
        Lbl_title.setForeground(earth_1);
        //Lbl_title.setAlignment(Label.CENTER);
        
        Lbl_fname.setFont(serif_bold_16);
        Lbl_lname.setFont(serif_bold_16);
        Lbl_email.setFont(serif_bold_16);
        Lbl_pwd.setFont(serif_bold_16);
        Lbl_cpwd.setFont(serif_bold_16);
        
        Lbl_fname.setForeground(earth_1);
        Lbl_lname.setForeground(earth_1);
        Lbl_email.setForeground(earth_1);
        Lbl_pwd.setForeground(earth_1);
        Lbl_cpwd.setForeground(earth_1);
        
        Btn_register.setBackground(earth_2);
        Btn_register.setFont(serif_bold_16);
        
        //Positioning
        /*
        Lbl_title.setBounds(20, 60, 400, 30);
        Lbl_fname.setBounds(20, 100, 100, 20);
        Lbl_lname.setBounds(240, 100, 100, 20);
        TF_fname.setBounds(20, 120, 180, 25);
        TF_lname.setBounds(240, 120, 180, 25);
        Lbl_email.setBounds(20, 160, 100, 20);
        TF_email.setBounds(20, 180, 400, 25);
        Lbl_pwd.setBounds(20, 220, 100, 20);
        Lbl_cpwd.setBounds(240, 220, 150, 20);
        TF_pwd.setBounds(20, 240, 180, 25);
        TF_cpwd.setBounds(240, 240, 180, 25);
        Cb_agreement.setBounds(20, 280, 250, 20);
        Btn_register.setBounds(20, 310, 100, 30); 
        
        frame.add(Lbl_title);
        frame.add(Lbl_fname);
        frame.add(Lbl_lname);
        frame.add(TF_fname);
        frame.add(TF_lname);
        frame.add(Lbl_email);
        frame.add(TF_email);
        frame.add(Lbl_pwd);
        frame.add(Lbl_cpwd);
        frame.add(TF_pwd);
        frame.add(TF_cpwd);
        frame.add(Cb_agreement);   
        frame.add(Btn_register); */
        
        pnl_lbl_fname.add(Lbl_fname);
        pnl_lbl_lname.add(Lbl_lname);
        pnl_tf_fname.add(TF_fname);
        pnl_tf_lname.add(TF_lname);
        
        pnl_center.add(pnl_lbl_fname);
        pnl_center.add(pnl_lbl_lname);
        pnl_center.add(pnl_tf_fname);
        pnl_center.add(pnl_tf_lname);
        
        frame.add(Lbl_title, BorderLayout.NORTH);
        frame.add(pnl_center, BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowClose());
    }
    
    public static void main(String[] args) {
        FormExample apps = new FormExample();
    }
    
}

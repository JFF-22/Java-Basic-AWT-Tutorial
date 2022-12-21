
package databasemanipulation;

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Dashboard implements ActionListener{
    String db_username = "root";
    String db_pwd = "";
    String db_url = "jdbc:mysql://localhost:3306/dummy_db";
    Connection conn;
    ResultSet rs = null;
    
    JFrame frame;
    ///////////////////////////////PANEL NORTH ///////
    JLabel lbl_name, lbl_ic, lbl_icon, lbl_id,lbl_fetch;
    JTextField txt_name, txt_ic,txt_id, txt_fetch;
    JButton btn_insert, btn_update, btn_delete, btn_fetch, btn_cancel;
    JPanel pnl_north,pnl_west, pnl_eist;
    JPanel pnl_in_box1,pnl_in_box2,pnl_in_box3, pnl_in_box_btn;
    JPanel pnl_fetch;
    JSeparator sep_1;
    JScrollPane sp;
    JTable table;
    
    //Border
    TitledBorder border_1, border_2;
    ImageIcon logo_1;

    Dashboard(){
        
          //database part
    
        frame = new JFrame();
        
        border_1 =  BorderFactory.createTitledBorder("Data List");
        border_2 =  BorderFactory.createTitledBorder("Fetch Data");
        
        
        /////////////panel north ///////
        pnl_north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // kiv relative path
        logo_1 = new ImageIcon(new File("src/Devices-computer-laptop-icon.png").getAbsolutePath());
        lbl_icon = new JLabel(logo_1);
        pnl_west = new JPanel();
        pnl_west.setLayout(new BoxLayout(pnl_west,BoxLayout.Y_AXIS));
        
        
        pnl_in_box1 =new JPanel(new FlowLayout(FlowLayout.CENTER));
        lbl_id = new JLabel("UID");
        lbl_id.setPreferredSize(new Dimension(50,26));
        txt_id = new JTextField(20);
        txt_id.setEditable(false);
        pnl_in_box1.add(lbl_id);
        pnl_in_box1.add(txt_id);
        
        pnl_in_box2 =new JPanel(new FlowLayout(FlowLayout.CENTER));
        lbl_ic = new JLabel("NRIC");
        lbl_ic.setPreferredSize(new Dimension(50,20));
        txt_ic = new JTextField(20);
        pnl_in_box2.add(lbl_ic);
        pnl_in_box2.add(txt_ic);
        
        
        pnl_in_box3 =new JPanel(new FlowLayout(FlowLayout.CENTER));
        lbl_name = new JLabel("Name");
        lbl_name.setPreferredSize(new Dimension(50,20));
        txt_name = new JTextField(20);
        pnl_in_box3.add(lbl_name);
        pnl_in_box3.add(txt_name);
        
        pnl_in_box_btn =new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn_insert =new JButton("Insert");
        btn_update =new JButton("Update");
        btn_update.addActionListener(this);
        btn_delete =new JButton("Delete");
        btn_delete.addActionListener(this);
        btn_cancel =new JButton("Cancel");
        btn_cancel.addActionListener(this);
        pnl_in_box_btn.add(btn_insert);
        pnl_in_box_btn.add(btn_update);
        pnl_in_box_btn.add(btn_delete);
        pnl_in_box_btn.add(btn_cancel);
        btn_insert.addActionListener(this);
        
        pnl_fetch =new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lbl_fetch = new JLabel("UID");
        txt_fetch = new JTextField(5);
        btn_fetch = new JButton("Fetch"); 
        btn_fetch.addActionListener(this);
        
        pnl_fetch.add(lbl_fetch);
        pnl_fetch.add(txt_fetch);
        pnl_fetch.add(btn_fetch);
        pnl_fetch.setBorder(border_2);
     
        sep_1 = new JSeparator();
        pnl_west.add(pnl_fetch);


        pnl_west.add(pnl_in_box1);
        pnl_west.add(pnl_in_box2);
        pnl_west.add(pnl_in_box3);
        pnl_west.add(pnl_in_box_btn);
        
        /////////////////////END OF WEST //////
        
        
        
        ////PANEL EAST////
        pnl_eist = new JPanel();
        try{
            table= new JTable();
            draw_table();
            sp = new JScrollPane(table);
            sp.setPreferredSize(new Dimension(600,200));
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            // TO BE DONE
            
        }catch(Exception ex){
            
        }
        pnl_eist.add(sp);
        pnl_eist.setBorder(border_1);
        
        
        pnl_north.add(lbl_icon);
        frame.add(pnl_north,BorderLayout.NORTH);
        frame.add(pnl_west, BorderLayout.WEST);
        frame.add(pnl_eist, BorderLayout.EAST);
        
        hide_delete_update_button();
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
      public  DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        String[] column_title = {"No.", "NRIC No.", "Student Name", "Mazda"};
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("uid");
        columnNames.add("Student Name");
        columnNames.add("NRIC");
        

        int columnCount = metaData.getColumnCount();

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
      
      public void fetch_all(){
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(db_url,db_username,db_pwd);
            String sql = "Select * from student";
            PreparedStatement statement = conn.prepareStatement(sql);
            
            rs = statement.executeQuery();
            
       
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error"+ex);
        }
          
      }
      
      public void draw_table(){
        try {
            fetch_all();
            table.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
      }

  
    public static void main(String[] args) {
        Dashboard apps = new Dashboard();
    }


@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btn_insert){
               insert_data();            
        }
        else if(e.getSource()==btn_fetch){
               set_data();           
        }
        else if(e.getSource()==btn_update){
            update();
                     
        }
        else if(e.getSource()==btn_delete){
            delete();
                     
        }     
        else if(e.getSource()==btn_cancel){
            clear_form();
            hide_delete_update_button();
                     
        } 
        
    }
    
    public void update(){
        try{
            //loadkan variable
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);
              
            //database operation
                    String sql = "UPDATE student Set ic=?, name=? WHERE id =?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, txt_ic.getText());
                    stmt.setString(2, txt_name.getText());
                    stmt.setInt(3, Integer.parseInt(txt_id.getText()));
                    
                    
                    int update;
                    update = stmt.executeUpdate();
                    
                    if(update > 0){
                        //success
                          JOptionPane.showMessageDialog(frame, "Update Success");
                          clear_form();
                          show_delete_update_button();
                        
                    }else{
                        //fail
                          JOptionPane.showMessageDialog(frame, "Update fail");
                        
                    }
                    
                    draw_table();
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error"+ex);
        }
    }
    
    public void set_data(){
        if (!validation_fetch()){
           //fetch single data
           int uid;
           //set uid var
           uid = Integer.parseInt(txt_fetch.getText());

           try{
               Class.forName("com.mysql.cj.jdbc.Driver");
               conn = DriverManager.getConnection(db_url,db_username,db_pwd);
               String sql = "Select * from student WHERE id = ?";
               PreparedStatement statement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
               statement.setInt(1, uid);
               //letakkan data dari database ke resultset
               rs = statement.executeQuery();

               //baca data dari result set dan set ke form
               if(rs.next()){
                   rs.beforeFirst();
                   while(rs.next()){
                       txt_id.setText(rs.getString("id"));
                       txt_ic.setText(rs.getString("ic"));
                       txt_name.setText(rs.getString("name"));

               }
                   JOptionPane.showMessageDialog(frame, "Data Fetched!");
                   show_delete_update_button();
               }else{
                   JOptionPane.showMessageDialog(frame, "There is no data associated with the ID!");
               }

           }catch(Exception ex){
               JOptionPane.showMessageDialog(frame, "Error"+ex);
           }
        }
        else{
            JOptionPane.showMessageDialog(frame, "Please Enter An ID");
        }
    }
            
    public void insert_data(){
        if (!validation_form()){
            try{
            //loadkan variable
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);
              
            //database operation
                    String sql_insert = "INSERT INTO student(name, ic) VALUES(?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql_insert);
                    stmt.setString(1, txt_name.getText());
                    stmt.setString(2, txt_ic.getText());
                    
                    
                    int insert;
                    insert = stmt.executeUpdate();
                    
                    if(insert > 0){
                        //success
                          JOptionPane.showMessageDialog(frame, "Insert Success");
                          clear_form();
                        
                    }else{
                        //fail
                          JOptionPane.showMessageDialog(frame, "Insert fail");
                        
                    }
                    
                    draw_table();
                    
            
        }catch(Exception ex){
                  JOptionPane.showMessageDialog(frame, "Error: "+ex);
            }
        }
        else{
            JOptionPane.showMessageDialog(frame, "Please complete the form");
        }
        
       
    
}
    
    public void delete(){
        try{
            //loadkan variable
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);
              
            //database operation
                    String sql = "DELETE FROM student WHERE id =?";
                    PreparedStatement stmt = conn.prepareStatement(sql);

                    stmt.setInt(1, Integer.parseInt(txt_id.getText()));
                    
                    
                    int delete;
                    delete = stmt.executeUpdate();
                    
                    if(delete > 0){
                        //success
                          JOptionPane.showMessageDialog(frame, "delete Success");
                          clear_form();
                          show_delete_update_button();
                        
                    }else{
                        //fail
                          JOptionPane.showMessageDialog(frame, "delete fail");
                        
                    }
                    
                    draw_table();
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error"+ex);
        }
    }
    
    public void clear_form(){
        txt_ic.setText("");
        txt_fetch.setText("");
        txt_id.setText("");
        txt_name.setText("");
    }
    
    public void hide_delete_update_button(){
        btn_delete.setVisible(false);
        btn_update.setVisible(false);
        btn_cancel.setVisible(false);
        btn_insert.setVisible(true);
    }
    
    public void show_delete_update_button(){
        btn_delete.setVisible(true);
        btn_update.setVisible(true);
        btn_cancel.setVisible(true);
        btn_insert.setVisible(false);
    }
    
    public boolean validation_fetch(){
        boolean is_empty = true;
        if ((!txt_fetch.getText().toString().isEmpty()) && (txt_fetch.getText().toString() != null)){
            is_empty = false;
        }
        
        return is_empty;
    }
    
    public boolean validation_form(){
        boolean is_empty = true;
        if ((!txt_ic.getText().toString().isEmpty()) && (!txt_name.getText().toString().isEmpty())){
            is_empty = false;
        }
        
        return is_empty;
    }
}
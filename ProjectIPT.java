/*
Mini Project
Yeo Ewe Wen 20DDT20F1016
Ngu Ming Henn 20DDT20F1006
 */
package projectipt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ProjectIPT implements ActionListener{

    JFrame frame;
    // Main Panel
    JPanel pnl_west, pnl_east;
    // Sub Panel
    JPanel pnl_detail_form, pnl_search_form, pnl_specific_option; 
    // panel inside pnl_detail_form
    JPanel pnl_uid, pnl_name, pnl_size, pnl_color, pnl_quantity, pnl_vendor, pnl_button;
    //Titled Border
    TitledBorder tb_detail, tb_search, tb_display;
    //Label and Text Field for pnl_detail_form
    JLabel lbl_uid, lbl_name, lbl_size, lbl_color, lbl_quantity, lbl_vendor;
    JComboBox cbx_name, cbx_size, cbx_color;
    JTextField tf_uid,  tf_quantity, tf_vendor;
    JButton btn_Insert, btn_Delete, btn_Update, btn_Cancel;
    //Label, Button, Text Field for pnl_search_form
    JLabel lbl_search;
    JTextField tf_search;
    JButton btn_search;
    //Label, Combobox, Button for pnl_specific_option
    JLabel lbl_specific;
    JComboBox cbx_choice;
    JButton btn_show;
    //Table for display all
    JTable table;
    
    JScrollPane sp;
    
    int CurrentDisplay = 0;
    
    String db_username = "root";
    String db_pwd = "";
    String db_url = "jdbc:mysql://localhost:3306/project";
    Connection conn;
    ResultSet rs = null;
    
    ProjectIPT(){
        frame = new JFrame("Raw Material Inventory");
        
        //Start WEST
        //Search Form
        pnl_search_form = new JPanel();
        pnl_search_form.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_search = new JLabel("Unique ID: ");
        lbl_search.setPreferredSize(new Dimension(100, 20));
        tf_search = new JTextField(10);
        btn_search = new JButton("Search");
        btn_search.setPreferredSize(new Dimension(100, 20));
        btn_search.addActionListener(this);
        tb_search = BorderFactory.createTitledBorder("Search Material");
        pnl_search_form.add(lbl_search);
        pnl_search_form.add(tf_search);
        pnl_search_form.add(btn_search);
        pnl_search_form.setBorder(tb_search);
        
        //Detail Form
        pnl_detail_form = new JPanel();
        pnl_detail_form.setLayout(new BoxLayout(pnl_detail_form, BoxLayout.Y_AXIS));
        // Element in Detail Form (UID)
        pnl_uid = new JPanel();
        pnl_uid.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_uid = new JLabel("Unique ID: ");
        lbl_uid.setPreferredSize(new Dimension(150, 20));
        tf_uid = new JTextField();
        tf_uid.setPreferredSize(new Dimension(200, 20));
        tf_uid.setEditable(false);
        pnl_uid.add(lbl_uid);
        pnl_uid.add(tf_uid);
        
        // Element in Detail Form (Name)
        pnl_name = new JPanel();
        pnl_name.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_name = new JLabel("Material Name: ");
        lbl_name.setPreferredSize(new Dimension(150, 20));
        String[] materialName = {"None", "Ribbon", "Plain Bag"};
        cbx_name = new JComboBox(materialName);
        cbx_name.setPreferredSize(new Dimension(200, 20));
        pnl_name.add(lbl_name);
        pnl_name.add(cbx_name);
        
        // Element in Detail Form (Size)
        pnl_size = new JPanel();
        pnl_size.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_size = new JLabel("Material Size: ");
        lbl_size.setPreferredSize(new Dimension(150, 20));
        String[] materialSize = {"None", "2ft x 50cm", "50cm x 50cm"};
        cbx_size = new JComboBox(materialSize);
        cbx_size.setPreferredSize(new Dimension(200, 20));
        pnl_size.add(lbl_size);
        pnl_size.add(cbx_size);
        
        // Element in Detail Form (Color)
        pnl_color = new JPanel();
        pnl_color.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_color = new JLabel("Material Color: ");
        lbl_color.setPreferredSize(new Dimension(150, 20));
        String[] materialColor = {"None", "Rainbow", "Navy Blue"};
        cbx_color = new JComboBox(materialColor);
        cbx_color.setPreferredSize(new Dimension(200, 20));
        pnl_color.add(lbl_color);
        pnl_color.add(cbx_color);
        
        // Element in Detail Form (Quantity)
        pnl_quantity = new JPanel();
        pnl_quantity.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_quantity = new JLabel("Material Quantity: ");
        lbl_quantity.setPreferredSize(new Dimension(150, 20));
        tf_quantity = new JTextField();
        tf_quantity.setPreferredSize(new Dimension(200, 20));
        pnl_quantity.add(lbl_quantity);
        pnl_quantity.add(tf_quantity);
        
        // Element in Detail Form (Vendor)
        pnl_vendor = new JPanel();
        pnl_vendor.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_vendor = new JLabel("Material Vendor: ");
        lbl_vendor.setPreferredSize(new Dimension(150, 20));
        tf_vendor = new JTextField();
        tf_vendor.setPreferredSize(new Dimension(200, 20));
        pnl_vendor.add(lbl_vendor);
        pnl_vendor.add(tf_vendor);
        
        // Element in Detail Form (Button)
        pnl_button = new JPanel();
        pnl_button.setLayout(new FlowLayout(FlowLayout.CENTER));
        btn_Insert = new JButton("Insert");
        btn_Insert.addActionListener(this);
        btn_Delete = new JButton("Delete");
        btn_Delete.addActionListener(this);
        btn_Update = new JButton("Update");
        btn_Update.addActionListener(this);
        btn_Cancel = new JButton("Cancel");
        btn_Cancel.addActionListener(this);
        pnl_button.add(btn_Insert);
        pnl_button.add(btn_Delete);
        pnl_button.add(btn_Update);
        pnl_button.add(btn_Cancel);
        
        tb_detail = BorderFactory.createTitledBorder("Material Details");
        
        pnl_detail_form.add(pnl_uid);
        pnl_detail_form.add(pnl_name);
        pnl_detail_form.add(pnl_size);
        pnl_detail_form.add(pnl_color);
        pnl_detail_form.add(pnl_quantity);
        pnl_detail_form.add(pnl_vendor);
        pnl_detail_form.add(pnl_button);
        pnl_detail_form.setBorder(tb_detail);
        
        pnl_west = new JPanel();
        pnl_west.setLayout(new BoxLayout(pnl_west, BoxLayout.Y_AXIS));
        pnl_west.add(pnl_search_form);
        pnl_west.add(pnl_detail_form);
        // End WEST
        
        //Start EAST
        // Specific Option
        pnl_specific_option = new JPanel();
        pnl_specific_option.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbl_specific = new JLabel("Display: ");
        String[] Specific = {"All", "Material Name: Ribbon", "Material Name: Plain Bag", "Material Size: 2ft x 50cm", "Material Size: 50cm x 50cm", "Material Color: Rainbow", "Material Color: Navy Blue"};
        cbx_choice = new JComboBox(Specific);
        cbx_choice.setPreferredSize(new Dimension(200, 20));
        btn_show = new JButton("Show");
        btn_show.addActionListener(this);
        pnl_specific_option.add(lbl_specific);
        pnl_specific_option.add(cbx_choice);
        pnl_specific_option.add(btn_show);
        
        try{
            //Table
            table = new JTable();
            sp = new JScrollPane(table);
            sp.setPreferredSize(new Dimension(800, 200));
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            draw_table();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(frame, "Error: " + ex);
        }
        
        pnl_east = new JPanel();
        pnl_east.setLayout(new BoxLayout(pnl_east, BoxLayout.Y_AXIS));
        pnl_east.add(pnl_specific_option);
        pnl_east.add(sp);
        tb_display = BorderFactory.createTitledBorder("List of Materials");
        pnl_east.setBorder(tb_display);
        //End EAST
        
        frame.add(pnl_west, BorderLayout.WEST);
        frame.add(pnl_east, BorderLayout.EAST);
        
        hide_delete_update();
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        ProjectIPT app = new ProjectIPT();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_Insert){
           
            insert();
            
        }
        else if (e.getSource() == btn_Delete){
            delete();
        }
        else if (e.getSource() == btn_Update){
            update();
        }
        else if (e.getSource() == btn_Cancel){
            display_insert();
            hide_delete_update();
            clear_form();
        }
        else if (e.getSource() == btn_search){
            search();
        }
        else if (e.getSource() == btn_show){
            CurrentDisplay = cbx_choice.getSelectedIndex();
            draw_table();
        }
    }
    
    public DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        String[] column_title = {"No.", "NRIC No.", "Student Name", "Mazda"};
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Name");
        columnNames.add("Size");
        columnNames.add("Color");
        columnNames.add("Quantity");
        columnNames.add("Vendor");
        columnNames.add("UID");
        

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
    
    public void fetch_all() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);
            String sql;
            switch (CurrentDisplay){
                case 1:
                    sql = "SELECT * FROM materials WHERE material_name = 'Ribbon'";
                    break;
                case 2:
                    sql = "SELECT * FROM materials WHERE material_name = 'Plain Bag'";
                    break;
                case 3:
                    sql = "SELECT * FROM materials WHERE material_size = '2ft x 50cm'";
                    break;
                case 4:
                    sql = "SELECT * FROM materials WHERE material_size = '50cm x 50cm'";
                    break;
                case 5:
                    sql = "SELECT * FROM materials WHERE material_color = 'Rainbow'";
                    break;
                case 6:
                    sql = "SELECT * FROM materials WHERE material_color = 'Navy Blue'";
                    break;
                default:
                    sql = "SELECT * FROM materials";
            }
            
            PreparedStatement statement = conn.prepareStatement(sql);

            rs = statement.executeQuery();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex);
        }

    }
    
    public void draw_table() {
        try {
            fetch_all();
            table.setModel(buildTableModel(rs));
        } catch (Exception ex) {
            Logger.getLogger(ProjectIPT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert(){
        
        if (validation_form()){
            try {
            //loadkan variable
            Class.forName("com.mysql.cj.jdbc.Driver");

            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);

            //database operation
            String sql_insert = "INSERT INTO materials(material_name, material_size, material_color, material_quantity, material_vendor) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql_insert);
            stmt.setString(1, cbx_name.getSelectedItem().toString());
            stmt.setString(2, cbx_size.getSelectedItem().toString());
            stmt.setString(3, cbx_color.getSelectedItem().toString());
            stmt.setInt(4, Integer.parseInt(tf_quantity.getText()));
            stmt.setString(5, tf_vendor.getText());
                
            int insert;
            insert = stmt.executeUpdate();
            
            
            if (insert > 0) {
                //success
                JOptionPane.showMessageDialog(frame, "Data Inserted Successfully!");
                display_delete_update();
                clear_form();
                
            } else {
                //fail
                JOptionPane.showMessageDialog(frame, "Data Failed to Insert!");
                

            }
            draw_table();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex);

        }

       }else{
           JOptionPane.showMessageDialog(frame, "Please completed the form!");
       }
    
    }
    
     public void search() {
        //fetch single data
        if(validation_search()){
            int search;
        //set search var
        search = Integer.parseInt(tf_search.getText().toString());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);
            String sql = "Select * from materials WHERE material_uid = ?";
            PreparedStatement statement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, search);
            //letakkan data dari database ke resultset
            rs = statement.executeQuery();

            //baca data dari result set dan set ke form
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    tf_uid.setText(rs.getString("material_uid"));
                    tf_quantity.setText(rs.getString("material_quantity"));
                    tf_vendor.setText(rs.getString("material_vendor"));
                    
                    if (rs.getString("material_name").equals("Ribbon")){
                        cbx_name.setSelectedIndex(1);
                    }
                    else{
                        cbx_name.setSelectedIndex(2);
                    }
                    
                    if (rs.getString("material_size").equals("2ft x 50cm")){
                        cbx_size.setSelectedIndex(1);
                    }
                    else{
                        cbx_size.setSelectedIndex(2);
                    }
                    
                    if (rs.getString("material_color").equals("Rainbow")){
                        cbx_color.setSelectedIndex(1);
                    }
                    else{
                        cbx_color.setSelectedIndex(2);
                    }
                }
                JOptionPane.showMessageDialog(frame, "Data Found!");
                display_delete_update();
                hide_insert();
            } else {
                JOptionPane.showMessageDialog(frame, "There is no data associated with the ID!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex);
        }
        }else{
             JOptionPane.showMessageDialog(frame, "Unique ID is Required!");
        }
        

    }
    
    public void delete() {
       
        try {
            //loadkan variable
            Class.forName("com.mysql.cj.jdbc.Driver");

            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);

            //database operation
            String sql = "DELETE FROM materials WHERE material_uid =?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, Integer.parseInt(tf_search.getText()));

            int delete;
            delete = stmt.executeUpdate();

            if (delete > 0) {
                //success
                draw_table();
                clear_form();
                JOptionPane.showMessageDialog(frame, "Data Deleted Successfully!");

            } else {
                //fail
                JOptionPane.showMessageDialog(frame, "Data Failed to Delete!");
            }

            } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex);
            }
        
        
    }
    
    public void update(){
      if (validation_form()){
         try {
            //loadkan variable
            Class.forName("com.mysql.cj.jdbc.Driver");

            //establish connection
            conn = DriverManager.getConnection(db_url, db_username, db_pwd);

            //database operation
            String sql = "UPDATE materials SET material_name=?,  material_size=?,  material_color=?,  material_quantity=?, material_vendor=? WHERE material_uid =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cbx_name.getSelectedItem().toString());
            stmt.setString(2, cbx_size.getSelectedItem().toString());
            stmt.setString(3, cbx_color.getSelectedItem().toString());
            stmt.setInt(4, Integer.parseInt(tf_quantity.getText().toString()));
            stmt.setString(5, tf_vendor.getText().toString());
            stmt.setInt(6, Integer.parseInt(tf_uid.getText().toString()));
            

            int update;
            update = stmt.executeUpdate();

            if (update > 0) {
                //success
                draw_table();
                clear_form();
                JOptionPane.showMessageDialog(frame, "Data Updated Successfully!");

            } else {
                //fail
                JOptionPane.showMessageDialog(frame, "Data Failed to Update!");

            }

          } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex);
          }  
         
         
        }else{
           JOptionPane.showMessageDialog(frame, "Please completed the form first!");
       }            
    }
     
    public boolean validation_search(){
        if (tf_search.getText().toString().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
     
    public boolean validation_form(){
        boolean valid1 = ((cbx_name.getSelectedIndex()!=0) && (cbx_size.getSelectedIndex()!=0) && (cbx_color.getSelectedIndex()!=0));
        boolean valid2 = ((!tf_quantity.getText().toString().isEmpty()) && (!tf_vendor.getText().toString().isEmpty()));
        
        if (valid1 && valid2){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void clear_form() {
        cbx_name.setSelectedIndex(0);;
        cbx_size.setSelectedIndex(0);
        cbx_color.setSelectedIndex(0);;
        tf_quantity.setText("");
        tf_vendor.setText("");
        tf_search.setText("");
        tf_uid.setText("");
    }
    
    public void hide_delete_update() {
        btn_Update.setVisible(false);
        btn_Delete.setVisible(false);
        btn_Cancel.setVisible(false);
        frame.pack();
    }

    public void display_delete_update() {
        btn_Update.setVisible(true);
        btn_Delete.setVisible(true);
        btn_Cancel.setVisible(true);
        frame.pack();
    }

    public void hide_insert() {
        btn_Insert.setVisible(false);
    }

    public void display_insert() {
        btn_Insert.setVisible(true);
    }
}






/*
#### Notes ####
cbx_name.getSelectedItem().toString()   --->    Get input from combobox
cbx_name.setSelectedIndex(0);   --->    Reset combobox (Go to first index (none))
*/

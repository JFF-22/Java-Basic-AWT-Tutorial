
package formexample;

import java.awt.event.*;

public class WindowClose  extends WindowAdapter{
    @Override
    public void windowClosing(WindowEvent e){
        e.getWindow().dispose();
    }
}
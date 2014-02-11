/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import java.awt.event.MouseEvent;

/**
 *
 * @author Hecktor
 */
public class Controller {
    private UserInterface ui;
    
    public Controller(Model m, UserInterface ui){
        this.ui =ui;
        ui.addJRadioButton2MouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                polerize(evt);
            }
        });
    }
    
    protected void polerize(MouseEvent e){
        String message = "<html><font name='tahoma' size = 30>Now you can hear"
                    + " the text being read by the computer!</font></html>";
        
        ui.showInfoMessage(message);
    }
}

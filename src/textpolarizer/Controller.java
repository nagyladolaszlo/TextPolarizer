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

    private Model model;
    private UserInterface ui;

    public Controller(Model m, UserInterface ui) {
        this.ui = ui;
        this.model = m;
        ui.addJRadioButton2MouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                polarize(evt);
            }
        });
    }

    protected void polarize(MouseEvent e) {
        String message;
        
        model.doPolarize();
        
        if (ui.getjRadioButton1().isSelected()) {
            // AUDIO
            message = "<html><font name='tahoma' size = 30>Now you can hear"
                + " the text being read by the computer!</font></html>";
            ui.showInfoMessage(message);
        } else {
            // TEXT
            message = model.getTextResult();
            ui.showPolarizedText(message);
        }
    }
}

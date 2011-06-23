/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hypemachine;

import data.HypeMachineParser;
import ui.HypeMachineGUI;

/**
 *
 * @author fzakaria
 */
public class HypeMachine {
    
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        //Start the application
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HypeMachineGUI.getInstance().setVisible(true);
            }
        });
        
    }
}

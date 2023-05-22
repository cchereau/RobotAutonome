package org.robot.frame.panelRobot;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPanelSituation extends JPanel {

    private final JEditorPaneAPIGoogleMap panelGoogle;


    public JPanelSituation()
    {

        panelGoogle = new JEditorPaneAPIGoogleMap();


        /////////////////////////////////////////////////////////////////////////////
        // CONFIGURATION DU PANEL GOOGLE
        /////////////////////////////////////////////////////////////////////////////
        try {
            panelGoogle.setApiKey("AIzaSyAhNrcx3CrscwK5dbGC7jm_iLGGW28U3zY");
            //panelGoogle.setRoadmap(panelGoogle.viewHybrid);

            /**Afficher la ville de Strabourg*/
            // panelGoogle.showLocation("paris", "france", 390, 400);

            /** Afficher Paris en fonction ses coordonnées GPS */
            panelGoogle.showCoordinate("48.9068908691", "2.1749124527", 800, 400);
        } catch (Exception ex) {
            Logger.getLogger(JEditorPaneAPIGoogleMap.class.getName()).log(Level.SEVERE, null, ex);
        }


    }




}

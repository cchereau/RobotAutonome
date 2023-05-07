package org.robot.frame.panelRobot;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.robot.constante.globalCte.properties_GoogleKeyAPI;
import static org.robot.constante.globalCte.properties;


public class JPanelSituation extends JPanel {

    private final JEditorPaneAPIGoogleMap panelGoogle;


    public JPanelSituation()
    {

        panelGoogle = new JEditorPaneAPIGoogleMap();


        /////////////////////////////////////////////////////////////////////////////
        // CONFIGURATION DU PANEL GOOGLE
        /////////////////////////////////////////////////////////////////////////////
        try {
            panelGoogle.setApiKey(properties.getProperty(properties_GoogleKeyAPI));
            panelGoogle.setRoadmap(panelGoogle.viewHybrid);

            /**Afficher la ville de Strabourg*/
            // panelGoogle.showLocation("paris", "france", 390, 400);

            /** Afficher Paris en fonction ses coordonnées GPS */
            panelGoogle.showCoordinate("48.9068908691", "2.1749124527", 800, 400);
        } catch (Exception ex) {
            Logger.getLogger(JEditorPaneAPIGoogleMap.class.getName()).log(Level.SEVERE, null, ex);
        }


    }




}

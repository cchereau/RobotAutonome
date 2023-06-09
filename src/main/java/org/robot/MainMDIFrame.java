//
//  https://learn.microsoft.com/fr-fr/azure/cognitive-services/bing-web-search/quickstarts/java
//  https://www.twilio.com/fr/blog/5-requete-http-java

package org.robot;

import org.robot.frame.JInternalFrameRobot;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;

import static org.robot.constante.globalCte.properties;
import static org.robot.constante.globalCte.propertiesFileName;


public class MainMDIFrame extends JFrame implements InternalFrameListener {

    public MainMDIFrame() {
        super("JDesktopPane / JInternalFrame sample");

        // chargement du fichier de propriétées
        FileInputStream in;
        try {
            in = new FileInputStream(propertiesFileName);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("MainMDIFrameJava - Fichier de Propriété");
            throw new RuntimeException(e);
        }


        JInternalFrameRobot internalFramePtf = new JInternalFrameRobot();
        //public static final Robot robot = new Robot();
        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.add(internalFramePtf);
        internalFramePtf.setVisible(true);
        try {
            internalFramePtf.setMaximizable(true); // maximize
            internalFramePtf.setIconifiable(true); // set minimize
            internalFramePtf.setClosable(true); // set closed
            internalFramePtf.setResizable(true); // set resizable
            internalFramePtf.pack();
            internalFramePtf.setMaximum(true);
            internalFramePtf.show();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // Autres Décorations
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.add(desktopPane, BorderLayout.CENTER);

        ////////////////////////////////////////////////////////////////////////////////////
        // Gestion de la fenêtre JTree des options
        ///////////////////////////////////////////////////////////////////////////////////
        contentPane.add(new JTree(), BorderLayout.WEST);
        contentPane.add(new JLabel("La barre de status"), BorderLayout.SOUTH);

        // gestion de l'héritage
        internalFramePtf.addInternalFrameListener(this);

    }

    public static void main(String[] args) {
        //test de l'ap pel unique
        System.out.println("Lancement du Robot");
        SwingUtilities.invokeLater(() -> {


            ///////////////////////////////////////////////////////////////////////////////////////////
            // Chargement de la fenetre portefeuille
            ///////////////////////////////////////////////////////////////////////////////////////////
            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
                MainMDIFrame mainMDIFrame;
                mainMDIFrame = new MainMDIFrame();
                mainMDIFrame.setVisible(true);
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {

    }
}
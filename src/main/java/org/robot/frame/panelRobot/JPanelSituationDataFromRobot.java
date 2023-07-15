package org.robot.frame.panelRobot;

import org.robot.frame.fntGUI.FntGUI;
import org.robot.robotComm.api.JSON.JSONDataRobotSensor;

import javax.swing.*;
import java.awt.*;


public class JPanelSituationDataFromRobot extends JPanel {

    private final String strDateTIme = "date Time : ";
    private final String strLongitude = "longitude : ";
    private final String strLatitude = "latitude : ";
    private final String strAzimut = "azimut : ";
    private final String strVitesse = "vitesse : ";
    private final String strVitesseChenilleDroite = "vitesse Chen. droite : ";
    private final String strVitesseChenilleGauche = "vitesse Chen. gauche : ";
    private final String strTemperature = "temperature : ";
    private final String strHumidite = "humidite : ";
    private final String strANgle = "angle : ";
    private final String strDistance = "distance : ";

    private final JLabel lblDate = new JLabel(strDateTIme);
    private final JLabel lblLongitude = new JLabel(strLongitude);
    private final JLabel lblLatitude = new JLabel(strLatitude);
    private final JLabel lblAzimut = new JLabel(strAzimut);
    private final JLabel lblVitesse = new JLabel(strVitesse);
    private final JLabel lblVitesseChenilleDroite = new JLabel(strVitesseChenilleDroite);
    private final JLabel lblVitesseChenilleGauche = new JLabel(strVitesseChenilleGauche);
    private final JLabel lblTemperature = new JLabel(strTemperature);
    private final JLabel lblHumidite = new JLabel(strHumidite);
    private final JLabel lblAngle = new JLabel(strANgle);
    private final JLabel lblDistance = new JLabel(strDistance);

    public JPanelSituationDataFromRobot() {

        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;

        FntGUI.setBagContraint(gbc, 0, 0, 1, 1, 1, 0.1f);
        this.add(lblDate, gbc);

        FntGUI.setBagContraint(gbc, 0, 1, 1, 1, 1, 0.1f);
        this.add(lblLongitude, gbc);

        FntGUI.setBagContraint(gbc, 1, 1, 1, 1, 1, 0.1f);
        this.add(lblLatitude, gbc);

        FntGUI.setBagContraint(gbc, 0, 2, 1, 1, 1, 0.1f);
        this.add(lblAzimut, gbc);

        FntGUI.setBagContraint(gbc, 1, 2, 1, 1, 1, 0.1f);
        this.add(lblVitesse, gbc);

        FntGUI.setBagContraint(gbc, 0, 3, 1, 1, 1, 0.1f);
        this.add(lblVitesseChenilleDroite, gbc);

        FntGUI.setBagContraint(gbc, 1, 3, 1, 1, 1, 0.1f);
        this.add(lblVitesseChenilleGauche, gbc);

        FntGUI.setBagContraint(gbc, 0, 4, 1, 1, 1, 0.1f);
        this.add(lblHumidite, gbc);

        FntGUI.setBagContraint(gbc, 1, 4, 1, 1, 1, 0.1f);
        this.add(lblTemperature, gbc);

        FntGUI.setBagContraint(gbc, 0, 5, 1, 1, 1, 0.1f);
        this.add(lblAngle, gbc);

        FntGUI.setBagContraint(gbc, 1, 5, 1, 1, 1, 0.1f);
        this.add(lblDistance, gbc);

    }


    public void setDataRobot(JSONDataRobotSensor dataRobot) {
        try {
            lblDate.setText(strDateTIme + dataRobot.getDateTime().toString());
            lblLongitude.setText(strLongitude + dataRobot.getLongitude().toString());
            lblLatitude.setText(strLatitude + dataRobot.getLatitude().toString());
            lblAzimut.setText(strAzimut + dataRobot.getAzimut().toString());
            lblVitesse.setText(strVitesse + dataRobot.getVitesse().toString());
            lblVitesseChenilleDroite.setText(strVitesseChenilleDroite + dataRobot.getvChenilleDroite().toString());
            lblVitesseChenilleGauche.setText(strVitesseChenilleGauche + dataRobot.getvChenilleGauche().toString());
            lblTemperature.setText(strTemperature + dataRobot.getTemperature().toString());
            lblHumidite.setText(strHumidite + dataRobot.getHumidite().toString());
            lblAngle.setText(strANgle + (dataRobot.getAngle() - 90));
            lblDistance.setText(strDistance + dataRobot.getDistance());
        } catch (Exception e) {
            System.out.println(" JPanelSituationDataFromRobot - dateUpdated() ");
        }
    }
}





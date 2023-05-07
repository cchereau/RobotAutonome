package org.robot.frame.panelRobot;

import org.robot.constante.globalCte.enActionRobot;
import org.robot.frame.fntGUI.FntGUI;
import org.robot.robotComm.Robot;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.time.LocalDateTime;


public class JPanelAction extends JPanel {
    /////////////////////////////////////////////////////////////////////////////
    // PANEL ACTION
    /////////////////////////////////////////////////////////////////////////////

    // private final Robot actionRobot = new Robot();

    // défintion des JPanel
    private JPanel panelCommande = new JPanel();
    private JPanel panelStatus = new JPanel();

    // définition du JBUtton
    private JButton btnUp = new JButton(enActionRobot.RobotAvant.toString());
    private JButton btnDown = new JButton(enActionRobot.RobotArriere.toString());
    private JButton btnLeft = new JButton(enActionRobot.RobotGauche.toString());
    private JButton btnRight = new JButton(enActionRobot.RobotDroite.toString());
    private JButton btnStop = new JButton(enActionRobot.RobotStop.toString());
    private JTextArea txtArea = new JTextArea(10, 100);
    private JScrollPane scrollTextArea = new JScrollPane(txtArea);   // JTextArea is placed in a JScrollPane.
    private JSlider sliderVitesse = new JSlider();
    private JSlider sliderRadar = new JSlider();
    private Robot robot;

    public JPanelAction( ) {

        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.NONE;
        FntGUI.setBagContraint(gbc, 1, 0, 1, 1, 1, 0.1f);
        this.add(btnUp, gbc);
        FntGUI.setBagContraint(gbc, 0, 1, 1, 1, 1, 0.1f);
        this.add(btnLeft, gbc);
        FntGUI.setBagContraint(gbc, 1, 1, 1, 1, 1, 0.1f);
        this.add(btnStop, gbc);
        FntGUI.setBagContraint(gbc, 2, 1, 1, 1, 1, 0.1f);
        this.add(btnRight, gbc);
        FntGUI.setBagContraint(gbc, 1, 3, 1, 1, 1, 0.1f);
        this.add(btnDown, gbc);
        FntGUI.setBagContraint(gbc, 0, 4, 3, 1, 1, 0.1f);
        this.add(sliderVitesse, gbc);
        FntGUI.setBagContraint(gbc, 0, 5, 3, 1, 1, 0.1f);
        this.add(sliderRadar, gbc);
        FntGUI.setBagContraint(gbc, 3, 0, 4, 4);
        this.add(scrollTextArea, gbc);
        // gestion des ordres de commandes
        btnUp.addActionListener(e -> {
            this.robot.addAction(enActionRobot.RobotAvant, sliderVitesse.getValue());
        });
        btnDown.addActionListener(e -> {
            this.robot.addAction(enActionRobot.RobotArriere, sliderVitesse.getValue());
        });
        btnStop.addActionListener(e -> {
            this.robot.addAction(enActionRobot.RobotStop, sliderVitesse.getValue());
        });
        btnLeft.addActionListener(e -> {
            this.robot.addAction(enActionRobot.RobotGauche, sliderVitesse.getValue());
        });
        btnRight.addActionListener(e -> {
            robot.addAction(enActionRobot.RobotDroite, sliderVitesse.getValue());
        });
        // configuration JScroll
        scrollTextArea.setBounds(160, 84, 150, 50);
        scrollTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
        scrollTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollTextArea.setAutoscrolls(false);
        // gestion du slider Vitesse
        sliderVitesse.setMinimum(0);
        sliderVitesse.setMaximum(100);
        sliderVitesse.setPaintLabels(true);
        sliderVitesse.setPaintTicks(true);
        sliderVitesse.setMajorTickSpacing(10);
        sliderVitesse.setMinorTickSpacing(5);
        sliderVitesse.setValue(50);
        sliderVitesse.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider slider = (JSlider) evt.getSource();
                if (!slider.getValueIsAdjusting()) {
                }
            }
        });

        // gestion du slider Vitesse
        sliderRadar.setMinimum(-90);
        sliderRadar.setMaximum(90);
        sliderRadar.setPaintLabels(true);
        sliderRadar.setPaintTicks(true);
        sliderRadar.setMajorTickSpacing(45);
        sliderRadar.setMinorTickSpacing(10);
        sliderRadar.setValue(0);

        sliderRadar.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider slider = (JSlider) evt.getSource();
                if (!slider.getValueIsAdjusting()) {
                    if (sliderRadar.getValue() < 0)
                        robot.addAction(enActionRobot.RadarDroite, Math.abs(sliderRadar.getValue()));
                    else robot.addAction(enActionRobot.RadarGauche, sliderRadar.getValue());
                }
            }
        });
    }


    public void attachRobot(Robot robot) {
        this.robot = robot;
    }

    public void setDataRobotReturnAction(JSONDataRobotReturnAction dataRobotReturnAction)
    {
        if(dataRobotReturnAction!=null)
            txtArea.insert(dataRobotReturnAction.toString(),0);
        else txtArea.insert(LocalDateTime.now().toString() + "JPanelAction : Data Not Receive",0);
    }


}

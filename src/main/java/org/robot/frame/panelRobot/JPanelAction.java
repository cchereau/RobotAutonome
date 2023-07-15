package org.robot.frame.panelRobot;

import org.robot.constante.globalCte.enActionRobot;
import org.robot.frame.fntGUI.FntGUI;
import org.robot.robotComm.Robot;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

public class JPanelAction extends JPanel {
    /////////////////////////////////////////////////////////////////////////////
    // PANEL ACTION
    /////////////////////////////////////////////////////////////////////////////
    private final JTextArea txtArea;
    private Robot robot;
    private final JSlider sliderVitesse = new JSlider();
    private final JSlider sliderVirage = new JSlider();
    private final JSlider sliderRadar = new JSlider();


    public JPanelAction() {

        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        gbc.fill = GridBagConstraints.NONE;

        JLabel lblVitesse = new JLabel("Vitesse");
        FntGUI.setBagContraint(gbc, 0, 0, 1, 1, 1, 0.1f);
        this.add(lblVitesse, gbc);
        FntGUI.setBagContraint(gbc, 1, 0, 1, 1, 1, 0.1f);
        this.add(sliderVitesse, gbc);

        JLabel lblAnlgeRobot = new JLabel("Virage °");
        FntGUI.setBagContraint(gbc, 0, 1, 1, 1, 1, 0.1f);
        this.add(lblAnlgeRobot, gbc);
        FntGUI.setBagContraint(gbc, 1, 1, 1, 1, 1, 0.1f);
        this.add(sliderVirage, gbc);

        JLabel lblAnlgeRadar = new JLabel("Radar °");
        FntGUI.setBagContraint(gbc, 0, 2, 1, 1, 1, 0.1f);
        this.add(lblAnlgeRadar, gbc);
        FntGUI.setBagContraint(gbc, 1, 2, 1, 1, 1, 0.1f);
        this.add(sliderRadar, gbc);

        JButton btnReset = new JButton("Reset");
        FntGUI.setBagContraint(gbc, 2, 0, 1, 1);
        this.add(btnReset, gbc);

        txtArea = new JTextArea(10, 100);
        JScrollPane scrollTextArea = new JScrollPane(txtArea);   // JTextArea is placed in a JScrollPane.
        FntGUI.setBagContraint(gbc, 3, 1, 3, 4);
        this.add(scrollTextArea, gbc);

        btnReset.addActionListener(e -> robot.addSequence(enActionRobot.SystemRobot, 0));

        // configuration JScroll
        scrollTextArea.setBounds(160, 84, 150, 50);
        scrollTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
        scrollTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollTextArea.setAutoscrolls(false);
        txtArea.setLineWrap(true);

        // gestion du slider Vitesse
        sliderVitesse.setMinimum(-100);
        sliderVitesse.setMaximum(100);
        sliderVitesse.setPaintLabels(true);
        sliderVitesse.setPaintTicks(true);
        sliderVitesse.setMajorTickSpacing(50);
        sliderVitesse.setMinorTickSpacing(25);
        sliderVitesse.setValue(0);
        sliderVitesse.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent event) {
            }

            @Override
            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                if (!sliderVitesse.getValueIsAdjusting()) {
                    enActionRobot actionRobot;
                    if (sliderVitesse.getValue() < 0) actionRobot = enActionRobot.RobotArriere;
                    else if (sliderVitesse.getValue() > 0) actionRobot = enActionRobot.RobotAvant;
                    else actionRobot = enActionRobot.RobotStop;
                    robot.addSequence(actionRobot, sliderVitesse.getValue());
                }
            }
        });

        // gestion du slider angleRadar
        sliderRadar.setMinimum(-90);
        sliderRadar.setMaximum(90);
        sliderRadar.setPaintLabels(true);
        sliderRadar.setPaintTicks(true);
        sliderRadar.setMajorTickSpacing(45);
        sliderRadar.setMinorTickSpacing(10);
        sliderRadar.setValue(0);
        sliderRadar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!sliderRadar.getValueIsAdjusting()) {
                    enActionRobot actionRobot;
                    if (sliderRadar.getValue() < 0) actionRobot = enActionRobot.RobotRadarDroite;
                    else actionRobot = enActionRobot.RobotRadarGauche;
                    robot.addSequence(actionRobot, Math.abs(sliderRadar.getValue()));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        // gestion de l'angle De VItesser
        sliderVirage.setMinimum(-90);
        sliderVirage.setMaximum(90);
        sliderVirage.setPaintLabels(true);
        sliderVirage.setPaintTicks(true);
        sliderVirage.setMajorTickSpacing(45);
        sliderVirage.setMinorTickSpacing(10);
        sliderVirage.setValue(0);
        sliderVirage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!sliderVirage.getValueIsAdjusting()) {
                    enActionRobot actionRobot;
                    if (sliderVirage.getValue() < 0) actionRobot = enActionRobot.RobotDroite;
                    else actionRobot = enActionRobot.RobotGauche;
                    robot.addSequence(actionRobot, Math.abs(sliderVirage.getValue()));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    public void attachRobot(Robot robot) {
        this.robot = robot;
    }

    public void setDataRobotReturnAction(JSONDataRobotReturnAction dataRobotReturnAction) {
        if (dataRobotReturnAction == null)
            return;
        enActionRobot actionRobot = dataRobotReturnAction.getAction();

        int actionValeur = dataRobotReturnAction.getValue();

        txtArea.insert(LocalDateTime.now() + " : " + actionRobot.getLabel() + "  :  " + actionValeur + "\n", 0);

        /*// mise a jour des statuts
        switch (actionRobot) {
            case RobotGauche, RobotDroite:
                sliderVirage.setValue(actionValeur);
                break;
            case RobotArriere, RobotAvant:
                sliderVitesse.setValue(actionValeur);
                break;
            case RobotRadarDroite, RobotRadarGauche:
                sliderRadar.setValue(actionValeur);
                break;
            case RobotStop:
                sliderVitesse.setValue(0);
                sliderVirage.setValue(0);
                break;

            default:
                break;
        }*/

    }

}

package org.robot.frame;

import org.robot.frame.panelRobot.JPanelAction;
import org.robot.frame.panelRobot.JPanelSituation;
import org.robot.frame.panelRobot.JPanelSituationDataFromRobot;
import org.robot.robotComm.EventListnerRobotDataReady;
import org.robot.robotComm.Robot;
import org.robot.robotComm.api.EventDataRobot;
import org.robot.robotComm.api.JSON.JSONDataRobot;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;

//////////////////////////////////////////////////////////////////////////////////////////////////
// CLasse liée à une instance de Robot donc gère les lancements, arrêts...
//////////////////////////////////////////////////////////////////////////////////////////////////
//
///////////////////////////////////////////////////////////////////////////////////////////////////
public class JInternalFrameRobot extends JInternalFrame implements EventListnerRobotDataReady {

    private final JPanelAction panelAction;
    private final JPanelSituation panelSituation;
    private final JPanelSituationDataFromRobot panelDataFromRobot;
    private Robot robot;

    public JInternalFrameRobot() {
        super("Robot V0.12", false, true, false, false);

        panelAction = new JPanelAction();
        panelSituation = new JPanelSituation();
        panelDataFromRobot=new JPanelSituationDataFromRobot();

        this.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                System.out.println(this.getClass().getCanonicalName() + " -  internal Frame Opened");

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                //YOUR CODE HERE
                int option = JOptionPane.showConfirmDialog(null, "Really Close?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.out.println(this.getClass().getName() + " - Je tue le robot");
                    robot.arret();
                    dispose();
                }
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                System.out.println(this.getClass().getCanonicalName() + " -  internal Frame Closed");

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
        });
        ////////////////////////////////////////////////////////////////////////////
        // CONFIGURATION DU ROBOT
        ////////////////////////////////////////////////////////////////////////////
        robot = new Robot();
        robot.addEventListnerDataRobotReturnAction(this);
        robot.start();

        // affectatop du robot sur les Panel
        panelAction.attachRobot(robot);

        JSplitPane secondSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelSituation,panelDataFromRobot);
        secondSplitPane.setResizeWeight(0.5d);
        secondSplitPane.setSize(new Dimension());

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelAction, secondSplitPane);
        mainSplitPane.setResizeWeight(0.5d);
        mainSplitPane.setSize(new Dimension());


        // intilisation des contrôle
        this.add(mainSplitPane);
        this.setEnabled(true);

        // intilisation des contrôle
        this.add(mainSplitPane);
        this.setEnabled(true);
        this.pack();

    }


    @Override
    public void DataRobotReady(JSONDataRobot dataRobot) {
        panelDataFromRobot.set(robot.getDataRobot());
        panelDataFromRobot.repaint();

    }
}

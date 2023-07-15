package org.robot.robotComm;

import org.joda.time.LocalDateTime;
import org.robot.constante.globalCte.enActionRobot;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;
import org.robot.robotComm.api.JSON.JSONDataRobotSensor;
import org.robot.robotComm.api.RobotAPI;
import org.robot.robotComm.controles.Controle;
import org.robot.robotComm.sequences.*;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.robot.constante.globalCte.enActionRobot.RobotData;
import static org.robot.constante.globalCte.enActionRobot.RobotStop;

public class Robot extends Thread {
    private final List<RobotAction> robotActions = new ArrayList<>();
    private final RobotAPI robotAPI;
    private enActionRobot statusRobot;
    private final LinkedHashMap<LocalDateTime, JSONDataRobotSensor> treeMapDataRobotReceived = new LinkedHashMap<>();
    private final Controle controle;
    protected EventListenerList eventListeners = new EventListenerList();
    private enActionRobot lastActionRobot;
    private JSONDataRobotReturnAction dataRobotResultAction;
    private JSONDataRobotSensor dataRobotSensor;
    private boolean running;

    public Robot() {
        controle = new Controle();
        this.robotAPI = new RobotAPI(false);
        this.running = true;
        this.lastActionRobot = this.statusRobot = RobotStop;
        addSequence(this.statusRobot, -1);
    }
    @Override
    public void run() {
        while (running) {
            List<RobotAction> arr = robotActions.stream().filter(e -> !e.isExecuted()).toList();
            for (RobotAction action : arr) {
                if (action.getActionRobot() == RobotData) {
                    dataRobotSensor = robotAPI.getDataRobot();
                    addValidatedData(dataRobotSensor);
                    fireDataUpdatedFromRobot(entypeDataReceived.DataSensor);
                    System.out.println("Robot Data");
                } else {
                    dataRobotResultAction = robotAPI.setActionRobot(action.getActionRobot(), action.getValue());
                    this.statusRobot = action.getActionRobot();
                    fireDataUpdatedFromRobot(entypeDataReceived.DataAction);
                    System.out.println(action.getActionRobot());
                }
                // mise a jour du statur executé dans l'action
                action.isExecuted(true);
            }

            // CONTROLE DU ROBOT
            if ((controle.isValidated(dataRobotSensor.getDistance())) && (!lastActionRobot.equals(RobotStop))) {
                addSequence(RobotStop, 0);
                fireDataUpdatedFromRobot(entypeDataReceived.DataAction);
                System.out.println("Robot Control");
            }

            // PAUSE DANS LE RUN
            try {
                Thread.sleep(100);
            } catch (InterruptedException io) {
                System.out.println(this.getClass().getCanonicalName() + "-  PROCESS ROBOT API STOPED");
                throw new RuntimeException(io);
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addSequence(enActionRobot actionRobot, Integer dataRobot) {

        RobotAction robotAction = new RobotAction(actionRobot, dataRobot);
        // récupération des séquences d'action du robot
        Sequence sequence = new Sequence(robotAction);
        switch (robotAction.getActionRobot()) {
            case RobotRadarGauche, RobotRadarDroite:
                sequence = new SequenceRobotRadar(robotAction);
                break;
            case RobotArriere, RobotAvant:
                sequence = new SequenceRobotAvance(robotAction);
                break;
            case RobotDroite, RobotGauche:
                sequence = new SequenceRobotTurn(robotAction);
                break;
            case RobotStop:
                sequence = new SequenceRobotStop(robotAction);
                break;
            case RobotData:
                break;
            default:
                sequence = new SequenceRobotKeepAlive();
                break;
        }
        this.robotActions.addAll(sequence.getActions().values());
        lastActionRobot = actionRobot;
    }
    public void arret() {
        running = false;
    }
    private void addValidatedData(JSONDataRobotSensor dataInput) {
        if (treeMapDataRobotReceived.size() == 0)
            treeMapDataRobotReceived.put(LocalDateTime.now(), new JSONDataRobotSensor());
        if (dataInput.getDistance() > 0)
            treeMapDataRobotReceived.put(LocalDateTime.now(), dataInput);                // sauvegarde de la data
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    // GESTIONNAIRE DES EVENEMENTS
    /////////////////////////////////////////////////////////////////////////////////////////////
    public void addEventListnerDataRobotReturnAction(EventListnerRobot eventListnerRobotDataReady) {
        eventListeners.add(EventListnerRobot.class, eventListnerRobotDataReady);
    }
    private enum entypeDataReceived {DataSensor, DataAction}
    protected void fireDataUpdatedFromRobot(entypeDataReceived dataAction) {
        Object[] listeners = eventListeners.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListnerRobot.class) {
                switch (dataAction) {
                    case DataSensor -> ((EventListnerRobot) listeners[i + 1]).DataRobotReady(dataRobotSensor);
                    case DataAction ->
                            ((EventListnerRobot) listeners[i + 1]).DataReturnActionRobot(dataRobotResultAction);
                }
            }
        }
    }
}


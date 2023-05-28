package org.robot.robotComm;

import org.joda.time.LocalDateTime;
import org.robot.constante.globalCte.enActionRobot;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;
import org.robot.robotComm.api.JSON.JSONDataRobotSensor;
import org.robot.robotComm.api.RobotAPI;
import org.robot.robotComm.controles.Controle;
import org.robot.robotComm.sequences.Sequence;
import org.robot.robotComm.sequences.SequenceRobotKeepAlive;
import org.robot.robotComm.sequences.SequenceRobotTurn;

import javax.swing.event.EventListenerList;
import java.util.*;
import java.util.stream.Collectors;

import static org.robot.constante.globalCte.enActionRobot.*;

public class Robot extends Thread {
    private final RobotAPI robotAPI;
    private enActionRobot statusRobot;

    private final HashMap<LocalDateTime, RobotAction> robotActions = new HashMap<>();
    private NavigableMap<LocalDateTime,JSONDataRobotSensor> datasRobot = new TreeMap<>();

    protected EventListenerList eventListeners = new EventListenerList();
    private JSONDataRobotReturnAction dataRobotResultAction;
    private JSONDataRobotSensor dataRobotSensor;
    private final Controle controle;

    public enActionRobot getStatusMobility() {
        return this.statusRobot;
    }
    private boolean running;

    public Robot() {
        controle = new Controle();
        this.robotAPI = new RobotAPI();
        this.running = true;
        this.statusRobot = enActionRobot.RobotStop;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // on regarde si besoin de relancer le robot filtre sur les actions non executée
                Map<LocalDateTime, RobotAction> mapActionRobotNonExecuted = filterOnNonExecute(this.robotActions);

                if (mapActionRobotNonExecuted.isEmpty())
                    this.addAction(RobotControl, 0);

                mapActionRobotNonExecuted.put(LocalDateTime.now(), new RobotAction(RobotData, 0));

                // lecture de toutes les actions à faire pour arriver à l'action demandée.
                for (Map.Entry<LocalDateTime, RobotAction> entry : mapActionRobotNonExecuted.entrySet()) {
                    switch (entry.getValue().getActionRobot()) {
                        case RobotData:
                            dataRobotSensor = robotAPI.getDataRobot();
                            addValidatedData(dataRobotSensor);
                            fireDataUpdatedFromRobot(entypeDataReceived.DataSensor);
                            break;
                        case RobotControl:
                            Map.Entry<LocalDateTime, JSONDataRobotSensor> lastEntry = datasRobot.lastEntry();
                            if (controle.isValidated(lastEntry.getValue().getDistance())) this.addAction(RobotStop, 0);
                            fireDataUpdatedFromRobot(entypeDataReceived.DataAction);
                            break;
                        default:
                            dataRobotResultAction = robotAPI.setActionRobot(entry.getValue().getActionRobot(), entry.getValue().getValue());
                            this.statusRobot = entry.getValue().getActionRobot();
                            fireDataUpdatedFromRobot(entypeDataReceived.DataAction);
                            break;
                    }
                    entry.getValue().isExecuted(true);
                    Thread.sleep(500);
                }
            } catch (InterruptedException io) {
                System.out.println(this.getClass().getCanonicalName() + "-  PROCESS ROBOT API STOPED");
                throw new RuntimeException(io);
            }

        }
    }

    public JSONDataRobotReturnAction getDataRobotResultAction() {
        return this.dataRobotResultAction;
    }

    public JSONDataRobotSensor getDataRobot() {
        return this.dataRobotSensor;
    }

    public void arret() {
        running = false;
    }

    public void addAction(enActionRobot actionRobot, Integer dataRobot) {
        Sequence sequence;
        RobotAction robotAction = new RobotAction(actionRobot, dataRobot);
        // récupération des séquences d'action du robot
        switch (robotAction.getActionRobot()) {
            case RobotArriere, RobotAvant, RobotStop, RobotGauche, RobotRadarGauche, RobotRadarDroite:
                this.robotActions.put(robotAction.getDateTime(), robotAction);
                break;

            case RobotDroite:
                sequence = new SequenceRobotTurn(robotAction);
                this.robotActions.putAll(sequence.getActions());
                break;

            case RobotControl:
                sequence = new SequenceRobotKeepAlive(robotAction);
                this.robotActions.putAll(sequence.getActions());
                break;


            default:

                break;
        }

    }

    public void addEventListnerDataRobotReturnAction(EventListnerRobot eventListnerRobotDataReady) {
        eventListeners.add(EventListnerRobot.class, eventListnerRobotDataReady);
    }

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

    private void addValidatedData(JSONDataRobotSensor dataInput){

        if(datasRobot.size()==0) datasRobot.put(LocalDateTime.now(), new JSONDataRobotSensor());
        if(dataInput.getDistance()>0)
            datasRobot.put(LocalDateTime.now(),dataInput);                // sauvegarde de la data

    }

    private Map<LocalDateTime, RobotAction> filterOnNonExecute(Map<LocalDateTime, RobotAction> _map) {
        Map<LocalDateTime, RobotAction> hashMapfilter;
        hashMapfilter = _map.entrySet().stream().filter(y -> !y.getValue().isExecuted())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return hashMapfilter;
    }
    private enum entypeDataReceived {DataSensor, DataAction}

}


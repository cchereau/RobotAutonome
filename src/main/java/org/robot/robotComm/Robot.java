package org.robot.robotComm;

import org.joda.time.LocalDateTime;
import org.robot.constante.globalCte.enActionRobot;
import org.robot.robotComm.api.JSON.JSONDataRobotSensor;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;
import org.robot.robotComm.api.RobotAPI;

import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.robot.constante.globalCte.enActionRobot.DataRobot;

public class Robot extends Thread {
    private final RobotAPI robotAPI;
    private enActionRobot statusRobot;
    private final HashMap<LocalDateTime, RobotAction> robotActions = new HashMap<>();
    protected EventListenerList eventListeners = new EventListenerList();
    private JSONDataRobotReturnAction dataRobotResultAction;
    private JSONDataRobotSensor dataRobotSensor;
    private Map<LocalDateTime, RobotAction> mapActionRobotNonExecuted ;
    private Map<LocalDateTime, RobotAction> mapActioRobotPriorty;
    private Map<LocalDateTime, RobotAction> mapActioRobotSortOlder;

    public enActionRobot getStatusMobility() {
        return this.statusRobot;
    }

    private enum entypeDataReceived{DataSensor,DataAction};


    private boolean running;


    public Robot() {

        this.robotAPI = new RobotAPI();
        this.running=true;
        this.statusRobot = enActionRobot.RobotStop;
    }
    public JSONDataRobotReturnAction getDataRobotResultAction() {
        return this.dataRobotResultAction;
    }
    public JSONDataRobotSensor getDataRobot() {
        return this.dataRobotSensor;
    }
    @Override
    public void run() {
        while (running) {
            try {
                System.out.println("je suis le Thread du robot et je tourne tout seul");
                mapActionRobotNonExecuted = filterOnNonExecute(robotActions);
                if (this.mapActionRobotNonExecuted.isEmpty())
                    this.addAction(DataRobot, 0);

                // on regarde si besoin de relancer le robot filtre sur les actions non executée
                mapActionRobotNonExecuted = filterOnNonExecute(robotActions);
                mapActioRobotPriorty = orderOnPriorities(mapActionRobotNonExecuted);
                mapActioRobotSortOlder = orderByOldest(mapActioRobotPriorty);

                // si on a rien à faire alors on demande des information du statut
                 if (!mapActioRobotSortOlder.isEmpty()) {
                    LocalDateTime keyMap = mapActionRobotNonExecuted.keySet().stream().findFirst().get();
                    RobotAction robotAction = robotActions.get(keyMap);
                        if (robotAction.getActionRobot() == DataRobot) {
                            try {
                                dataRobotSensor = robotAPI.getDataRobot();
                                fireDataUpdatedFromRobot(entypeDataReceived.DataSensor);
                            } catch (IOException io) {
                                System.out.println("ROBOT.java -> Exception Retrieve JSON DATA" + io.getMessage());
                            }
                        } else {
                            try {
                                dataRobotResultAction = robotAPI.setActionRobot(robotAction.getActionRobot(), robotAction.getValue());
                                fireDataUpdatedFromRobot(entypeDataReceived.DataAction);

                                switch (robotAction.getActionRobot())
                                {
                                    case RobotArriere, RobotAvant, RobotDroite, RobotGauche, RobotStop:statusRobot = robotAction.getActionRobot();break;
                                    default:break;
                                }


                            } catch (IOException io) {
                                System.out.println("ROBOT.java -> Exception Send Data" + io.getMessage());
                            }
                        }
                        robotAction.isExecuted(true);
                }
                Thread.sleep(500);
            } catch (InterruptedException io) {
                System.out.println(this.getClass().getCanonicalName() + "-  PROCESS ROBOT API STOPED");
                throw new RuntimeException(io);
            }
        }
    }
    public void arret() {
        running = false;
    }
    public void addAction(enActionRobot actionRobot, Integer dataRobot) {
        RobotAction robotAction = new RobotAction(actionRobot, dataRobot);
        this.robotActions.put(robotAction.getDateTime(), robotAction);
    }
    public void addEventListnerDataRobotReturnAction(EventListnerRobot eventListnerRobotDataReady) {
        eventListeners.add(EventListnerRobot.class, eventListnerRobotDataReady);
    }
    protected void fireDataUpdatedFromRobot(entypeDataReceived dataAction) {
        Object[] listeners = eventListeners.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListnerRobot.class) {
                switch (dataAction)
                {
                    case DataSensor -> ((EventListnerRobot) listeners[i + 1]).DataRobotReady(dataRobotSensor);
                    case DataAction -> ((EventListnerRobot) listeners[i + 1]).DataReturnActionRobot(dataRobotResultAction);
                }
            }
        }
    }

    // filtre sur les Map
    private Map<LocalDateTime, RobotAction> orderByOldest(Map<LocalDateTime, RobotAction> _map) {
        return _map
                .entrySet()
                .stream()
                .sorted(Map.Entry.<LocalDateTime, RobotAction>comparingByKey().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newValue) -> oldVal,
                        LinkedHashMap::new));
    }
    private Map<LocalDateTime, RobotAction> orderOnPriorities(Map<LocalDateTime, RobotAction> _map) {
        Comparator<RobotAction> byPriority = (RobotAction obj1, RobotAction obj2) -> obj2.getActionRobot().getPriority().compareTo(obj1.getActionRobot().getPriority());

        return _map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(byPriority))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
    private Map<LocalDateTime, RobotAction> filterOnNonExecute(Map<LocalDateTime, RobotAction> _map) {

        Map<LocalDateTime,RobotAction> hashMapfilter;
        hashMapfilter= _map.entrySet().stream().filter(y -> !y.getValue().isExecuted())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()) );
        return hashMapfilter;
    }




}


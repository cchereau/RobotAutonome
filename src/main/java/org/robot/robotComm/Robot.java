package org.robot.robotComm;

import org.joda.time.LocalDateTime;
import org.robot.constante.globalCte.enActionRobot;
import org.robot.robotComm.api.JSON.JSONDataRobot;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;
import org.robot.robotComm.api.RobotAPI;

import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.robot.constante.globalCte.enActionRobot.DataRobot;

public class Robot extends Thread {
    private final RobotAPI robotAPI;
    //private Timer timer;
    private final HashMap<LocalDateTime, RobotAction> robotActions = new HashMap<>();
    protected EventListenerList eventListeners = new EventListenerList();
    JSONDataRobotReturnAction dataRobotResultAction;
    JSONDataRobot dataRobot;
    Map<LocalDateTime, RobotAction> mapActionRobotNonExecuted ;
    Map<LocalDateTime, RobotAction> mapActioRobotPriorty;
    Map<LocalDateTime, RobotAction> mapActioRobotSortOlder;

    private boolean running;


    public Robot() {

        this.robotAPI = new RobotAPI();
        this.running=true;
    }

    public JSONDataRobotReturnAction getDataRobotResultAction() {
        return this.dataRobotResultAction;
    }

    public JSONDataRobot getDataRobot() {
        return this.dataRobot;
    }

    public void addAction(enActionRobot actionRobot, Integer dataRobot) {
        RobotAction robotAction = new RobotAction(actionRobot, dataRobot);
        this.robotActions.put(robotAction.getDateTime(), robotAction);
    }

    @Override
    public void run() {
        System.out.println("je suis le Thread du robot et je tourne une fois");

        while (running) {

            try {
                System.out.println("je suis le Thread du robot et je tourne tout seul");
                mapActionRobotNonExecuted = filterOnNonExecute(robotActions);
                if (this.mapActionRobotNonExecuted.isEmpty())
                    this.addAction(DataRobot, 0);

                // on regarde si besoin de relancer le robot filtre sur les actions non executée
                mapActionRobotNonExecuted = filterOnNonExecute(robotActions);
                mapActioRobotPriorty = orderOnPriorities(mapActionRobotNonExecuted);
                mapActioRobotSortOlder = orderByOLdest(mapActioRobotPriorty);

                // si on a rien à faire alors on demande des information du statut
                 if (!mapActioRobotSortOlder.isEmpty()) {

                    LocalDateTime keyMap = mapActionRobotNonExecuted.keySet().stream().findFirst().get();
                    RobotAction robotAction = robotActions.get(keyMap);
                    try {
                        if (robotAction.getActionRobot() == DataRobot) {
                            dataRobot = robotAPI.getDataRobot();
                            fireDataUpdatedFromRobot();
                        } else {
                            dataRobotResultAction = robotAPI.setActionRobot(robotAction.getActionRobot(), robotAction.getValue());
                        }
                        robotAction.isExecuted(true);

                    } catch (IOException ioException) {
                        System.out.println("Robot.java - Public run :"+ioException.getMessage());
                    }
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

    public void addEventListnerDataRobotReturnAction(EventListnerRobotDataReady eventListnerRobotDataReady) {
        eventListeners.add(EventListnerRobotDataReady.class, eventListnerRobotDataReady);
    }


    protected void fireDataUpdatedFromRobot() {
        Object[] listeners = eventListeners.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListnerRobotDataReady.class) {
                ((EventListnerRobotDataReady) listeners[i + 1]).DataRobotReady(dataRobot);
            }
        }
    }


    private Map<LocalDateTime, RobotAction> orderByOLdest(Map<LocalDateTime, RobotAction> _map) {
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


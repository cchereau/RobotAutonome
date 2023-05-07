package org.robot.robotComm.api;

import org.robot.robotComm.api.JSON.JSONDataRobot;

import java.util.EventObject;

public abstract class EventDataRobot extends EventObject {


    /**
     * Constructs a prototypical Event.
     *
     * @throws IllegalArgumentException if source is null
     */
    public EventDataRobot(Object source) {
        super(source);
    }

    public abstract void dataUpdated(JSONDataRobot dataRobot);
}

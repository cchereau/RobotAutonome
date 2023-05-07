package org.robot.robotComm;

import org.robot.robotComm.api.EventDataRobot;
import org.robot.robotComm.api.JSON.JSONDataRobot;

import java.util.EventListener;

public interface EventListnerRobotDataReady extends EventListener {

    void DataRobotReady(JSONDataRobot dataRobot);
}

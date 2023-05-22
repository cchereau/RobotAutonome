package org.robot.robotComm;

import org.robot.robotComm.api.JSON.JSONDataRobotSensor;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;

import java.util.EventListener;

public interface EventListnerRobot extends EventListener {

    void DataRobotReady(JSONDataRobotSensor dataRobot);
    void DataReturnActionRobot(JSONDataRobotReturnAction dataReturn);
}

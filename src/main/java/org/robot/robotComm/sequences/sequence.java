package org.robot.robotComm.sequences;

import org.robot.robotComm.Robot;
import org.robot.robotComm.RobotAction;
import org.robot.robotComm.api.JSON.JSONDataRobotSensor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;

public class sequence {

    protected HashMap<LocalDateTime, RobotAction> actions = new HashMap<>();
    protected JSONDataRobotSensor dataRobotFrom = new JSONDataRobotSensor();
    protected JSONDataRobotSensor dataRobotTo=new JSONDataRobotSensor();
    protected Robot robot;

    public sequence(Robot robot)
    {
        this.robot = robot;
    }
    public HashMap<LocalDateTime,RobotAction> getActions(){return this.actions;}








}

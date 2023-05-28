package org.robot.robotComm.sequences;

import org.joda.time.LocalDateTime;
import org.robot.robotComm.RobotAction;

import java.util.HashMap;

public class Sequence {

    protected HashMap<LocalDateTime, RobotAction> actions = new HashMap<LocalDateTime, RobotAction>();
    private final RobotAction robotAction;

    public Sequence(RobotAction robotAction) {
        this.robotAction = robotAction;
    }

    public HashMap<org.joda.time.LocalDateTime, RobotAction> getActions() {
        return this.actions;
    }


}

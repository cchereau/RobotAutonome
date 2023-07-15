package org.robot.robotComm.sequences;

import org.robot.robotComm.RobotAction;

import static org.robot.constante.globalCte.enActionRobot.RobotData;

public class SequenceRobotRadar extends Sequence {
    public SequenceRobotRadar(RobotAction action) {
        super(action);
        actions.put(action.getDateTime().minusMillis(10), new RobotAction(action.getActionRobot(), action.getValue()));
        actions.put(action.getDateTime().minusMillis(0), new RobotAction(RobotData, -1));
    }
}

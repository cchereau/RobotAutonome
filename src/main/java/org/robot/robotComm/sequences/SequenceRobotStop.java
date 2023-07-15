package org.robot.robotComm.sequences;

import org.robot.robotComm.RobotAction;

import static org.robot.constante.globalCte.enActionRobot.RobotData;
import static org.robot.constante.globalCte.enActionRobot.RobotStop;

public class SequenceRobotStop extends Sequence {


    public SequenceRobotStop(RobotAction action) {
        super(action);
        actions.put(action.getDateTime().minusMillis(2), new RobotAction(RobotStop, -1));
        actions.put(action.getDateTime().minusMillis(0), new RobotAction(RobotData, 0));
    }

}

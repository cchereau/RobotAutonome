package org.robot.robotComm.sequences;

import org.joda.time.LocalDateTime;
import org.robot.robotComm.RobotAction;

import static org.robot.constante.globalCte.enActionRobot.RobotControl;

public class SequenceRobotAvance extends Sequence {

    public SequenceRobotAvance(RobotAction action) {
        super(action);
        ////////////////////////////////////////////////////////////////////////////////////////
        // gestion du radar
        ////////////////////////////////////////////////////////////////////////////////////////
        //actions.put(LocalDateTime.now().minusMillis(5), new RobotAction(RobotData, 0));

        actions.put(LocalDateTime.now().minusMillis(4), new RobotAction(RobotControl, 0));


    }


}

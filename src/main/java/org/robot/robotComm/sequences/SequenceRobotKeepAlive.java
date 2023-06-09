package org.robot.robotComm.sequences;

import org.joda.time.LocalDateTime;
import org.robot.robotComm.RobotAction;

import static org.robot.constante.globalCte.enActionRobot.RobotControl;
import static org.robot.constante.globalCte.enActionRobot.RobotData;

public class SequenceRobotKeepAlive extends Sequence {

    public SequenceRobotKeepAlive(RobotAction action) {
        super(action);
        ////////////////////////////////////////////////////////////////////////////////////////
        // gestion du radar
        ////////////////////////////////////////////////////////////////////////////////////////
        actions.put(LocalDateTime.now().minusMillis(5), new RobotAction(RobotData, 0));

        actions.put(LocalDateTime.now().minusMillis(4), new RobotAction(RobotControl, 0));


    }


}

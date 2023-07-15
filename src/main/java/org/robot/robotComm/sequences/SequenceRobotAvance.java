package org.robot.robotComm.sequences;

import org.robot.robotComm.RobotAction;

import static org.robot.constante.globalCte.enActionRobot.RobotData;

public class SequenceRobotAvance extends Sequence {

    public SequenceRobotAvance(RobotAction action) {
        super(action);

        ////////////////////////////////////////////////////////////////////////////////////////
        // gestion de l'avancement
        ////////////////////////////////////////////////////////////////////////////////////////
        actions.put(action.getDateTime().minusMillis(10), new RobotAction(action.getActionRobot(), action.getValue()));
        actions.put(action.getDateTime().minusMillis(0), new RobotAction(RobotData, -1));

    }


}

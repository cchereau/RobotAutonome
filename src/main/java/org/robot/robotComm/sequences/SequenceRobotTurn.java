package org.robot.robotComm.sequences;

import org.robot.constante.globalCte.enActionRobot;
import org.robot.robotComm.RobotAction;

import static org.robot.constante.globalCte.enActionRobot.*;

public class SequenceRobotTurn extends Sequence {

    public SequenceRobotTurn(RobotAction action) {
        super(action);
        enActionRobot actionDirection;
        int angleRotation = action.getValue();

        if (action.getActionRobot().equals(RobotDroite)) actionDirection = RobotDroite;
        else actionDirection = RobotGauche;

        ////////////////////////////////////////////////////////////////////////////////////////
        // gestion du radar
        ////////////////////////////////////////////////////////////////////////////////////////
        // action de virage
        actions.put(action.getDateTime().minusMillis(10), new RobotAction(RobotStop, -1));
        actions.put(action.getDateTime().minusMillis(5), new RobotAction(actionDirection, angleRotation));
        actions.put(action.getDateTime().minusMillis(0), new RobotAction(RobotData, -1));
    }


}

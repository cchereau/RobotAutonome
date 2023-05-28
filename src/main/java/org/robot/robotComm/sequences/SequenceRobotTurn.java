package org.robot.robotComm.sequences;

import org.joda.time.LocalDateTime;
import org.robot.robotComm.RobotAction;

import static org.robot.constante.globalCte.enActionRobot.*;

public class SequenceRobotTurn extends Sequence {

    public SequenceRobotTurn(RobotAction action) {
        super(action);
        ////////////////////////////////////////////////////////////////////////////////////////
        // gestion du radar
        ////////////////////////////////////////////////////////////////////////////////////////
        actions.put(LocalDateTime.now().minusMillis(6), new RobotAction(RobotRadarDroite, action.getValue()));

        actions.put(LocalDateTime.now().minusMillis(5), new RobotAction(RobotData, 0));

        // action de virage
        actions.put(LocalDateTime.now().minusMillis(4), new RobotAction(RobotDroite, action.getValue()));

        // action de virage
        actions.put(LocalDateTime.now().minusMillis(3), new RobotAction(RobotStop, action.getValue()));

        // remise à zero du radar
        actions.put(LocalDateTime.now().minusMillis(2), new RobotAction(RobotRadarGauche, 0));

        // arret du virage
        actions.put(LocalDateTime.now().minusMillis(1), new RobotAction(RobotStop, 0));

    }


}

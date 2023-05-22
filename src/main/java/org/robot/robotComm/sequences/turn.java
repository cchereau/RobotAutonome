package org.robot.robotComm.sequences;
import org.robot.robotComm.Robot;
import org.robot.robotComm.RobotAction;
import java.time.LocalDateTime;

import static org.robot.constante.globalCte.enActionRobot.*;

public class turn extends sequence{

    public turn(Robot robot)
    {
        super(robot);
    }

    public void execcute(RobotAction action)
    {
        // calcul des variable nécessaire
        this.dataRobotFrom = robot.getDataRobot();
        this.dataRobotTo.setAzimut(this.dataRobotFrom.getAzimut()+action.getValue());
        this.actions.put(LocalDateTime.now(), new RobotAction(RobotStop,0));

        ////////////////////////////////////////////////////////////////////////////////////////
        // gestion du radar
        ////////////////////////////////////////////////////////////////////////////////////////
        this.actions.put(LocalDateTime.now(),new RobotAction(RadarDroite,action.getValue()));
        this.actions.put(LocalDateTime.now(), new RobotAction(DataRobot,0));
        // récupération de la dernière donnée de la distance
        int distance =  robot.getDataRobot().getAzimut();
        if(distance<=300)
            System.out.println("Turn.java - Distance trop courte");
            else
            this.actions.put(LocalDateTime.now(),new RobotAction(RadarDroite,0));

        // action de virage




    }


}

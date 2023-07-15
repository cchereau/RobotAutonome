package org.robot.robotComm.api.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.robot.constante.globalCte.enActionRobot;

public class JSONDataRobotReturnAction {
    private final Integer direction;
    private final Integer valeur;
    private final String returnString;

    public JSONDataRobotReturnAction(@JsonProperty("action") String direction,
                                     @JsonProperty("value") String valeur,
                                     @JsonProperty("status") String returnString) {
        this.direction = Integer.valueOf(direction);
        this.valeur = Integer.valueOf(valeur);
        this.returnString = returnString;
    }



    public JSONDataRobotReturnAction() {
        this.direction = -1;
        this.valeur = -1;
        this.returnString = null;
    }

    @Override
    public String toString() {
        return " ActionRobot{ " +
                "action=" + this.direction.toString() + "," +
                "value=" + this.valeur.toString() + "," +
                "status=" + returnString + "}";
    }

    public enActionRobot getAction() {
        switch (direction) {
            case 0:
                return enActionRobot.RobotAvant;
            case 1:
                return enActionRobot.RobotArriere;
            case 2:
                return enActionRobot.RobotStop;
            case 3:
                return enActionRobot.RobotDroite;
            case 4:
                return enActionRobot.RobotGauche;
            case 5:
                return enActionRobot.RobotRadarDroite;
            case 6:
                return enActionRobot.RobotRadarGauche;
            case 7:
                return enActionRobot.RobotData;
            case 8:
                return enActionRobot.RobotControl;
            case 9:
                return enActionRobot.SystemRobot;
            case 10:
                return enActionRobot.undefined;
        }
        return enActionRobot.undefined;
    }

    public int getValue() {
        switch (this.getAction()) {
            case RobotRadarDroite, RobotGauche:
                return this.valeur - 90;
            default:
                return this.valeur;
        }
    }
}

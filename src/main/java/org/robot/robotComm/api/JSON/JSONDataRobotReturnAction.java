package org.robot.robotComm.api.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONDataRobotReturnAction {
    private Integer direction;
    private Integer valeur;
    private String returnString;

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
                "status=" + returnString.toString() + "}";
    }


}

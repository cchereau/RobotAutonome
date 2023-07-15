package org.robot.robotComm;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Classe de gestion des ordres des Actions élémentaire en charge de la communication avec le Robot
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


import org.joda.time.LocalDateTime;
import org.robot.constante.globalCte.enActionRobot;

public class RobotAction {

    private LocalDateTime dateTime;
    private enActionRobot actionRobot;
    private Integer Value;
    private Boolean blnExecuted;

    public RobotAction(enActionRobot actionRobot, Integer value) {
        this.dateTime = LocalDateTime.now();
        this.actionRobot = actionRobot;
        Value = value;
        blnExecuted = false;
    }
    public enActionRobot getActionRobot() {
        return actionRobot;
    }
    public Integer getValue() {
        return Value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Boolean isExecuted() {
        return this.blnExecuted;
    }

    public void isExecuted(Boolean _blnExecuted) {
        this.blnExecuted = _blnExecuted;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof RobotAction) &&
                (((RobotAction) o).actionRobot.equals(this.actionRobot))
                && (((RobotAction) o).Value == this.Value);
    }
}




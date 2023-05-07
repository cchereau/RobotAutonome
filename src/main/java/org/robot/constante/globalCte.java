package org.robot.constante;

import java.util.Properties;

public final class globalCte {

    public static String propertiesFileName = "myRobot.properties";
    public static Properties properties = new Properties();
    public static final String properties_GoogleKeyAPI = "API_keyGoogleMap";
    public static final String properties_URL_GETDATAROBOT= "URL_GET_DATA";
    public static final String properties_URL_SETDACTIONROBOT= "URL_SET_ACTIONS";

    public enum enActionRobot {
        RobotAvant(0, "RobotAvance", 2),
        RobotArriere(1, "RobotArriere", 2),
        RobotStop(2, "RobotStop", 0),
        RobotDroite(3, "RobotDroite", 2),
        RobotGauche(4, "RobotGauche", 2),
        RadarDroite(5, "RadarDroite", 1),
        RadarGauche(6, "RadarGauche", 1),
        DataRobot(7, "DataRobotAll", 3),
        undefined(7, "Unedefined", 4);
        Integer value;
        String label;
        Integer priority;

        private enActionRobot(Integer value, String label, Integer priority) {
            this.value = value;
            this.label = label;
            this.priority = priority;
        }

        public String getLabel() {
            return this.label;
        }
        public Integer getPriority() {
            return this.priority;
        }

    }

}

package org.robot.robotComm.controles;

public class Controle {
    private final int VALEUR_DISTANCE_SECU = 500;


    public Controle() {

    }

    public boolean isValidated(int valeur) {
        return valeur >= VALEUR_DISTANCE_SECU;
    }

}

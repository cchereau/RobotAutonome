//https://github.com/mjg123/java-http-clients/blob/master/src/main/java/com/twilio/APOD.java

package org.robot.robotComm.api.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class JSONDataRobot {

    private final LocalDateTime dateTime;
    //public final String time;
    private final Double longitude;
    private final Double latitude;
    private final Integer azimut;
    private final Float vitesse;
    private final Float vChenilleDroite;
    private final Float vChenilleGauche;
    private final Float temperature;
    private final Float humidite;
    private final Integer angle;
    private final Float distance;


    public JSONDataRobot(@JsonProperty("date") String date,
                         @JsonProperty("time") String time,
                         @JsonProperty("longitude") String longitude,
                         @JsonProperty("latitude") String latitude,
                         @JsonProperty("azimut") String azimut,
                         @JsonProperty("vitesse") String vitesse,
                         @JsonProperty("vChenilleDroite") String vChenilleDroite,
                         @JsonProperty("vChenilleGauche") String vChenilleGauche,
                         @JsonProperty("temperature") String temperature,
                         @JsonProperty("humidite") String humidite,
                         @JsonProperty("angle") String angle,
                         @JsonProperty("distance") String distance
    ) throws Exception {
        dateTime = getDateTime(date, time);
        this.longitude = Double.valueOf(longitude);
        this.latitude = Double.valueOf(latitude);
        this.azimut = Integer.valueOf(azimut);
        this.vitesse = Float.valueOf(vitesse);
        this.vChenilleGauche = Float.valueOf(vChenilleGauche);
        this.vChenilleDroite = Float.valueOf(vChenilleDroite);
        this.temperature = Float.valueOf(temperature);
        this.humidite = Float.valueOf(humidite);
        this.angle = Integer.valueOf(angle);
        this.distance = Float.valueOf(distance);
    }

    public JSONDataRobot() {
        dateTime = LocalDateTime.now();
        this.longitude = Double.valueOf(0d);
        this.latitude = Double.valueOf(0d);
        this.azimut = Integer.valueOf(0);
        this.vitesse = Float.valueOf(0f);
        this.vChenilleGauche = Float.valueOf(0f);
        this.vChenilleDroite = Float.valueOf(0f);
        this.temperature = Float.valueOf(0f);
        this.humidite = Float.valueOf(0f);
        this.angle = Integer.valueOf(0);
        this.distance = Float.valueOf(0f);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Integer getAzimut() {
        return azimut;
    }

    public Float getVitesse() {
        return vitesse;
    }

    public Float getvChenilleDroite() {
        return vChenilleDroite;
    }

    public Float getvChenilleGauche() {
        return vChenilleGauche;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Float getHumidite() {
        return humidite;
    }

    public Integer getAngle() {
        return angle;
    }

    public Float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return " DataRobot{ " +
                "datetime=" + dateTime.toString() + "," +
                "longitude=" + longitude.toString() + "," +
                "latitude=" + latitude.toString() + "," +
                "azimut=" + azimut.toString() + "," +
                "vitesse=" + vitesse.toString() + "," +
                "vChenilleDroite=" + vChenilleDroite.toString() + "," +
                "vChenilleGauche=" + vChenilleGauche.toString() + "," +
                "temperature=" + temperature.toString() + "," +
                "humidite=" + humidite.toString() + "," +
                "angle=" + angle.toString() + "," +
                "distance=" + distance.toString() +
                "}";

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // récupération des chaines initiales en entrée
    //this.date  = 280323
    // this.time = 9415000
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private LocalDateTime getDateTime(String _date, String _time) throws StringIndexOutOfBoundsException {
        // Mise au format de la date
        String date = "0" + _date;
        date = date.substring(date.length() - 6);
        // mise au format de l'heure
        String time = _time.substring(0, _time.length() - 2);
        time = String.format("%06d", Integer.valueOf(time));
        // mise eu format de la date
        String strDateTime = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyy HHmmss");
        String dateInString = date + " " + time;
        return DateTime.parse(dateInString, formatter).toLocalDateTime();
    }


}

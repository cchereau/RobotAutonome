package org.robot.robotComm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.robot.constante.globalCte.enActionRobot;
import org.robot.robotComm.api.JSON.JSONDataRobot;
import org.robot.robotComm.api.JSON.JSONDataRobotReturnAction;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.robot.constante.globalCte.*;

public class RobotAPI {


    public RobotAPI() {
    }


    //    public RobotAPIDataReturnStatus getDataRobot() throws Exception {
    public JSONDataRobot getDataRobot() throws IOException{
        String strMessage = new String();

            // Create a neat value object to hold the URL
            URL url = new URL(properties.getProperty(properties_URL_GETDATAROBOT));
            // Open a connection(?) on the URL(??) and cast the response(???)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Now it's "open", we can set the request method, headers etc.
            connection.setRequestProperty("accept", "application/json");

            // This line makes the request
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseStream, JSONDataRobot.class);

            /*byte[] array = new byte[responseStream.available()];
            responseStream.read(array);
            strMessage = new String(array);

            // Manually converting the response body InputStream to APOD using Jackson
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(strMessage, JSONReturnStatus.class);
            */
    }


    public JSONDataRobotReturnAction setActionRobot(enActionRobot actionRobot, Integer valeur) throws IOException {


        Map<String, Object> params = new LinkedHashMap<>();
        params.put("action", actionRobot.getLabel());
        params.put("value", valeur.toString());

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
        }
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        URL url = new URL(properties.getProperty(properties_URL_SETDACTIONROBOT));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connection.setDoOutput(true);
        connection.getOutputStream().write(postDataBytes);

        // Manually converting the response body InputStream to APOD using Jackson
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream responseStream = connection.getInputStream();
            return mapper.readValue(responseStream, JSONDataRobotReturnAction.class);
        } catch (ValueInstantiationException e) {
            System.out.println(this.getClass().getCanonicalName() + "-" + e.getMessage());
            return new JSONDataRobotReturnAction();
        }
    }


}

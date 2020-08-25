package ru.instagram;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class HttpClientSync {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String []args) {
        try {
            System.out.println("SMS is: " + getSMSCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getSMSCode() throws IOException, InterruptedException {
        String AccessToken = "***";
        String URL = "https://api.pushbullet.com/v2/permanents/DEVICEIDEN_threads";
        String CONFIRMCODE = "";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .setHeader("Access-Token", AccessToken)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(response.body());
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray msg = (JSONArray) jsonObject.get("threads");

            JSONObject body = (JSONObject) msg.get(0);
            Object data = body.get("latest");

            JSONObject code = (JSONObject) data;
            String sms = (String) code.get("body");
            sms = sms.substring(0, 7);
            sms = sms.replaceAll(" ", "");

            CONFIRMCODE = sms;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return CONFIRMCODE;
    }
}

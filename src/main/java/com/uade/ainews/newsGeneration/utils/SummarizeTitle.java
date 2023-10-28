package com.uade.ainews.newsGeneration.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SummarizeTitle {

    // Call Python service to sum up
    public static String sumUp(String message, Integer maxTextExtension, Integer minTextExtension) {
        String summary = "";
        try {
            String restUrl = "http://localhost:8081/api/summarize/title";
            String response = sendTextViaRest(message, maxTextExtension, minTextExtension, restUrl);
            summary = decodeUnicode(response);

        } catch (Exception e) {
            System.err.println("Error al enviar el texto: " + e.getMessage());
            summary = "?";
        }
        return summary;
    }


    public static String sendTextViaRest(String text, Integer maxTextExtension, Integer minTextExtension, String restUrl) throws IOException {
        // Build URL with textExtension parameter
        String urlWithParams = restUrl + "?maxTextExtension=" + maxTextExtension + "&minTextExtension=" + minTextExtension;
        URL url = new URL(urlWithParams);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "text/plain; charset=" + StandardCharsets.UTF_8);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = text.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Error en la respuesta del servidor: " + responseCode);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            return response.toString().substring(1, response.length() - 1); //delete extra ""
        }
    }

    public static String decodeUnicode(String input) {
        Properties properties = new Properties();
        properties.put("input", input);
        return properties.getProperty("input");
    }


}


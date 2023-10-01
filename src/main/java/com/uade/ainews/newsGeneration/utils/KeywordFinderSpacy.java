package com.uade.ainews.newsGeneration.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KeywordFinderSpacy {

    public static List<String> getKeyWords(String message) throws Exception {
        String summary = "";
        try {
            String restUrl = "http://localhost:8081/api/keywords";
            return parseResponse(message, restUrl);

        } catch (Exception e) {
            throw new Exception("Error getting keywords: " + message.substring(0, 50), e);
        }
    }

    public static String decodeUnicode(String input) {
        Properties properties = new Properties();
        properties.put("input", input);
        return properties.getProperty("input");
    }

    private static String extractResponse(String jsonResponse) {
        String summaryText = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            if (jsonNode.isArray() && jsonNode.size() > 0) {
                JsonNode summaryNode = jsonNode.get(0).get("summary_text");
                if (summaryNode != null) {
                    summaryText = summaryNode.asText();
                    System.out.println("Texto resumido: " + summaryText);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al parsear JSON: " + e.getMessage());
        }
        return summaryText;
    }
    public static List<String> parseResponse(String text, String restUrl) throws IOException {

        // Construir la URL con el parámetro textExtension
        String urlWithParams = restUrl;
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
            Gson gson = new Gson();

            String generatedText = response.toString();
            List<String> elementsAsList = gson.fromJson(generatedText, new TypeToken<List<String>>(){}.getType());
            StringBuilder result = new StringBuilder();
            for (String palabra : elementsAsList) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(palabra);
            }
            String resultadoFinal = result.toString();
            List<String> cleanResponse = standardizeResponse(resultadoFinal);
            System.out.println("Generated Text RAW: " + generatedText);
            if (cleanResponse.size() > 5) {
                List<String> first5Elements = cleanResponse.subList(0, 5);
                cleanResponse = first5Elements;
            }
            System.out.println("Generated Text SHORT: " + cleanResponse);
            connection.disconnect();
            return cleanResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public static List<String> standardizeResponse(String responseRaw) {
        String responseWithoutSpaces = responseRaw.trim();
        String commaGranted = responseWithoutSpaces.replace(" ", ",");
        String removeDots = commaGranted.replace(".", "");
        String removeEnters = removeDots.replace("\n\n", ",");
        String removeDobleComma = removeEnters.replace(",,", ",");
        String[] elements = removeDobleComma.split(",");
        List<String> result = new ArrayList<>();

        for (String element : elements) {
            String upperCaseElement = element.toUpperCase();

            String replaceLetterA = upperCaseElement.replace("Á", "A");
            String replaceLetterE = replaceLetterA.replace("É", "E");
            String replaceLetterI = replaceLetterE.replace("Í", "I");
            String replaceLetterO = replaceLetterI.replace("Ó", "O");
            String replaceLetterU = replaceLetterO.replace("Ú", "U");

            String trimmedElement = replaceLetterU.trim();
            result.add(trimmedElement);
        }
        //REMOVING PREOPSICIONES
        result.remove("DE");
        result.remove("DEL");
        result.remove("LA");
        result.remove("LAS");
        result.remove("LO");
        result.remove("LOS");
        result.remove("");
        result.remove("|");
        return result;
    }


}
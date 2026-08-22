package com.sahyog.msappreg;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiTest {

    public static void main(String[] args) throws Exception {
        testInitialize();
        Thread.sleep(1000);
        testNext();
        Thread.sleep(1000);
        testInitializeWithNumber();
        Thread.sleep(1000);
        testNext2();
    }

    static void testInitialize() throws Exception {
        String json = "{\"applicationNum\":\"\"}";
        System.out.println("=== TEST 1: Initialize (non-existent) ===");
        System.out.println("Request: " + json);
        String response = sendRequest("http://localhost:8090/ms-application-registration/api/v1/register-application/initialize", json);
        System.out.println("Response: " + response);
        System.out.println();
    }

    static void testNext() throws Exception {
        String json = "{\"applicationNum\":\"\",\"pageId\":\"AR001\",\"firstName\":\"Ameya\",\"middleName\":\"ww\",\"lastName\":\"Dikshit\",\"mobileNumber\":\"1234567876\",\"emailAddress\":\"amydikshit@gmail.com\",\"applicationDate\":\"2026-07-25\"}";
        System.out.println("=== TEST 2: Next (generate A9000000) ===");
        System.out.println("Request: " + json);
        String response = sendRequest("http://localhost:8090/ms-application-registration/api/v1/register-application/next", json);
        System.out.println("Response: " + response);
        System.out.println();
    }

    static void testInitializeWithNumber() throws Exception {
        String json = "{\"applicationNum\":\"A9000000\"}";
        System.out.println("=== TEST 3: Initialize (with A9000000) ===");
        System.out.println("Request: " + json);
        String response = sendRequest("http://localhost:8090/ms-application-registration/api/v1/register-application/initialize", json);
        System.out.println("Response: " + response);
        System.out.println();
    }

    static void testNext2() throws Exception {
        String json = "{\"applicationNum\":\"\",\"pageId\":\"AR002\",\"firstName\":\"John\",\"middleName\":\"M\",\"lastName\":\"Smith\",\"mobileNumber\":\"9876543210\",\"emailAddress\":\"john@example.com\",\"applicationDate\":\"2026-07-25\"}";
        System.out.println("=== TEST 4: Next (generate A9000001) ===");
        System.out.println("Request: " + json);
        String response = sendRequest("http://localhost:8090/ms-application-registration/api/v1/register-application/next", json);
        System.out.println("Response: " + response);
        System.out.println();
    }

    static String sendRequest(String urlStr, String json) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int status = conn.getResponseCode();
        StringBuilder response = new StringBuilder();
        
        java.io.BufferedReader br = new java.io.BufferedReader(
            new java.io.InputStreamReader(
                status >= 400 ? conn.getErrorStream() : conn.getInputStream(), 
                StandardCharsets.UTF_8
            )
        );
        
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        return response.toString();
    }
}

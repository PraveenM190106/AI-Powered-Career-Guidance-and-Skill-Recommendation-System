package ai;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GeminiService {

    private static final String API_KEY =
            "*************";

    public String askAI(String prompt) {
        try {
            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

            JSONObject textPart = new JSONObject();
            textPart.put("text", prompt);

            JSONArray parts = new JSONArray();
            parts.put(textPart);

            JSONObject content = new JSONObject();
            content.put("parts", parts);

            JSONArray contents = new JSONArray();
            contents.put(content);

            JSONObject requestBody = new JSONObject();
            requestBody.put("contents", contents);

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .header("X-goog-api-key", API_KEY)
                            .timeout(Duration.ofSeconds(30))
                            .POST(
                                    HttpRequest.BodyPublishers.ofString(
                                            requestBody.toString()
                                    )
                            )
                            .build();

            HttpClient client =
                    HttpClient.newBuilder()
                            .connectTimeout(Duration.ofSeconds(10))
                            .build();

            int maxRetries = 3;
            for(int i = 0; i < maxRetries; i++) {
                HttpResponse<String> response =
                        client.send(
                                request,
                                HttpResponse.BodyHandlers.ofString()
                        );

                int statusCode = response.statusCode();

                if(statusCode == 200) {
                    JSONObject jsonResponse =
                            new JSONObject(response.body());

                    return jsonResponse
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text");
                }

                if(statusCode == 429) {
                    System.out.println("\n[AI Assistant] Rate limit exceeded (429). Retrying in 5 seconds... (Attempt " + (i + 1) + " of " + maxRetries + ")");
                    Thread.sleep(5000);
                    continue;
                }

                if(statusCode == 503) {
                    System.out.println("\n[AI Assistant] Service temporarily unavailable (503). Retrying in 5 seconds... (Attempt " + (i + 1) + " of " + maxRetries + ")");
                    Thread.sleep(5000);
                    continue;
                }

                if(statusCode == 500) {
                    System.out.println("\n[AI Assistant] Internal server error (500). Retrying in 3 seconds... (Attempt " + (i + 1) + " of " + maxRetries + ")");
                    Thread.sleep(3000);
                    continue;
                }

                try {
                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.has("error")) {
                        return "Gemini API Error (" + statusCode + "): " + jsonResponse.getJSONObject("error").getString("message");
                    }
                } catch (Exception ignored) {}

                return "Gemini API Error: HTTP Status Code " + statusCode + "\nResponse: " + response.body();
            }

            return "Gemini Server Busy. Try Again Later.";

        }
        catch (Exception e) {
            return "Error : " + e.getMessage();
        }
    }
}

package org.example.Examen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DefinitionWordRAE {

    public static String getDefinition(String word) {
        try {
            String urlRAE = "https://dle.rae.es/";
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlRAE + word))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return getFirstDefinition(response, word);
            }

            if (response.statusCode() == 301) {
                urlRAE = urlRAE.substring(0, urlRAE.length() - 1);
                String newUrl = urlRAE + response.headers().firstValue("Location").orElse(null);
                request = HttpRequest.newBuilder()
                        .uri(URI.create(newUrl))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());

                return getFirstDefinition(response, word);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error processing the request: " + e.getMessage());
        }
        return null;
    }

    private static String getFirstDefinition(HttpResponse<String> response, String word) {
        Document document = Jsoup.parse(response.body());
        Elements definitionElements = document.select("div .j");
        if (!definitionElements.isEmpty()) {
            String definition = definitionElements.get(0).text();
            System.out.println(word + ": " + definition);
            return response.body();
        } else {
            System.out.println("Definition not found the word: " + word);
        }
        return null;
    }

    public static void saveHTMLToFile(String htmlContent, String filename) throws IOException {
        File file = new File(filename + ".html");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(htmlContent);
        writer.close();
        System.out.println("[ HTML content saved to " + file.getAbsolutePath() + " ]");
    }
}

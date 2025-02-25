package com.rhcsoft.spring.drools.function;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.BlobTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.StorageAccount;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class DroolsBlobTrigger {
    /**
     * This function will be invoked when a new or updated blob is detected at the
     * specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("BlobTrigger")
    @StorageAccount("AzureWebJobsStorage")
    public void run(
            @BlobTrigger(name = "content", path = "drools-workitems/{name}", dataType = "binary") byte[] content,
            @BindingName("name") String name,
            final ExecutionContext context) {

        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: "
                + content.length + " Bytes");

        try {
            String uriString = System.getenv("CALCULATOR_URL");
            if (uriString == null || uriString.isEmpty()) {
                throw new IllegalArgumentException("Environment variable CALCULATOR_URL is not set or is empty");
            }

            URI uri = new URI(uriString);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(content))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            context.getLogger().info("Response Code: " + response.statusCode());
            context.getLogger().info("Response Content: " + response.body());
        } catch (Exception e) {
            context.getLogger().severe("Error sending blob content: " + e.getMessage());
        }

    }
}

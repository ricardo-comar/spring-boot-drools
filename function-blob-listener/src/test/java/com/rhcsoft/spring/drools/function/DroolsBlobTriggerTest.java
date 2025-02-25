package com.rhcsoft.spring.drools.function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearEnvironmentVariable;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.microsoft.azure.functions.ExecutionContext;

public class DroolsBlobTriggerTest {

    @Mock
    private ExecutionContext context;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<Object> httpResponse;

    private DroolsBlobTrigger droolsBlobTrigger;

    byte[] content = "test content".getBytes();
    String name = "testBlob";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        droolsBlobTrigger = new DroolsBlobTrigger();

        when(context.getLogger()).thenReturn(mock(java.util.logging.Logger.class));
    }

    @Test
    @SetEnvironmentVariable(key = "CALCULATOR_URL", value = "http://localhost:8080/calculate")
    public void testRun() throws Exception {

        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("Success");

        try (MockedStatic<HttpClient> mocked = mockStatic(HttpClient.class)) {
            mocked.when(HttpClient::newHttpClient).thenReturn(httpClient);
            droolsBlobTrigger.run(content, name, context);
        }

        verify(context.getLogger()).info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: "
                + content.length + " Bytes");
        verify(context.getLogger()).info("Response Code: 200");
        verify(context.getLogger()).info("Response Content: Success");
    }

    @Test
    @SetEnvironmentVariable(key = "CALCULATOR_URL", value = "http://localhost:8080/calculate")
    public void testRunWithException() throws Exception {

        when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Test Exception"));

        try (MockedStatic<HttpClient> mocked = mockStatic(HttpClient.class)) {
            mocked.when(HttpClient::newHttpClient).thenReturn(httpClient);
            droolsBlobTrigger.run(content, name, context);
        }

        verify(context.getLogger()).info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: "
                + content.length + " Bytes");
        verify(context.getLogger()).severe("Error sending blob content: Test Exception");
    }

    @Test
    @ClearEnvironmentVariable(key = "CALCULATOR_URL")
    public void testRunWithoutUrlProperty() throws Exception {
        when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Test Exception"));

        try (MockedStatic<HttpClient> mocked = mockStatic(HttpClient.class)) {
            mocked.when(HttpClient::newHttpClient).thenReturn(httpClient);
            droolsBlobTrigger.run(content, name, context);
        }

        verify(context.getLogger())
                .severe("Error sending blob content: Environment variable CALCULATOR_URL is not set or is empty");
    }
}
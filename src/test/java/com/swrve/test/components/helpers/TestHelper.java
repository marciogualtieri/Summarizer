package com.swrve.test.components.helpers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.swrve.test.constants.TestConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jbehave.core.model.ExamplesTable;
import org.mortbay.jetty.Response;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Component
public class TestHelper {

    private WireMockServer wireMockServer = null;

    public void startMockServer() {
        wireMockServer = new WireMockServer(wireMockConfig().httpsPort(TestConstants.PORT_NUMBER));
        wireMockServer.start();
    }

    public void stopMockServer() {
        if (wireMockServer != null) {
            WireMock.reset();
            wireMockServer.stop();
        }
    }

    public void createWiremockStubWithOkResponseAndBodyFromString(String body) throws IOException {
        stubFor(get(urlPathEqualTo(TestConstants.RESOURCE))
                .willReturn(aResponse().withStatus(Response.SC_OK)
                        .withHeader(TestConstants.CONTENT_TYPE_HEADER, TestConstants.CONTENT_TYPE_JAVASCRIPT)
                        .withHeader(TestConstants.CONTENT_ENCODING_HEADER, TestConstants.CONTENT_ENCODING_GZIP)
                        .withBody(gzipInputString(body))));
    }

    public void createWiremockStubWithOkResponseAndBodyFromFile(String fileNameAndPath) throws IOException {
        stubFor(get(urlPathEqualTo(TestConstants.RESOURCE))
                .willReturn(aResponse().withStatus(Response.SC_OK)
                        .withHeader(TestConstants.CONTENT_TYPE_HEADER, TestConstants.CONTENT_TYPE_JAVASCRIPT)
                        .withHeader(TestConstants.CONTENT_ENCODING_HEADER, TestConstants.CONTENT_ENCODING_GZIP)
                        .withBody(readFileToByteArray(fileNameAndPath))));
    }

    public String extractStringFromExamplesTable(ExamplesTable examplesTable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map<String, String> row : examplesTable.getRows()) {
            String value = row.get(examplesTable.getHeaders().get(0));
            stringBuilder.append(value);
            stringBuilder.append(System.lineSeparator());
        }
        String value = stringBuilder.toString();
        return value.substring(0, value.length() - 1);
    }

    public List<String> extractStringListFromExamplesTable(ExamplesTable examplesTable) {
        List<String> stringList = new ArrayList<>();
        for (Map<String, String> row : examplesTable.getRows()) {
            String value = row.get(examplesTable.getHeaders().get(0));
            stringList.add(value);
        }
        return stringList;
    }

    public String readFileToString(String fileNameAndPath)
            throws IOException {
        return FileUtils.readFileToString(new File(fileNameAndPath));
    }

    private byte[] readFileToByteArray(String fileNameAndPath)
            throws IOException {
        return FileUtils.readFileToByteArray(new File(fileNameAndPath));
    }

    public static byte[] gzipInputString(String input) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(input.length());
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(input.getBytes());
        gzipOutputStream.close();
        byte[] compressedInput = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return compressedInput;
    }

}

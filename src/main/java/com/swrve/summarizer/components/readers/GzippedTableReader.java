package com.swrve.summarizer.components.readers;


import com.swrve.summarizer.constants.AppConstants;
import com.swrve.summarizer.messages.AppMessages;
import com.swrve.summarizer.ssl.TrustAllHostNameVerifier;
import com.swrve.summarizer.ssl.TrustAllTrustManager;
import org.apache.commons.io.IOUtils;
import org.relique.io.TableReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Component
public class GzippedTableReader implements TableReader {
    private static final Logger logger = LoggerFactory.getLogger(GzippedTableReader.class);

    private InputStreamReader inputStreamReader;

    @PreDestroy
    public void closeInputStreamReader() {
        IOUtils.closeQuietly(inputStreamReader);
    }

    @Override
    public Reader getReader(Statement statement, String url) throws SQLException {
        logger.debug(AppMessages.GETTING_TABLE_READER_MESSAGE_FORMAT, url);
        if (url.startsWith(AppConstants.HTTPS)) {
            return getHttpsReader(url);
        }
        return getHttpReader(url);
    }

    @Override
    public List<String> getTableNames(Connection connection) {
        return null;
    }

    private InputStreamReader getHttpReader(String url) throws SQLException {
        try {
            HttpURLConnection connection =
                    (HttpsURLConnection) (new URL(url)).openConnection();
            inputStreamReader = getInputStreamReader(connection);
            return inputStreamReader;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private InputStreamReader getHttpsReader(String url) throws SQLException {
        try {
            configureHttpsURLConnectionToTrustAll();
            HttpsURLConnection connection =
                    (HttpsURLConnection) (new URL(url)).openConnection();
            inputStreamReader = getInputStreamReader(connection);
            return inputStreamReader;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private InputStreamReader getInputStreamReader(URLConnection urlConnection)
            throws IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(urlConnection.getInputStream());
        return new InputStreamReader(gzipInputStream);
    }

    private void configureHttpsURLConnectionToTrustAll() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new TrustAllTrustManager()}, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new TrustAllHostNameVerifier());
    }

}

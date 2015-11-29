package com.swrve.summarizer.components.clients;

import com.swrve.summarizer.constants.AppConstants;
import com.swrve.summarizer.messages.AppMessages;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Properties;

@Component
public class UserInfoClient {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoClient.class);

    private Connection connection = null;
    private Statement statement = null;
    private String url;

    public void open(String url) throws SQLException {
        logger.debug(AppMessages.OPENING_USER_INFO_CLIENT_MESSAGE_FORMAT, url);
        Properties properties = new Properties();
        properties.put(AppConstants.COLUMN_TYPES_PROPERTY, AppConstants.COLUMN_TYPES_VALUE);
        connection = DriverManager.getConnection(AppConstants.CSV_JDBC_URL_STRING, properties);
        statement = connection.createStatement();
        this.url = url;
    }

    public void close() {
        logger.debug(AppMessages.CLOSING_USER_INFO_CLIENT_MESSAGE);
        DbUtils.closeQuietly(connection);
        connection = null;
        statement = null;
    }

    public String getMinDateJoined() throws SQLException {
        String sql = String.format(AppConstants.MIN_DATE_JOINED_SQL_QUERY, url);
        return executeQuery(sql, AppConstants.DATE_JOINED_COLUMN);
    }

    public String getFirstUserToJoin() throws SQLException {
        String minDateJoined = getMinDateJoined();
        if (minDateJoined != null) {
            String sql = String.format(AppConstants.USER_ID_WHERE_DATE_JOINED_SQL_QUERY, url, minDateJoined);
            return executeQuery(sql, AppConstants.USER_ID_COLUMN_NAME);
        }
        return null;
    }

    public Integer getCountForResolution(int width, int height) throws SQLException {
        String sql = String.format(AppConstants.COUNT_WITH_WIDTH_AND_HEIGHT_SQL_QUERY, url, width, height);
        String result = executeQuery(sql, AppConstants.COUNT_RESULT_COLUMN_LABEL);
        return result == null ? null : Integer.parseInt(result);
    }

    public Integer getTotalSpent() throws SQLException {
        String sql = String.format(AppConstants.SUM_SPENT_SQL_QUERY, url);
        String result = executeQuery(sql, AppConstants.SUM_RESULT_COLUMN_LABEL);
        return result == null ? null : Integer.parseInt(result);
    }

    public Integer getUserCount() throws SQLException {
        String sql = String.format(AppConstants.COUNT_USES_SQL_QUERY, url);
        String result = executeQuery(sql, AppConstants.COUNT_RESULT_COLUMN_LABEL);
        return result == null ? null : Integer.parseInt(result);
    }

    private String executeQuery(String sql, String column) throws SQLException {
        logger.debug(AppMessages.EXECUTING_SQL_QUERY_MESSAGE_FORMAT, sql, column);
        if (!isClosed()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString(column);
            }
        }
        return null;
    }

    private boolean isClosed() throws SQLException {
        return connection == null || connection.isClosed();
    }

}

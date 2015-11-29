package com.swrve.summarizer.constants;

import com.swrve.summarizer.components.readers.GzippedTableReader;

public class AppConstants {

    public final static String CSV_JDBC_URL_STRING = String.format("jdbc:relique:csv:class:%s",
            GzippedTableReader.class.getName());
    public static final String COLUMN_TYPES_PROPERTY = "columnTypes";
    public static final String COLUMN_TYPES_VALUE = "String,String,Int,Int,Int,Int";

    public static final String DATE_JOINED_COLUMN = "date_joined";
    public static final String USER_ID_COLUMN_NAME = "user_id";
    public static final String SUM_RESULT_COLUMN_LABEL = "sum_result";
    public static final String COUNT_RESULT_COLUMN_LABEL = "count_result";

    public static final String MIN_DATE_JOINED_SQL_QUERY =
            "SELECT MIN(date_joined) AS " + DATE_JOINED_COLUMN + " FROM %s";
    public static final String USER_ID_WHERE_DATE_JOINED_SQL_QUERY =
            "SELECT user_id FROM %s WHERE date_joined = '%s'";
    public static final String SUM_SPENT_SQL_QUERY = "SELECT SUM(spend) AS " + SUM_RESULT_COLUMN_LABEL + " FROM %s";
    public static final String COUNT_WITH_WIDTH_AND_HEIGHT_SQL_QUERY =
            "SELECT COUNT(*) AS " + COUNT_RESULT_COLUMN_LABEL +
                    " FROM %s WHERE device_width = %d AND device_height = %d";
    public static final String COUNT_USES_SQL_QUERY =
            "SELECT COUNT(user_id) AS " + COUNT_RESULT_COLUMN_LABEL + " FROM %s";
    public static final String HTTPS = "https";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private AppConstants() {
    }
}

package com.swrve.test.constants;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class TestConstants {
    public static final String APPLICATION_CONTEXT = "classpath:spring/applicationContext.xml";
    public static final String HOST_NAME = "localhost";
    public static final int PORT_NUMBER = 18080;
    public static final String UNCOMPRESSED_FILE_NAME = "data.csv";
    public static final String FILE_NAME = String.format("%s.gz", UNCOMPRESSED_FILE_NAME);
    public static final String FILE_NAME_AND_PATH = String.format("target/%s", FILE_NAME);
    public static final String UNCOMPRESSED_FILE_NAME_AND_PATH =
            String.format("src/test/resources/%s", UNCOMPRESSED_FILE_NAME);
    public static final String RESOURCE = String.format("/test/%s", FILE_NAME);
    private static final String URL_FORMAT = "https://%s:%s%s";
    public static final String URL =
            String.format(URL_FORMAT, HOST_NAME, PORT_NUMBER, RESOURCE);
    public static final String NON_EXISTENT_FILE_NAME = "i.do.not.exist";
    private static final String NON_EXISTENT_RESOURCE = String.format("/test/%s", NON_EXISTENT_FILE_NAME);
    public static final String INVALID_URL =
            String.format(URL_FORMAT, HOST_NAME, PORT_NUMBER, NON_EXISTENT_RESOURCE);
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    public static final String CONTENT_TYPE_JAVASCRIPT = "application/javascript";
    public static final String CONTENT_ENCODING_GZIP = "gzip";

    public static final int USER_COUNT = 4;
    public static final String MIN_DATE_STRING = "2015-11-21T16:53:49+00:00";
    public static final String FIRST_USER_TO_JOIN = "first_user_to_join";
    public static final int RESOLUTION_WIDTH = 640;
    public static final int RESOLUTION_HEIGHT = 960;
    public static final int RESOLUTION_COUNT = 3;
    public static final int TOTAL_SPENT = 10;

    public static final List<String> REPORT = ImmutableList.of(
            Integer.toString(USER_COUNT),
            Integer.toString(RESOLUTION_COUNT),
            Integer.toString(TOTAL_SPENT),
            FIRST_USER_TO_JOIN);

    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
    public static final String STORY_FILES = "**/*.story";

    private TestConstants() {
    }
}

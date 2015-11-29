package com.swrve.test.messages;

import com.swrve.test.constants.TestConstants;

public class TestMessages {

    public static final String FILE_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "java.io.FileNotFoundException: %s";
    public static final String FILE_NOT_FOUND_EXCEPTION_MESSAGE =
            String.format(FILE_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, TestConstants.INVALID_URL);
}

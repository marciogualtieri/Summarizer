package com.swrve.summarizer.components.readers;

import com.swrve.test.components.helpers.TestHelper;
import com.swrve.test.constants.TestConstants;
import com.swrve.test.messages.TestMessages;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.io.Reader;
import java.sql.SQLException;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = TestConstants.APPLICATION_CONTEXT)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class GzippedTableReaderTest {

    @Autowired
    private GzippedTableReader gzippedTableReader;
    @Autowired
    private TestHelper testHelper;

    @Before
    public void before() throws Exception {
        testHelper.startMockServer();
        testHelper.createWiremockStubWithOkResponseAndBodyFromFile(TestConstants.FILE_NAME_AND_PATH);
    }

    @After
    public void after() throws Exception {
        testHelper.stopMockServer();
    }

    @Test
    public void whenGetReader_thenOk() throws Exception {
        Reader reader = gzippedTableReader.getReader(null, TestConstants.URL);
        String content = IOUtils.toString(reader);
        String expectedContent = testHelper.readFileToString(TestConstants.UNCOMPRESSED_FILE_NAME_AND_PATH);
        assertThat(content, equalTo(expectedContent));
    }

    @Test
    public void whenGetReaderAndFileDoesNotExist_thenException() throws Exception {
        when(gzippedTableReader).getReader(null, TestConstants.INVALID_URL);
        then(caughtException())
                .isInstanceOf(SQLException.class)
                .hasMessageContaining(TestMessages.FILE_NOT_FOUND_EXCEPTION_MESSAGE);
    }
} 

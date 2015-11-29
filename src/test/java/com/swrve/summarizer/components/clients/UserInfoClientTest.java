package com.swrve.summarizer.components.clients;

import com.swrve.test.components.helpers.TestHelper;
import com.swrve.test.constants.TestConstants;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = TestConstants.APPLICATION_CONTEXT)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class UserInfoClientTest {

    @Autowired
    private UserInfoClient userInfoClient;
    @Autowired
    private TestHelper testHelper;

    @Before
    public void before() throws Exception {
        testHelper.startMockServer();
        testHelper.createWiremockStubWithOkResponseAndBodyFromFile(TestConstants.FILE_NAME_AND_PATH);
        userInfoClient.open(TestConstants.URL);
    }

    @After
    public void after() throws Exception {
        userInfoClient.close();
        testHelper.stopMockServer();
    }

    @Test
    public void whenGetUserCount_thenOk() throws Exception {
        Integer count = userInfoClient.getUserCount();
        assertThat(count, equalTo(TestConstants.USER_COUNT));
    }

    @Test
    public void whenGetMinDateJoined_thenOk() throws Exception {
        String date = userInfoClient.getMinDateJoined();
        assertThat(date, equalTo(TestConstants.MIN_DATE_STRING));
    }

    @Test
    public void whenGetFirstUserToJoin_thenOk() throws Exception {
        String user = userInfoClient.getFirstUserToJoin();
        assertThat(user, equalTo(TestConstants.FIRST_USER_TO_JOIN));
    }

    @Test
    public void whenGetCountForResolution_thenOk() throws Exception {
        int count = userInfoClient.getCountForResolution(TestConstants.RESOLUTION_WIDTH,
                TestConstants.RESOLUTION_HEIGHT);
        assertThat(count, equalTo(TestConstants.RESOLUTION_COUNT));
    }

    @Test
    public void whenGetTotalSpent_thenOk() throws Exception {
        int totalSpent = userInfoClient.getTotalSpent();
        assertThat(totalSpent, equalTo(TestConstants.TOTAL_SPENT));
    }

} 

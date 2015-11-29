package com.swrve.summarizer.components.generators;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.swrve.test.constants.TestConstants;
import com.swrve.test.components.helpers.TestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = TestConstants.APPLICATION_CONTEXT)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class ReportGeneratorTest {

    @Autowired
    private ReportGenerator reportGenerator;
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
    public void whenGenerateReportForDataFromUrl_thenOk() throws Exception {
        List<String> report = reportGenerator.generateReportForDataFromUrl(TestConstants.URL);
        assertThat(report, contains(TestConstants.REPORT.toArray()));
    }

} 

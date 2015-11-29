package com.swrve.test.components.jbehave;

import com.swrve.summarizer.components.generators.ReportGenerator;
import com.swrve.test.components.helpers.TestHelper;
import com.swrve.test.constants.TestConstants;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@Component
public class ReportSteps {

    @Autowired
    private TestHelper testHelper;
    @Autowired
    private ReportGenerator reportGenerator;

    private List<String> report = null;

    @BeforeStories
    public void beforeStories() {
        testHelper.startMockServer();
    }

    @AfterStories
    public void afterStories() {
        testHelper.stopMockServer();
    }

    @Given("a data file with the following lines: $dataTable")
    public void givenADataFileWithTheFollowingData(@Named("dataTable") ExamplesTable dataTable)
            throws IOException {
        String data = testHelper.extractStringFromExamplesTable(dataTable);
        testHelper.createWiremockStubWithOkResponseAndBodyFromString(data);
    }

    @When("I generate the report")
    public void whenIGenerateTheReport() throws Exception {
        report = reportGenerator.generateReportForDataFromUrl(TestConstants.URL);
    }

    @Then("the report contains the following lines: $reportTable")
    public void thenTheFollowingLinesArePrintedToTheStandardOutput(
            @Named("outputTable") ExamplesTable reportTable) {
        List<String> expectedReport = testHelper.extractStringListFromExamplesTable(reportTable);
        assertThat(report, contains(expectedReport.toArray()));
    }

}

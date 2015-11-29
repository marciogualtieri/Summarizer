package com.swrve.summarizer.components.generators;

import com.swrve.summarizer.components.clients.UserInfoClient;
import com.swrve.summarizer.components.readers.GzippedTableReader;
import com.swrve.summarizer.messages.AppMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ReportGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    private final UserInfoClient userInfoClient;

    @Autowired
    public ReportGenerator(UserInfoClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    public List<String> generateReportForDataFromUrl(String url) throws SQLException {
        logger.debug(AppMessages.GENERATING_REPORT_MESSAGE_FORMAT, url);
        userInfoClient.open(url);
        List<String> report = new ArrayList<>();
        report.add(userInfoClient.getUserCount().toString());
        report.add(userInfoClient.getCountForResolution(640, 960).toString());
        report.add(userInfoClient.getTotalSpent().toString());
        report.add(userInfoClient.getFirstUserToJoin());
        userInfoClient.close();
        logger.info(AppMessages.REPORT_GENERATED_MESSAGE_FORMAT, report, url);
        return report;
    }
}

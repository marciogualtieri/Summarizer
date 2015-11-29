package com.swrve.summarizer.cli;

import com.google.common.base.Joiner;
import com.swrve.summarizer.components.generators.ReportGenerator;
import com.swrve.summarizer.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.swrve.summarizer.components")
public class SummarizerCli implements CommandLineRunner {

    private static final String APPLICATION_FAILURE_MESSAGE_FORMAT =
            "Report generator failed with the following exception: %s";

    @Autowired
    private ReportGenerator reportGenerator;
    @Value("${url}")
    private String url;

    @Override
    public void run(String... args) {
        try {
            List<String> report = reportGenerator.generateReportForDataFromUrl(url);
            System.out.println(Joiner.on(AppConstants.LINE_SEPARATOR).join(report));
        } catch (SQLException e) {
            System.out.println(String.format(
                    APPLICATION_FAILURE_MESSAGE_FORMAT, e.getMessage()));
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(
                SummarizerCli.class);
        application
                .setApplicationContextClass(AnnotationConfigApplicationContext.class);
        SpringApplication.run(SummarizerCli.class, args);
    }
}

### TABLE OF CONTENTS
##### [TEST & BUILD THE APPLICATION](#test-and-build-the-application)
##### [EXECUTE THE JAR IN THE COMMAND LINE](#execute-the-jar-in-the-command-line)
###### [Usage](#usage)
###### [Example](#example)
###### [Configuration](#configuration)
##### [BEHAVIORAL DRIVEN DEVELOPMENT](#behavioral-driven-development)
##### [ON THE DESIGN CHOICES](#on-the-design-choices)


### <a name="test-and-build-the-application"></a> TEST & BUILD THE APPLICATION

     $ mvn clean verify package

Maven 3 and Java 8 are required to build the application.

Once the application is built, JavaDocs can be found in the following directory:

     ./target/apidocs/index.html

### <a name="execute-the-jar-in-the-command-line"></a> EXECUTE THE JAR IN THE COMMAND LINE

#### <a name="usage"></a> Usage

     $ java -jar ./target/summarizer-1.0-SNAPSHOT-jar-with-dependencies.jar
          {--url=<URL of the gzipped data file>}

The summary report will then be printed to the standard output, e.g.:

    100
    30
    47223
    284158cc5461cefa9c74035d8b14a107

#### <a name="example"></a> Example

     $ java -jar ./target/summarizer-1.0-SNAPSHOT-jar-with-dependencies.jar
        --url=https://s3.amazonaws.com/dept-dev-swrve/full_stack_programming_test/test_data.csv.gz

#### <a name="configuration"></a> Configuration

Logging can be configured in the following file:

     ./src/main/resources/log4j.xml

At the moment, logs are generated in the folder `./logs` for the application and `./target/logs` for tests.

### <a name="behavioral-driven-development"></a> BEHAVIORAL DRIVEN DEVELOPMENT

The application has BDD integration tests using JBehave, additionally to unit tests (JUnit).
After the application is built, JBehave reports can be found in the following HTML file:

    ./target/jbehave/view/reports.html

BDD scenarios are defined in the following fle:

    ./src/test/resources/jbehave/stories/summarizer.story

### <a name="on-the-design-choices"></a> ON THE DESIGN CHOICES

Given the requirement of querying data from CSV files, I have chosen
[CSV jdbc](http://csvjdbc.sourceforge.net/develop.html) for the task. 
This framework makes possible to run SQL queries against CSV files.

The framework is also easily customizable regarding the source of the data.
 It allows the implementation of a custom table reader class by implementing
 the provided interface `TableReader`, which is used by frameworks' database driver.

This table reader has been implemented to fetch data using the HTTP or HTTPS protocols. Particularly 
 the method `TableReader.getReader()` returns `InputStreamReader`, thus, we can take advantage of the decorator pattern
to handle compressed data by using `GZIPInputStream`.

Once the custom reader has been implemented, it's pretty strait forward to perform standard SQL queries to compute the
 summary report required for the output.

Just for the fun of it, I have used [SpringBoot](http://projects.spring.io/spring-boot/) instead of implementing a
 standard stand-alone Spring application.
Unfortunately [JBehave](http://jbehave.org/reference/stable/maven-goals.html), a powerful BDD (Behavioral Driven
Development) framework, doesn't yet support SpringBoot, therefore, for my JBehave and JUnit tests, I have used
old fashion Spring. 


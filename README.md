### TABLE OF CONTENTS
##### [TEST & BUILD THE APPLICATION](#test-and-build-the-application)
##### [EXECUTE THE JAR IN THE COMMAND LINE](#execute-the-jar-in-the-command-line)
###### [Usage](#usage)
###### [Example](#example)
###### [Configuration](#configuration)
##### [BEHAVIORAL DRIVEN DEVELOPMENT](behavioral-driven-development)
##### [ON THE DESIGN CHOICES](#on-the-design-choices)


### <a name="test-and-build-the-application"></a> TEST & BUILD THE APPLICATION

     $ mvn clean verify package

Maven 3 and Java are required to build the application.

Once the application is built, JavaDocs will be present in the following directory:

     ./target/apidocs/index.html

### <a name="execute-the-jar-in-the-command-line"></a> EXECUTE THE JAR IN THE COMMAND LINE

#### <a name="usage"></a> Usage

     $ java -jar ./target/summarizer-1.0-SNAPSHOT-jar-with-dependencies.jar
          {--url=<URL of the gzipped data file>}

The summary will then be printed to the standard output:

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
After the application is built, JBehave reports will be available in the following HTML file:

    ./target/jbehave/view/reports.html

BDD scenarios can be found in the following fle:

    ./src/test/resources/jbehave/stories/summarizer.story

### <a name="on-the-design-choices"></a> ON THE DESIGN CHOICES

Given the requirement of querying data from CSV files, I have chosen
[CSV jdvbc ](http://csvjdbc.sourceforge.net/develop.html) for the task. 
This framework makes possible to run SQL queries against CSV files.

The framework is also easily customizable regarding the source of the CSV data.
 It allows the implementation of a custom table reader class by implementing
 the provided interface `TableReader`, which is used by CSV jdbc's database driver.

A custom table reader has been implemented to fetch CSV data using the HTTP or HTTPS protocols. Particularly 
 the method `TableReader.getReader()` returns `InputStreamReader`, thus, we can take advantage of the decorator pattern
to handle compressed data by using `GZIPInputStream`.

Once the custom reader has been implemented, it's pretty strait forward to perform standard SQL queries to compute the
 summary required for the report output.

Just for the fun of it, I have used [SpringBoot](http://projects.spring.io/spring-boot/) instead of a standard stand-alone Spring application.
Unfortunately [JBehave](http://jbehave.org/reference/stable/maven-goals.html), a powerful BDD (Behavioral Driven
Development) framework, isn't yet compatible with SpringBoot, therefore, for my JBehave and JUnit tests, I have used
standard Spring (both use their respective Spring runners and use the same `applicationContext.xml` file for
 configuration). 


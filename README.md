
# Serenity FPDS OpenGSA BDD  ![Build Status](https://github.com/venuduggireddy/fpds-opengsa-content/actions/workflows/maven.yml/badge.svg)

Serenity BDD and JUnit 5 project to test FPDS and OpenGSA content integration automation

* Check for `Content`
* Check for `Layout`
* Check each download href has correct desitination `URLs` 
* Check each dowloaded file `File Name` matchs with source
* Check each dowloaded file `ChecksumSHA_256` matchs with source


## Prerequisites

* JDK 1.3 + 
* Maven 3.6 +
* Spring tools suite 

## ChecksumSHA_256

Generating file checksum online [ChecksumSHA_256](https://emn178.github.io/online-tools/sha256_checksum.html)

## Running the tests under Maven

The template project comes with both Maven and Gradle build scripts. To run the tests with Maven, open a command window and run:

    mvn clean verify

## Viewing the reports

Both of the commands provided above will produce a Serenity test report in the `target/site/serenity` directory. Go take a look!

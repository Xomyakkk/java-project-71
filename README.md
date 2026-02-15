### Hexlet tests and linter status:
[![Actions Status](https://github.com/Xomyakkk/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Xomyakkk/java-project-71/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-71&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-71)

# Difference Calculator

Difference Calculator is a program that determines the difference between two data structures. It is a popular task, similar to online services like http://www.jsondiff.com/. This mechanism is used when outputting tests or automatically tracking changes in configuration files.

## Goal

This project challenges even experienced developers. You will face complex architectural decisions, automated testing, continuous integration, and work with data structures and algorithms.

## Features

*   Support for different input formats: YAML and JSON
*   Report generation in plain text, stylish, and JSON formats

## Data Structures and Algorithms

Choosing the right data structures is key to successful architecture. The main challenge is how to describe the internal representation of the diff between files so that it is convenient to work with. This project improves algorithmic thinking through data processing and transformations.

## Architecture

Building a diff involves reading files, parsing input data, building a difference tree, and formatting the output. This requires good code organization, modularity, and abstractions. The project also involves working with command-line arguments.

## Testing and Debugging

Automated tests are an integral part of professional development. This project uses the JUnit framework for testing. It is an ideal project for practicing TDD.

## Usage Example

```bash
# plain format
./app --format plain path/to/file.yml another/path/file.json

Property 'follow' was added with value: false
Property 'baz' was updated. From 'bas' to 'bars'
Property 'group2' was removed

# stylish format
./app file1.json file2.json

{
  + follow: false
  + numbers: [1, 2, 3]
    setting1: Value 1
  - setting2: 200
  - setting3: true
  + setting3: {key=value}
  + setting4: blah blah
}
```
<a href="https://asciinema.org/a/dFLVNmZtldO0yoUM" target="_blank"><img src="https://asciinema.org/a/dFLVNmZtldO0yoUM.svg" /></a>
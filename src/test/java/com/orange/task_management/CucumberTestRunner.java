package com.orange.task_management;


import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("Features")
public class CucumberTestRunner {

}
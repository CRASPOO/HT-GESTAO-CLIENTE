//package com.example.order.bdd;

//import io.cucumber.junit.platform.engine.Cucumber;

//@Cucumber
//public class CucumberTest { }
package com.example.order.bdd; // Ou o pacote onde ela estiver

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
// AQUI ESTÁ A CORREÇÃO: Aponta para a pasta dentro de src/test/resources
@SelectClasspathResource("features")
// AQUI DIZEMOS ONDE ESTÃO OS STEPS (JAVA):
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.order.bdd")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class CucumberTest {
}
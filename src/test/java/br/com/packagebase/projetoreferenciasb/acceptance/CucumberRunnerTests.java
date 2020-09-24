package br.com.packagebase.projetoreferenciasb.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", plugin = {"pretty", "html:target/cucumber"})
public class CucumberRunnerTests {
}

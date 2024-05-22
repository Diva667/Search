package org.Runner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\src\\test\\resources\\Feature_Files\\Search.feature",
					tags = {"@DealIterate"},
					glue = "org.stepDefinition",
					dryRun = false,
					plugin = "json:C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\target\\Reports\\JSON\\consolidated.json")

public class Runner_Class {
	
	@AfterClass
	public static void jvm() {
		Reports.jvmReport("C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\target\\Reports\\JSON\\consolidated.json");
	}

}

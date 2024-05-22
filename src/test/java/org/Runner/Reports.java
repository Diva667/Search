package org.Runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class Reports {
	
	public static void jvmReport(String lo) {

		File f = new File("C:\\Users\\diva6\\eclipse-workspace\\Informa_Search\\target\\Reports\\JVM");

		Configuration c = new Configuration(f, "IGM Informa");
		c.addClassifications("Funtionality", "Search");


		List<String> l = new ArrayList();
		l.add(lo);

		ReportBuilder r = new ReportBuilder(l, c);

		r.generateReports();
		
	}

}

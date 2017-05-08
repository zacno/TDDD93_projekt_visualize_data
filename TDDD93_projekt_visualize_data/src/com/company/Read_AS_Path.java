package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Read_AS_Path
{
    private String[] file_name;
    private final String STORE = "/home/zacno594/liu/tddd93/TDDD93_projekt_visualize_data/src/com/company/data.txt";

    Read_AS_Path(String[] file_name) {
        this.file_name = file_name;
    }

    String readInData() {
	StringBuilder paths = new StringBuilder();

	try {
	PrintWriter writer = new PrintWriter(STORE, "UTF-8");
	for (int i = 0; i < file_name.length; i++) {
	    BufferedReader reader = new BufferedReader(new FileReader(file_name[i]));
	    String line = reader.readLine();
	    while (line != null && !line.isEmpty()) {
	        if (line.contains("Path Segment Value:")) {
		    //System.out.println(line);
		    //System.out.println(line.split("Path Segment Value:")[1]);
		    //paths.append(line.split("Path Segment Value:")[0]);
		    //paths.append("\r\n");
		    writer.println(line.split("Path Segment Value:")[1]);
		}
		line = reader.readLine();
	    }
	}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
 	} catch (IOException e) {
		e.printStackTrace();
 	}

	return paths.toString();
    }
}

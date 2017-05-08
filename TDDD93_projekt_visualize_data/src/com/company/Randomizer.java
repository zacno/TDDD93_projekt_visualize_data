package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Randomizer
{
    Randomizer() {

    }
    public void generate() {
	try {
	    PrintWriter writer = new PrintWriter("/home/zacno594/liu/tddd93/TDDD93_projekt_visualize_data/src/com/company/random" , "UTF-8");
	    Random random = new Random();
	    //writer.println("1.0.4.0");
	    //writer.println("22");
	    for (int i = 0; i < 100000; i++) {
	        for (int j = 0;  j < 6; j++) {
			writer.print(random.nextInt(10000) + " ");
		}
		writer.println();
	    }
	    writer.flush();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
    }
    }

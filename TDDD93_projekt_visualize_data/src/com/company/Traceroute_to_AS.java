package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Traceroute_to_AS
{
    private final static String SAVE_AS = "/home/zacno594/liu/tddd93/TDDD93_projekt_visualize_data/src/com/company/result.txt" ;
    private final static String READ_FROM = "/home/zacno594/liu/tddd93/projekt/as_traceroute_22.txt";
    private final static String CHECK_AGAINST = "/home/zacno594/liu/tddd93/projekt/as_database.txt";


    public static void run() {
        try {
            BufferedReader readerCheck = new BufferedReader(new FileReader(CHECK_AGAINST));
            Map<String, String> check = new HashMap<String, String>();
            while (readerCheck.ready()) {
                String read = readerCheck.readLine();
                if (read.contains("AS")) {
                    System.out.println(read);
                    String[] splitCheck = read.split("\t");
                    if (splitCheck.length < 2) {
                        splitCheck = read.split(" ");
                    }
                    String[] ip = splitCheck[0].split(" ");
                    String[] as = splitCheck[1].split(" ");
                    System.out.println("Ip.length = " + ip.length + " AS.length = " + as.length);
                    for (int i = 0; i < Math.min(ip.length, as.length); i++) {
                        check.put(ip[i], as[i]);
                    }
                }
            }
            BufferedReader reader = new BufferedReader(new FileReader(READ_FROM));
            FileWriter writer = new FileWriter(SAVE_AS);
            String line = reader.readLine();
            while (reader.ready()) {
                if (line.startsWith("traceroute to")) {
                    System.out.println(line);
                    writer.append(line);
                    writer.append("\r\n");
                } else if (line.contains("Tue May")) {
                    System.out.println(line);
                    writer.append(line);
                    writer.append("\r\n");
                } else if (line.contains("(") && line.contains(")")) {
                    StringBuilder ip = new StringBuilder();
                    String tmp = line;
                    while (line.contains("(") && line.contains(")")) {
                        int start = line.indexOf("(");
                        int end = line.indexOf(")") + 1;
                        String split = line.substring(start + 1, end - 1);
                        //System.out.println(split);
                        ip.append(split);
                        ip.append(" - ");
                        ip.append(check.get(split));
                        ip.append(" - ");
                        line = line.substring(0, start) + line.substring(end, line.length());
                    }
                    while (tmp.contains(" AS")) {
                        String[] split = tmp.split(" ");
                        for (int i = 0; i < split.length ; i++) {
                            if (split[i].contains("AS")) {
                                ip.append(split[i]);
                                ip.append("-");
                            }
                        }
                    }
                    ip.append("\r\n");
                    System.out.println(ip.toString());
                    writer.append(ip.toString());
                }
                line = reader.readLine();
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void take_out_AS_paths() {
        try {
            BufferedReader readerCheck = new BufferedReader(new FileReader(READ_FROM));
	    FileWriter writer = new FileWriter(SAVE_AS);
            String line;
	    while (readerCheck.ready()) {
		line = readerCheck.readLine();
		if (line.contains("Tue May")) {
		    writer.append("\r\n");
		    writer.append("\r\n");
		    writer.append(line);
		    writer.append("\r\n");
		} else if (line.contains("traceroute")) {
		    writer.append(line);
		    writer.append("\r\n");
		} else if (line.contains("AS")) {
		    String[] as_ip = line.split(" - ");
		    StringBuilder as = new StringBuilder();
		    for (int i = 0; i<as_ip.length; i++) {
		        if (as_ip[i].contains("AS")) {
		            as.append(as_ip[i].substring(2, as_ip[i].length()));
		            as.append("-");
			}
		    }
		    if (as.toString().split("-").length > 1) {
		        boolean same = true;
		        String[] as_on_same_row = as.toString().split("-");
		        String tmp = as_on_same_row[0];
		        for (int j = 1; j < as_on_same_row.length; j++) {
		            same = tmp.contentEquals(as_on_same_row[j]) && same;
		            tmp = as_on_same_row[j];
			}
			if (same) {
		            writer.append(as_on_same_row[0]);
		            writer.append(" ");
			} else  {
			    writer.append(as.toString());
			    writer.append(" ");
			}
		    } else {
		        writer.append(as.toString().split("-")[0]);
		        writer.append(" ");
		    }
		}
	    }
	    writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
	    e.printStackTrace();
	}
    }

}

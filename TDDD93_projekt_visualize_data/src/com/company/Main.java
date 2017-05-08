package com.company;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;


public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            //Traceroute_to_AS.run();
            //Traceroute_to_AS.take_out_AS_paths();
            //String[] path = new String[16];
            //for (int j = 0; j<16; j++) {
            //    path[j] = "/home/zacno594/Desktop/parsed.txt_" + j;
	    //}
            //Read_AS_Path test = new Read_AS_Path(path);
            //test.readInData();

	    BufferedReader reader = new BufferedReader(new FileReader(args[0]));
	//    String[] announcement = readAnnouncement(reader);
            //for (int i = 1; i < args.length; i++) {
	//	reader = new BufferedReader(new FileReader(args[i]));
	//	String[] temp = readAnnouncement(reader);
	//	System.out.println("Temp.length = " + temp.length + " Announcement.length = " + announcement.length);
	//	announcement = concat(announcement, temp);
	 //   }
	 //   System.out.println(announcement.length);
	    visulizeInformation(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static String[] concat(String[] a, String[] b) {
        int aLen = a.length;
	int bLen = b.length;
	String[] c= new String[aLen+bLen];
	System.arraycopy(a, 0, c, 0, aLen);
	System.arraycopy(b, 0, c, aLen, bLen);
	return c;
    }

    public static String[] readAnnouncement(BufferedReader reader) {
            String[] announcement;
            StringBuilder announced = new StringBuilder();
            try {
                String line = reader.readLine();
                while (line != null && !line.isEmpty()) {
                    announced.append(line + "\r\n");
		    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            announcement = announced.toString().split("\r\n");
            return announcement;
        }


    public static void visulizeInformation(BufferedReader reader) {
        Graph graph = new AdjacencyListGraph("AS:es");
	//Graph graph = new SingleGraph("AS:es");
	graph.addAttribute("ui.stylesheet", styleSheet);
	graph.addAttribute("ui.quality");
	//graph.addAttribute("ui.antialias");
	String announcment = null;
	try {
	    announcment = reader.readLine();
	    while (announcment != null && !announcment.isEmpty()) {
		//if (announcment.endsWith("3356")) {
		    String[] line = announcment.split(" ");
		    for (int j = 0; j < line.length; j++) {
			if (graph.getNode(line[j]) == null) {
			    Node node = graph.addNode(line[j]);
			    node.addAttribute("ui.label", line[j]);
			    if (j == 0) {
				node.setAttribute("ui.class", "marked");
				node.addAttribute("ui.label", line[j]);
				node.setAttribute("ui.style", "text-mode: normal;");
			    }
			}
			if (j > 0) {
			    if (graph.getEdge(line[j - 1] + " " + line[j]) == null &&
				graph.getEdge(line[j] + " " + line[j - 1]) == null) {
				graph.addEdge(line[j - 1] + " " + line[j], line[j - 1], line[j]);
			    }
			}
		    }
		//}
	    announcment = reader.readLine();
	}

	for (int i = 0; i < graph.getNodeCount(); i++) {
	    Node node = graph.getNode(i);
	    if (node.getDegree() > 50 && node.getAttribute("ui.class") != "marked") {
		node.setAttribute("ui.class", "intresting");
		node.addAttribute("ui.label", node.getId());
	    }
	}
	} catch (IOException e) {
		    e.printStackTrace();
		}
	System.out.println("Graph Nodes Cont = " +  graph.getNodeCount());
	graph.display();
    }

    protected static String styleSheet =
           "graph {" +
	   "	fill-color: white;" +
	   "}" +
	    "node {" +
           //"	fill-color: #7fbf7b;" +
	   "	fill-color: #396AB1;" +
	   "	size: 5px;" +
	   "}" +
           "node.marked {" +
           //"	fill-color: #f7f7f7;" +
	   "	fill-color: #DA7C30;" +

	   "	size: 10px;" +
	   "}" +
	   "node.intresting {" +
	   //"	fill-color: red;" +
	   "	fill-color: #3E9651;" +
	   "	size: 15px;" +
	   "}" +
	    "edge {" +
            //"	fill-color: #af8dc3;" +
	    "	fill-color: #CC2529;" +
	    //"	size: 1px;" +
            "}";

}




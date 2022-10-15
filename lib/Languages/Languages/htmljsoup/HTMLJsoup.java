package htmljsoup;


import java.io.*;
import org.jsoup.*;

public class HTMLJsoup {

	public static void main(String[] args) throws IOException {
		org.jsoup.nodes.Document doc = Jsoup.connect("http://luisrueda.cs.uwindsor.ca/researchint").get();
		String text = doc.text();
		System.out.println(text);
		PrintWriter out = new PrintWriter("jsoupText.txt");
		out.println(text);
		out.close();
	}
}

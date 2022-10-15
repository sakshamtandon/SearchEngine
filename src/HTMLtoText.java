import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.jsoup.Jsoup;

public class HTMLtoText extends HTMLEditorKit.ParserCallback {
	StringBuffer s; // string buffer class is used for creating mutable strings

	public HTMLtoText() {
	}

	// Reader class is an abstract class that represent a stream of characters
	public void parse(Reader in) throws IOException {
		s = new StringBuffer();
		ParserDelegator delegator = new ParserDelegator();
		// the third parameter is TRUE to ignore charset directive
		delegator.parse(in, this, Boolean.TRUE);
	}

	public void handleText(char[] text, int pos) {
		s.append(text);
	}

	public String getText() {
		return s.toString(); // converts string object into a string
	}

	public static void conversion(File destinationfile, int m)
			throws IOException, FileNotFoundException, NullPointerException

	{

		try {

			org.jsoup.nodes.Document _doc = Jsoup.parse(destinationfile, "UTF-8");

			BufferedWriter bufferedWriterBW = new BufferedWriter(new FileWriter(
					"/Users/sakshamtandon/Downloads/UofW Library/ACC/Advance_Computing/ACC search engine/Search-Engine-master/W3C Web Pages/Text"
							+ destinationfile.getName() + ".txt"));

			bufferedWriterBW.write(_doc.text());

			bufferedWriterBW.close();

			System.out.println("File " + destinationfile.getName() + " to  " + destinationfile.getName() + ".txt");

		} catch (Exception e) {

		}

	}

	public static void main(String[] args) throws IOException, FileNotFoundException, NullPointerException {

		int m = 1;

		try {

			File directoryPth = new File(
					"/Users/sakshamtandon/Downloads/UofW Library/ACC/Advance_Computing/ACC search engine/Search-Engine-master/W3C Web Pages");

			File[] FilesArrayA1 = directoryPth.listFiles();
			System.out.println(FilesArrayA1.length);

			File[] FilesArrayA2 = new File[105];

			int totalCount = 0;

			for (int i = 0; i < FilesArrayA1.length; i++)

			{

				if (FilesArrayA1[i].isFile())

				{

					FilesArrayA2[totalCount] = FilesArrayA1[i];

					totalCount++;

				}

			}

			for (int i = 0; i < 104; i++)

			{

				conversion(FilesArrayA2[i], m);

				m = m + 1;
			}
		}

		catch (Exception e) {

			System.out.println("Exception:" + e);

		}

		finally

		{

		}

	}

}
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSearchEngine {

	public static String metadata;
	public static String search1;
	static ArrayList<String> suggested_regex_list = new ArrayList<String>();
	static Hashtable<String, Integer> edit_distance_hash = new Hashtable<String, Integer>();
	static Scanner sc = new Scanner(System.in);

	// get occurance and position of word
	public int searchWord(File filePath, String search) throws IOException {

		int counter = 0; // Taking counter to calculate the frequency of the searched keyword

		String data = ""; // Empty string that will contain the text of the file

		try {

			BufferedReader reader_obj = new BufferedReader(new FileReader(filePath)); // Initialising reader object that
																						// takes the filepath and reads
																						// the
																						// text.
			String line = null; // Initialising with null to keep a check on empty lines in text files

			while ((line = reader_obj.readLine()) != null) {

				data = data + line; // Appending the lines of the text file into a large string.
			}

			reader_obj.close(); // Closing the reader object.

//			System.out.println("DATA: " + data);
		} catch (NullPointerException e) {
			e.printStackTrace(); // Printing the TraceBack of the error.
		}

//		String txt = data;

		BoyerMoore boyerSearch = new BoyerMoore(search); // Creating the object of BoyerMoore class and passing search
															// keyword in the constructor as argument

		int offset = 0; // Offset to handle multiple occurences of the searched keyword

//		int offset1 = 50;

		for (int loc = 0; loc <= data.length(); loc += offset + search.length()) {

			offset = boyerSearch.horspool(data.substring(loc), search); // Calling search function of boyermoore that
																		// takes pattern and text and return len(text)
																		// if not found else index of pattern found

			// System.out.println("\n");
			if ((offset + loc) < data.length()) {
				counter++;

				System.out.println(search + " is found at  position " + (offset + loc)); // printing position of word

//				int sum = offset + loc;
				search1 = search;
//				metadata = txt.substring(sum - offset1, sum + offset1);
//				System.out.println(txt.substring(sum - offset1, sum + offset1));
			}
		}

		if (counter != 0) {
			System.out.println("\n                                     In file: " + filePath.getName());
			System.out.println("\t\t\t\t-----------------------------------------------------\n");

		}

		return counter;
	}

	// Ranking of Web Pages using merge sort
	// Collections.sort by default uses merge sort
	public static void rankFiles(Hashtable<String, Integer> hashtable, int occur) {

		// Transfer as List and sort it
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(hashtable.entrySet());

		// Sorting using merge sort and creating a generic comparator.
		Collections.sort(list, new Comparator<Map.Entry<?, Integer>>() {

			public int compare(Map.Entry<?, Integer> obj1, Map.Entry<?, Integer> obj2) {
				return obj1.getValue().compareTo(obj2.getValue());
			}
		});

		Collections.reverse(list); // Converting arraylist to descending order.

		System.out.println("List: " + list);

		if (occur != 0) {
			System.out.println("\n------Top 10 Search Results for " + search1 + "------\n");

			int num = 10;
			int j = 1;
			while (list.size() > j && num > 0) {
				System.out.println("(" + j + ") " + list.get(j) + " times ");
				j++;
				num--;
			}
		} else {

		}

	}

	/* using regex to find similar string to pattern */
	public void suggest(String pattern) {
		try {

			// String to be scanned to find the pattern.
			String line = " ";

			String regular_expression = "[\\w]+[@$%^&*()!?{}\b\n\t]*";

			// Create a Pattern object
			Pattern re = Pattern.compile(regular_expression);

			// Now create matcher object.
			Matcher match = re.matcher(line);

//			System.out.println(match.matches());

			int fileNum = 0;

			try {
				File directory = new File(
						"/Users/sakshamtandon/Downloads/UofW Library/ACC/Advance_Computing/ACC search engine/Search-Engine-master/W3C Web Pages/Text");

				File[] fileArray = directory.listFiles();

				for (int i = 0; i < fileArray.length; i++) {
					findWord(fileArray[i], fileNum, match, pattern); // Updates edit_distance_hashtable using edit
																		// distance.
					fileNum++;
				}

//				Set keys = new HashSet();				Integer value = 1;

				int counter = 0;

				System.out.println("\nDid you mean?:");

				System.out.println("---------------------");

				for (Map.Entry<String, Integer> entry : edit_distance_hash.entrySet()) // Iterator to iterate over
																						// hashmap
				{
//					System.out.println("Entry,Value: " + entry.getKey() + " , " + entry.getValue());

					if (entry.getValue() == 0) //
					{
						break; // This means the edit distance is zero, hence break.
					} else if (entry.getValue() == 1 && counter == 0) {
						System.out.print(entry.getKey());
						counter++;
					} else {
						System.out.print(" or " + entry.getKey());
						counter++;
					}

				}
//					else {
//
//						if (entry.getValue() == 1) {
//							if (counter == 0) {
//								System.out.print(entry.getKey());
//								counter++;
//							}
//
//							else {
//								System.out.print(" or " + entry.getKey());
//								counter++;
//							}
//
//						}
//
//					}

			} catch (Exception e) {
				System.out.println("Exception:" + e);
			} finally {

			}

		} catch (Exception e) {

		}
	}

	// finds strings with similar pattern and calls edit distance() on those strings
	public static void findWord(File sourceFile, int fileNumber, Matcher match, String str)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		try {
//			int i = 0;
			BufferedReader readerObject = new BufferedReader(new FileReader(sourceFile)); // Initialising the reader
																							// object that reads the
																							// source file.

			String line = null; // Checking on null lines

			while ((line = readerObject.readLine()) != null) {
				match.reset(line); // Reseting the matcher object with a new line.

				while (match.find()) // If true, then...
				{
					suggested_regex_list.add(match.group()); // Append the matches in the array list.
				}
			}

			readerObject.close();

			for (int item = 0; item < suggested_regex_list.size(); item++) // Iterating through the suggested_regex_list
																			// and...
			{
				edit_distance_hash.put(suggested_regex_list.get(item),
						editDistance(str.toLowerCase(), suggested_regex_list.get(item).toLowerCase())); // Calling edit
																										// distance on
																										// each string
																										// and storing
																										// it in a
																										// Hashtable.
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

	// Uses Edit distance to compare nearest distance between keyword and similar
	// patterns obtained from regex
	public static int editDistance(String str1, String str2) {
		int len1 = str1.length(); // calculating length of string
		int len2 = str2.length(); // calculating length of string

		int[][] array = new int[len1 + 1][len2 + 1]; // Creating a 2-D array of +1 length. (+1 to create empty
														// array[][])

		for (int i = 0; i <= len1; i++) {
			array[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			array[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {

			char c1 = str1.charAt(i);

			for (int j = 0; j < len2; j++) {

				char c2 = str2.charAt(j);

				if (c1 == c2) {

					array[i + 1][j + 1] = array[i][j]; // If the characters match, then copy the diagonal element to
				} else {
					int replace = array[i][j] + 1;
					int insert = array[i][j + 1] + 1;
					int delete = array[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					array[i + 1][j + 1] = min; // Adding the minimum of replace,insert,delete
				}
			}
		}

		return array[len1][len2]; // Returns the last block of matrix
	}

	public static void main(String[] args) {
		while (true) {

			WebSearchEngine websearch = new WebSearchEngine(); // Creating an object of class WebSearchEngine

			Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>(); // Initiliasing a hashtable of type
																						// key:String,value:Integer

			@SuppressWarnings("resource")

			Scanner scan = new Scanner(System.in); // Creating a scanner object to take user input

			System.out.println("\n*-*-*-*-*-*-*-*- ");
			System.out.println("Enter your search: ");

			String input = scan.nextLine(); // Taking user input in the next line

//			long fileNumber = 0;

			int frequency = 0;

			int number_of_files_containing_word = 0; // No. of files that contains the Searched word

			try {

				long startTime = System.currentTimeMillis();

				long startTimesearch = System.currentTimeMillis();

				File dir = new File(
						"/Users/sakshamtandon/Downloads/UofW Library/ACC/Advance_Computing/ACC search engine/Search-Engine-master/W3C Web Pages/Text");

				File[] fileArray = dir.listFiles(); // Storing the .txt files in a fileArray.

				for (int i = 0; i < fileArray.length; i++) // Iterating over the file array
				{
					// Calling searchWord function,
					// that uses boyermore search algorithm to search the pattern and returns the
					// index of the search pattern.

					frequency = websearch.searchWord(fileArray[i], input);

//					System.out.println("Input" + input + "frequency " + frequency);

					hashtable.put(fileArray[i].getName(), frequency); // Adding the frequency of each word and the file
																		// name in a hashtable.

//					System.out.println("HashTable: " + hashtable);

					if (frequency != 0) {
						number_of_files_containing_word++; // Incrementing the count when the word is found atleast once
															// in a file.
					}

//					fileNumber++;
				}

				System.out.println(
						"\nNumber of files contains " + input + " word is= " + number_of_files_containing_word);

				long endTimesearch = System.currentTimeMillis();

				System.out.println(
						"\nSearch Result execution time:" + (endTimesearch - startTimesearch) + " Milli Seconds");

				if (number_of_files_containing_word == 0) // If the word is not found in any file, then we suggest the
															// user.
				{

					System.out.println("\nSearching...");

					websearch.suggest(input); // Calls edit_distance and suggests using regex.

				}

				double startTimerank = System.nanoTime();

				rankFiles(hashtable, number_of_files_containing_word); // Sorting the hashtable using merge sort and
																		// reversing the result.

				double endTimerank = System.nanoTime();

				System.out.println("\nRanking Algorithm time:" + (endTimerank - startTimerank) + " Nano Seconds");

				long endTime = System.currentTimeMillis();

				System.out.println("\nTotal Execution Time:" + (endTime - startTime) + " Milli Seconds");

			} catch (Exception e) {
				System.out.println("Exception11:" + e);
			} finally {

			}
		}
	}
}
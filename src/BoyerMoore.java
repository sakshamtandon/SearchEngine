public class BoyerMoore {
//	private final int R; // the radix
//	private static int[] table; // the bad-character skip array
//
//	public char[] pattern; // store the pattern as a character array
//	public String pat; // or as a string
	public static int SIZE = 70000;
	public static int table[] = new int[SIZE];

	// pattern provided as a string
	public BoyerMoore(String pattern) {

		int i, j, pattern_len;
		char p[] = pattern.toCharArray(); // Converting pattern to a string array.
//			System.out.println(p[0]);
		pattern_len = pattern.length();

		for (i = 0; i < SIZE; i++) {
			table[i] = pattern_len; // Filling the array with initial values
		}

		for (j = 0; j < pattern_len - 1; j++) {
			table[p[j]] = pattern_len - 1 - j; // Changing the corresponding values for characters in table according to
												// the formula.
//			    System.out.println(table[p[j]]);
		}

//			System.out.println("Table: "+Arrays.toString(table));

//		this.R = 70000;
//		this.pat = pat;
//
//		// position of rightmost occurrence of c in the pattern
//
//		table = new int[R];
//
//		for (int c = 0; c < R; c++)
//			table[c] = -1;
//
//		for (int j = 0; j < pat.length(); j++)
//			table[pat.charAt(j)] = j;
	}

	// return offset of first match; N if no match
//	public int search(String pat, String txt) {
//		int M = pat.length();
//		int N = txt.length();
//		int skip;
//		for (int i = 0; i <= N - M; i += skip) {
//			skip = 0;
//			for (int j = M - 1; j >= 0; j--) {
//				if (pat.charAt(j) != txt.charAt(i + j)) {
//					skip = Math.max(1, j - right[txt.charAt(i + j)]);
//					break;
//				}
//			}
//			if (skip == 0)
//				return i; // found
//		}
//		return N; // not found
//	}

	public int horspool(String text, String pattern) {

		int i, k, pos, pattern_len;

		char t[] = text.toCharArray();

		char p[] = pattern.toCharArray();

		pattern_len = pattern.length();

		int text_len = text.length();

		for (i = pattern_len - 1; i < text.length();) // Checking sequence of text and pattern from left to right
		{
			k = 0;
			while ((k < pattern_len) && (p[pattern_len - 1 - k] == t[i - k])) {
				k++; // Comparing the characters in reverse
			}
			// order
			if (k == pattern_len) {
				pos = i - pattern_len + 2;
				return pos;
			} else {
				i += table[t[i]];
			}

		}
		return text_len;
	}

}
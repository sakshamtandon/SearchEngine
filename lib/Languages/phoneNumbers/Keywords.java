package phoneNumbers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keywords
{
    public static void main( String args[] ){

      // String to be scanned to find the pattern.
      String line = "micro, microarray, micro arrays, microarrays, micro-chips, array, arrays";
      String pattern = "(micro)?( |-)?(array|chip)(s)?";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);

      // Now create matcher object.
      Matcher m = r.matcher(line);
      while (m.find( )) {
          System.out.println("Found value: " + m.group(0) + " at " + m.start(0) );
      } 
   }
}
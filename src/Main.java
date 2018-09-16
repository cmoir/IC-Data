import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.parser.*;
import org.jsoup.internal.*;
import org.jsoup.select.*;
import org.jsoup.safety.*;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("https://imperialconflict.com/families/view.php?family=6112").get();
		getFamilyList(doc);
		}
		   
   private static void getFamilyList(Document doc) {
	    Element family = doc.getElementById("family-view");
		String familyData = family.toString();
		
		
		Pattern empireNamePattern = Pattern.compile("empire=\\d{6}\">(\\w+)");
		Matcher empireNameMatcher = empireNamePattern.matcher(familyData);
		if (empireNameMatcher.find()) {
			    System.out.println(empireNameMatcher.group(1));
			}
		
		Pattern networthPattern = Pattern.compile("(?s)empire=\\d{6}\\\">(\\w+)</a>.</td>\\s*<td><a href=..view_race.php.id=\\d.>\\w+</a></td>\\s*<td>((\\d{0,3},)?(\\d{3},)?\\d{0,3})</td>\\s{5,6}<td>(\\d*)</td> ");
		Matcher networthMatcher = networthPattern.matcher(familyData);
		
		while (networthMatcher.find())	{
		     System.out.println(networthMatcher.group(1));
		     System.out.println(networthMatcher.group(2));
		     System.out.println(networthMatcher.group(5));
		}					
   }
   
}

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
		
		
		String filename = "ICdata.db";
		String url = "jdbc:sqlite:E:/Java/sqlite/db/" + filename;
		
		Document doc = Jsoup.connect("https://imperialconflict.com/families/view.php?family=6112").get();
		getFamilyList(doc);
       
		//DB stuff
		//createNewDatabase(url);
        //connect(url);
        SQlite.createNewTable(url);
        SQlite.insert("p1", 54321, 50, url);
        SQlite.insert("Semifinished Goods", 12345, 36, url);
        SQlite.insert("Finished Goods", 5000, 44, url);
        SQlite.selectAll();
        
		
		}
		   
   private static void getFamilyList(Document doc) {
	    Element family = doc.getElementById("family-view");
		String familyData = family.toString();
		
		Pattern familyMemberPattern = Pattern.compile("(?s)empire=\\d{6}\\\">(\\w+)</a>.</td>\\s*<td><a href=..view_race.php.id=\\d.>\\w+</a></td>\\s*<td>((\\d{0,3},)?(\\d{3},)?\\d{0,3})</td>\\s{5,6}<td>(\\d*)</td> ");
		Matcher familyMember = familyMemberPattern.matcher(familyData);
		
		while (familyMember.find())	{
		     System.out.println(familyMember.group(1));
		     System.out.println(familyMember.group(2));
		     System.out.println(familyMember.group(5));
		}					
   }
   
}

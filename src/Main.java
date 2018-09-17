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
import java.sql.Connection;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
		String filename = "ICdata.db";
		String url = "jdbc:sqlite:E:/Java/sqlite/db/" + filename;
		
		Document doc = Jsoup.connect("https://imperialconflict.com/families/view.php?family=6113").get();
		
		
		SQlite.createNewDatabase(url);
		SQlite.createNewTable(url);
		

		Connection conn = SQlite.connect(url);
		
		getFamilyList(doc, conn);
		
		//DB stuff
		//SQlite.createNewDatabase(url);
		//Connection conn = SQlite.connect(url);
        
        SQlite.selectAll();
        
		
		}
		   
   private static void getFamilyList(Document doc, Connection conn) {
	    Element family = doc.getElementById("family-view");
		String familyData = family.toString();
		
		Pattern familyMemberPattern = Pattern.compile("(?s)empire=\\d{6}\\\">(\\w+)</a>.</td>\\s*<td><a href=..view_race.php.id=\\d.>\\w+</a></td>\\s*<td>((\\d{0,3},)?(\\d{3},)?\\d{0,3})</td>\\s{5,6}<td>(\\d*)</td> ");
		Matcher familyMember = familyMemberPattern.matcher(familyData);
		
		while (familyMember.find())	{
			
			 String name = familyMember.group(1);
			 int networth = Integer.parseInt(familyMember.group(2).replace(",",""));
			 int planets = Integer.parseInt(familyMember.group(5)); 
			 
			 SQlite.insert(name, networth, planets, conn);
		     //System.out.println(familyMember.group(1));
		     //System.out.println(familyMember.group(2));
		     //System.out.println(familyMember.group(5));
		}					
   }
   
}

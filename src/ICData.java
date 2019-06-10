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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ICData {
	
	static String filename = "IC_MW_Round_70.db";
	static String url = "jdbc:sqlite:F:/Videos/Home Videos/OneDrive/IC/" + filename;

	public static void main(String[] args) throws IOException, ParseException, InterruptedException {


		SQlite.createNewDatabase(url);
		SQlite.createNewTable(url);
		writing((SQlite.selectAllData(url)));
		for (int retries = 0;; retries++) {
			try {
				int FamCount = 10;
				int firstFam = 7048;
				int i = 0;

				while (i <= 5000) {
					for (int x = firstFam; x <= (firstFam + FamCount - 1); x++) {
						Calendar cal = Calendar.getInstance();
						int min = cal.get(Calendar.MINUTE);

						if ((min > 10)) {
							getAllFamilyData(x);							Thread.sleep(3000);
						} else {
							Thread.sleep(600000);
							getAllFamilyData(x);
							Thread.sleep(3000);
						}
					}
					i++;
					writing((SQlite.selectAllData(url)));
					Thread.sleep(1500000);
					
				}
				
			
				;
			} catch (Exception e) {
				if (retries < 100) {
					Thread.sleep(60000);
					continue;
				} else {
					throw e;
				}
			}
		}
		
	}

	private static void getAllFamilyData(int familyIn) throws IOException, ParseException {
		try {
			String family = Integer.toString(familyIn);


			int maxTurn = SQlite.selectTurn(url, family);
			int currentTurn = turn();
			if (maxTurn < currentTurn || maxTurn == 0) {


				Connection conn = SQlite.connect(url);

				getFamilyList(conn, url, family);

				System.out.println("_______________________________________________________________________");
				System.out.println("Family " + family);

				SQlite.selectAll(url, family);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void outputDataTxt() throws IOException, ParseException {
		try {
			System.out.println(SQlite.selectAllData(url));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void getFamilyList(Connection conn, String url, String familyS) throws ParseException, IOException {
		try {
			int turn = turn();
			// if ((SQlite.selectTurn(url, familyS) < turn) || (SQlite.selectTurn(url,
			// familyS) == 0 )) {
			Document doc = Jsoup.connect("https://imperialconflict.com/families/view.php?family=" + familyS).get();

			Element family = doc.getElementById("family-view");
			String familyData = family.toString();

			Pattern familyMemberPattern = Pattern.compile(
					"(?s)empire=\\d{6}\\\">([[a-zA-Z0-9_]\\w+\\s*[a-zA-Z0-9_]]*)</a>.</td>\\s*<td><a href=..view_race.php.id=\\d+.>[\\w+\\s*\\w*?*!*'*$*\\.*]*</a></td>\\s*<td>((\\d{0,3},)?(\\d{3},)?\\d{0,3})</td>\\s{5,6}<td>(\\d*)</td> ");
			Matcher familyMember = familyMemberPattern.matcher(familyData);

			while (familyMember.find()) {
				String name = familyMember.group(1);
				int networth = Integer.parseInt(familyMember.group(2).replace(",", ""));
				int planets = Integer.parseInt(familyMember.group(5));
				SQlite.insert(turn, familyS, name, networth, planets, conn);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static int turn() throws ParseException {
		int baseturn = 89;
		Date baseDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("29/05/2019 21:07");
		Date endDate = new Date();
		long secs = (endDate.getTime() - baseDateTime.getTime()) / 1000;
		int hours = (int) secs / 3600;
		int turn = baseturn + hours;
		return turn;
	}

	public static void writing(String text) {
        try {
            //Whatever the file path is.
            File statText = new File("F:/Videos/Home Videos/OneDrive/IC/"+filename+".txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            w.write(text);
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file statsTest.txt");
        }
    }
}

package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Test {
	@SuppressWarnings("unused")
	private static String readFile(String fileName) {
		String output = "";
		File file = new File(fileName);

		if (file.exists()) {
			if (file.isFile()) {
				try {
					BufferedReader input = new BufferedReader(new FileReader(
							file));
					StringBuffer buffer = new StringBuffer();
					String text;

					while ((text = input.readLine()) != null)
						buffer.append(text + "/n");

					output = buffer.toString();
					input.close();
				} catch (IOException ioException) {
					System.err.println("File Error!");
				}
			} else if (file.isDirectory()) {
				String[] dir = file.list();
				output += "Directory contents:/n";

				for (int i = 0; i < dir.length; i++) {
					output += dir[i] + "/n";
				}
			}
		} else {
			System.err.println("Does not exist!");
		}
		return output;
	}

	@SuppressWarnings("unused")
	private static void writeFile(String fileName, String contentString) {
		File file = new File(fileName);

		BufferedWriter writer = null;

		try {
			file.createNewFile();
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			writer.write(contentString);
			writer.flush();
			writer.close();

			System.out.println("file create successful!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// final Classifier<String, String> bayes = new BayesClassifier<String,
		// String>();
		//
		// ArrayList<String> steam1 = ProcessDocs
		// .stemming(ProcessDocs
		//
		// .tokenize("‘Steam for Linux’ will be available for everyone to try from next week, after spending just over a month as a limited beta. Valve cite the ‘stability of the client’ as allowing for the beta to be opened up to more users. “The Open Beta will be available to the public and will increase the current population from 80K to a higher number,” they wrote in a message to the closed Steam for Linux Mailing List. System Requirements As we noted earlier this month, System Requirements for some Linux games are now being listed on the Steam Store. The list – see below – is not exhaustive. Games developers are being encouraged to submit their ‘minimum’ and ‘recommend’ System Requirements to Valve for publishing ‘ASAP’. Amnesia Dynamite Jack Eversion iBomber Attack Killing Floor Red Orchestra: Ostfront 41-45 Serious Sam 3:BFE Sword & Sworcery The Book of Unwritten Tales: The Critter Chronicles World of Goo Other Notes A handful of other changes will accompany the Open Beta according to Valve. These include: A new web page with details on installing Ubuntu and Steam for Linux Steam for Linux repository will be set up for installing the client Statistics for the current Linux games library will soon be available Supported Linux games will be available for purchase"));
		// bayes.learn("Steam", steam1);
		//
		// ArrayList<String> steam2 = ProcessDocs
		// .stemming(ProcessDocs
		//
		// .tokenize("More users now been given access to the limited Steam for Linux beta, Valve have confirmed. Announcing the expansion of beta access on the Steam for Linux form, Valve’s Frank Crockett urged those who have applied previously to check their inbox for a redemption code. This expansion brings the total number of Steam for Linux users up to 21,542 Members. The company added an additional 5,000 users late last month. New Bug Forums In addition to wider access, Valve also announced several new sub-forums have been added for testers to report bugs in. These include sections for Valve Game bugs, Non-Valve games bug, to forums for bug reporting against specific graphics cards. Getting Close Another sign that the Steam for Linux beta is nearing its end listing of Linux System Requirements  games, the beta period for Serious Sam 3: BFE is now over – meaning players need to purchased the game inorder to keep playing. Saved game data remains unaffected. Serious Sam 3: BFE was available to play for free during the beta period. With that period now over, could the client be one step closer to a proper release? For a full list of Linux-friendly games available on Steam can be found hiding behind the button below."));
		// bayes.learn("Steam", steam2);
		//
		// ArrayList<String> linux1 = ProcessDocs
		// .stemming(ProcessDocs
		//
		// .tokenize("A new video celebrating the achievements of Linux during 2012 has been published online by the Linux Foundation. At 2:38 minutes long the video offers whirlwind highlights of a number of milestones reached by the open-source Unix-like operating system during the last 12 months. Amongst the items highlighted is the unstoppable march of Android, the launch of affordable Linux-powered Chromebooks from Google, and the success of the Linux Foundation itself in attracting more big-name members. Perhaps the only critical thing there is to say about the otherwise buoyant celebration,concerns the decision to frame it within a Firefox browser window running on, for all purposes appears to be, OS X! Since it’s the season of goodwill and all, we’ll pretend it’s a very convincing GTK theme design to show the versatility of Linux as a platform ;)"));
		// bayes.learn("Linux", linux1);
		//
		// ArrayList<String> linux2 = ProcessDocs
		// .stemming(ProcessDocs

		// .tokenize("highlights of a number of milestones reached by the open-source Unix-like operating system during the last 12 months. Amongst the items highlighted is the unstoppable march of Android, the launch of affordable Linux-powered Chromebooks from Google, and the success of the Linux Foundation itself in attracting more big-name members. Perhaps the only critical thing there is to say about the otherwise buoyant celebration,concerns the decision to frame it within a Firefox browser window running on, for all purposes appears to be, OS X! Since it’s the season of goodwill and all, we’ll pretend it’s a very convincing GTK theme design to show the versatility of Linux as a platform ;)"));
		// bayes.learn("Linux", linux2);
		//
		// ArrayList<String> unknownText1 = ProcessDocs
		// .stemming(ProcessDocs
		//
		// .tokenize("More users now been given access to the limited Steam for Linux beta, Valve have confirmed. Announcing the expansion of beta access on the Steam for Linux form, Valve’s Frank Crockett urged those who have applied previously to check their inbox for a redemption code. This expansion brings the total number of Steam for Linux users up to 21,542 Members. The company added an additional 5,000 users late last month. New Bug Forums In addition to wider access, Valve also announced several new sub-forums have been added for testers to report bugs in. These include sections for Valve Game bugs, Non-Valve games bug, to forums for bug reporting against specific graphics cards. Getting Close Another sign that the Steam for Linux beta is nearing its end listing of Linux System Requirements  games, the beta period for Serious Sam 3: BFE is now over – meaning players need to purchased the game inorder to keep playing. Saved game data remains unaffected. Serious Sam 3: BFE was available to play for free during the beta period. With that period now over, could the client be one step closer to a proper release? For a full list of Linux-friendly games available on Steam can be found hiding behind the button below."));
		// ArrayList<String> unknownText2 = ProcessDocs
		// .stemming(ProcessDocs
		//
		// .tokenize("Along with the recently covered design changes set to arrive in Ubuntu 13.04 came some changes to the look of the Unity launcher. These small updates change the shape and icon of the dash home button (colloquially termed “BFB”) and tweak the look of Unity’s launcher tiles. Now, if you’re one of those folks who just can’t wait to get the latest thing I’ve created a simple little guide for you to follow to install these new-look ‘assets’ in Ubuntu. How To Install New Unity Design Assests First you must download the modified assets, which I’ve packaged as into the following zip file. Download New Unity Launcher Design Assets Extract the contents of the archive and copy (as root) all the items in the “assets” folder to “/usr/share/unity/6″ replacing the existing assets. To do this via via the Terminal: cd <location of download>/raring-design-assets/assets sudo cp -r * /usr/share/unity/6 For the changes to take effect, you need only log out then back in."));
		//
		// ArrayList<String> unknownText3 = ProcessDocs
		// .stemming(ProcessDocs
		//
		// .tokenize("Some revolutionaries wear Guy Fawkes masks and talk about the 1 percent, and some revolutionaries wear suits and talk about policy thresholds. Chicago Fed president Charles Evans is one of the latter. A year ago Evans was the rare dovish dissenter at the Fed. He didn t think it was taking the unemployment half of its dual mandate seriously enough, so he proposed a new, eponymous rule for it to do better. He certainly wasn t the first Fed president to have his own ideas about monetary policy, but a funny thing happened on his way to heterodoxy -- his ideas quickly became the consensus. Now, just a year later, the Fed has fully embraced the so-called Evans rule by linking interest rates to the unemployment rate. Ain t no revolution like a monetary policy revolution. It s been a brave, old world for central banks the past four years. Short-term interest rates have been stuck at zero, which, outside of Japan, hasn t happened since the 1930s. It s what economists call a liquidity trap, and it means central banks can t stimulate growth like they normally do by cutting short-term interest rates. They can t cut below zero. This doesn t mean central banks are powerless, just that they have to try new things. These new things come in two varieties: promises and purchases. Central banks can pledge to hold short-term rates at zero even after the recovery accelerates, or they can buy long-term bonds to push down long-term rates; the former is what Paul Krugman calls  credibly promising to be irresponsible  and the latter is what we call  quantitative easing.  These sound like big changes from standard operating procedure, but the goal with both is the same as normal -- to reduce interest rates. It s just harder to do in a liquidity trap. Central banks have to increase expected inflation to lower inflation-adjusted rates when nominal, that is headline, rates are at zero. That s the point of these promises and purchases, and that s been the point of the Fed saying it expects to keep rates at zero through mid-2015 and buying $85 billion of mortgage and Treasury bonds a month. But as much as the Fed has done, there s still much more it can do -- like making its promises more explicit -- which it started to do with its latest policy move. Let s break it down into two pieces. (1) THE EVANS RULE The Fed s big announcement was that it won t raise rates before unemployment falls to 6.5 percent or inflation rises to 2.5 percent. Notice the word  before  here. The Fed won t automatically raise rates if unemployment or inflation hits one of these thresholds, but it won t do so until at least then. These are the exact thresholds Evans endorsed a few weeks ago, which are modest tweaks from his original thresholds last year of 7 percent unemployment and 3 percent inflation. Why all the fuss? This Evans rule doesn t seem to tell us anything the Fed wasn t already telling us. Just look at the Federal Open Market Committee s (FOMC) latest economic projections. The Fed doesn t think unemployment will fall below 6.5 percent until 2015 -- and it never thinks inflation will rise above 2 percent -- which implies rates will stay at zero until then. That s exactly what they were saying before. In truth, the Evans revolution is less a revolution itself and more a significant step on the way to the actual revolution -- NGDP targeting. We ll come back to this larger point, but first let s talk about why the Evans rule matters. Its virtue is it should make the Fed s decision-making more transparent, and that should affect people s expectations more. Contrast the Evans rule with what the Fed told us before -- say from October -- about how long zero interest rates would last. [The Fed] currently anticipates that exceptionally low levels for the federal runds rate are likely to be warranted at least through mid-2015. Is this a promise, maybe? That s how most people interpreted it, but it s not entirely clear. Read it again. The Fed was saying it expected the economy to be crummy enough to justify zero rates until mid-2015. But what if the economy picked up before then? Would the Fed raise rates then? Good question! The Evans rule clears this up a bit -- though not entirely -- but more importantly, it clears up whether the Fed has a 2 percent inflation target or ceiling. The Fed has been trying to answer that question for the past year. As Greg Ip of The Economist pointed out, the Fed rather significantly announced back in January that it thought the inflation and unemployment halves of its mandate were equally important, and changed its long-run inflation target from 1.5-2 percent to 2 percent. This was the Fed s way of saying it wouldn t necessarily raise rates if inflation crept over 2 percent as long as unemployment was still high and long-run inflation expectations didn t rise. In other words, the Fed s inflation target was not a 2 percent speed limit on the recovery. Or was it? Look at that table again. The Fed doesn t project inflation to go above 2 percent at all. That sure looks like a ceiling, still. The Evans rule tries to correct this -- though it would help if these latest projections were symmetrical around 2 percent -- by explicitly saying the Fed really, seriously will tolerate inflation as high as 2.5 percent in the short run. But there s plenty that still isn t clear. Like how and whether this will work. The Evans rule sounds straightforward enough, but these thresholds are not. The Fed left itself a bit of wiggle room. When it comes to unemployment, the Fed will look at other labor force measures like the participation rate. In other words, it will consider whether unemployment is falling because people are finding jobs or because people have given up on finding jobs. It gets murkier when it comes to inflation. The Fed will use its 1-2 year inflation forecasts for its threshold. Yes, forecasts. That gives the Fed some needed flexibility to ignore commodity surges, like oil in 2011, but it s not the clearest of guides. Remember, clarity is supposed to be the point. The idea is that the more markets understand the Fed s plans, the more the Fed s plans will shape markets  expectations. It s a bit like a Jedi mind trick. If people think things will be better in the future, then things will be better in the future, because that will get them spending and investing more now. Making us expect a better tomorrow might be the best the Fed can do today. Especially when you consider how short-lived the effects have been from the Fed s other unconventional easing. You can see that in the chart below that looks at market-based inflation expectations for 1, 2, and 10 year periods. Inflation expectations rise every time the Fed does something, and then retreat a few months later. (Note: These break-evens measure the differences between Treasury and TIPS, or inflation-protected, bonds. They aren t always reliable because TIPS are so lightly traded -- their nickname is  terribly illiquid pieces of,  well, we ll let you figure out the rest -- but they re a decent proxy. All data is from Bloomberg). Inflation expectations should tick up again, especially if we disarm the austerity bomb known as the fiscal cliff, but the overall pattern of peaks and valleys probably isn t going to go away yet. (2) ASSET PURCHASES The Fed s other (slightly less) big announcement was that it will continue its $85 billion of monthly asset purchases, albeit with a slight, um, twist. Here s what hasn t changed: the Fed will buy $45 billion of Treasury bonds and $40 billion of mortgage bonds each and every month until unemployment  substantially  improves. What has changed is how the Fed will pay for its $45 billion of Treasury bond purchases. Before, the Fed had been selling $45 billion of short-term bonds to pay for the $45 billion of long-term bonds it was buying, which went by the dramatic name of  Operation Twist . It was a way to lower long-term borrowing costs without printing money, back when more Fed members were worried about potential inflation. But with its supply of short-term Treasuries running, well, short, the Fed will turn Twist into QE. In other words, it will now print money to pay for the $45 billion of Treasuries it buys. The Fed s balance sheet will grow more than before, though its monthly flow of purchases remains the same. *** It s okay if you have that Animal Farm feeling. There s been a revolution, but nothing has changed. The Fed still thinks it s first rate hike will come in 2015-ish, and it s still buying $85 billion of bonds a month. This is a true fact. But it undersells the intellectual shift at the Fed. It s gone from mostly thinking about inflation to creating a framework to guide its thinking about inflation and unemployment. And it s done that in just a year. This framework, the Evans rule, is really just a quasi-NGDP target. It s not exactly the catchiest of phrases, but NGDP, or nominal GDP, targeting would be a real revolution in central banking. In plain English, it s the idea that central banks should target the size of the economy, unadjusted for inflation, and make up for any past over-or-undershooting. In theory, a flexible enough inflation target should mimic an NGDP target, which is why the Evans rule is so historic. It s an incremental step on the way to regime change at the Fed. That doesn t mean we should expect the Fed to move towards NGDP targeting anytime soon. A risk-averse institution like the Fed will want to see another country try it first -- and it might get that chance soon. Incoming Bank of England chief Mark Carney, who currently heads the Bank of Canada, endorsed the idea in a recent speech, and British Treasury officials indicated they might be open to it too -- which is significant because the British Treasury can unilaterally change its central bank s mandate. It might not be long till NGDP targeting comes to Britain, and from there, the world. If it does, you can be sure that Charles Evans will be figuring out how to make it work here. The Evans rule won t save the economy today, but it might tomorrow -- if it leads to better central banking. It should. It s a big conceptual step forward. And it s a big conceptual step forward we re going to need if Evan Soltas is right that we re likely to hit the zero bound more often in the future."));
		//
		// bayes.writeCountsToDb();
		//
		// System.out.println(bayes.classify(unknownText1).getCategory());
		// System.out.println(bayes.classify(unknownText2).getCategory());
		// System.out.println(bayes.classify(unknownText3).getCategory());
		//
		// Test code for TfIdf
		// TfIdf tf = new TfIdf(
		// "/home/wheatmai/Data/Downloads/Downloads_Temp/crawler_data/");
		// String word;
		// Double[] corpusdata;
		// for (Iterator<String> it = tf.allwords.keySet().iterator(); it
		// .hasNext();) {
		// word = it.next();
		// corpusdata = tf.allwords.get(word);
		// System.out
		// .println(word + " " + corpusdata[0] + " " + corpusdata[1]);
		// }
		//
		// tf.buildAllDocuments();
		//
		// String[] bwords;
		// String[] bdocs;
		// for (Iterator<String> it = tf.documents.keySet().iterator(); it
		// .hasNext();) {
		// word = it.next();
		// System.out.println(word);
		// System.out.println("------------------------------------------");
		// bwords = tf.documents.get(word).bestWordList(5);
		// bdocs = tf.similarDocuments(word);
		// for (int i = 0; i < 5; i++) {
		// System.out.print(bwords[i] + " ");
		// }
		// System.out.println();
		// for (int i = 0; i < bdocs.length; i++) {
		// System.out.println(bdocs[i] + " ");
		// }
		// System.out.println("\n\n");
		// }

		// Connection conn = null;
		//
		// String url = "jdbc:mysql://localhost:3306/search_engine_data";
		// String username = "root";
		// String password = "mc19-92mc";
		// try {
		// Class.forName("com.mysql.jdbc.Driver");
		// conn = DriverManager.getConnection(url, username, password);
		// } catch (ClassNotFoundException cnfex) {
		// cnfex.printStackTrace();
		// } catch (SQLException sqlex) {
		// sqlex.printStackTrace();
		// }
		//
		// int id = 0;
		//
		// String value = "news";
		//
		// try {
		// PreparedStatement statement = conn
		// .prepareStatement("select id from wordlist where word = ?");
		// statement.setString(1, value);
		// ResultSet resultSet = statement.executeQuery();
		//
		// while (resultSet.next()) {
		// id = resultSet.getInt(1);
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// System.out.println(id);

	}
}

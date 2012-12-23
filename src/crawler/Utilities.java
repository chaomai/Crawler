package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class Utilities {
	// TODO: change the regular expression when crawling other site
	// private static String pat = "http://arstechnica\\.com/.*";
	// private static String pat = "http://www\\.engadget\\.com/.*";
	// private static String pat = "http://www\\.bbc\\.com/.*";
	private static String pat = "http://www\\.theatlantic\\.com/.*";
	// private static String pat = "http://www\\.economist\\.com/.*";
	// private static String pat =
	// "http://127\\.0\\.1\\.1/~wheatmai/testing_site/.*";
	private static Pattern pattern = Pattern.compile(pat);

	/**
	 * Check whether a url is what I want.
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isCreateFile(String url) {
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}

	/**
	 * Save web page's contents into a file.
	 * 
	 * @param content
	 * @param url
	 */
	public static void createFile(String content, String url) {
		String dataFolder = "/home/wheatmai/Data/Downloads/Downloads_Temp/crawler_data/";
		String filePath = "";

		try {
			URL aUrl = new URL(url);
			String host = aUrl.getHost();
			String filename = aUrl.getFile();

			filePath = filePath + host;

			if (filename.substring(filename.length() - 1).endsWith("/")) {
				filePath = filePath
						+ filename.substring(0, filename.length() - 1);
				// filePath = filePath.replace('/', '_');
			} else {
				filePath = filePath + filename;
				// filePath = filePath.replace('/', '_');
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		filePath = filePath + ".txt";

		filePath = dataFolder + filePath;

		System.out.println(filePath);

		File file = new File(filePath);

		writeFile(file.getAbsoluteFile().toString(), content);
	}

	/**
	 * If the url in the web page start with '/', than add the host url before
	 * the '/'.
	 * 
	 * @param href
	 * @return
	 */
	public static String getFullHref(String href) {
		String finalHrefString = "";

		if (href.startsWith("http://")) {
			finalHrefString = href;
		} else {
			if (href.startsWith("/")) {
				// TODO: change the finalHrefString when crawling other site
				// finalHrefString = "http://arstechnica.com" + href;
				// finalHrefString = "http://www.engadget.com" + href;
				// finalHrefString = "http://www.bbc.com" + href;
				finalHrefString = "http://www.theatlantic.com" + href;
				// finalHrefString = "http://www.economist.com" + href;
				// finalHrefString = "http://127.0.1.1/~wheatmai/testing_site"
				// + href;
			}
		}
		return finalHrefString;
	}

	/**
	 * Use the boilerpipe to get the main textual content of a web page.
	 * 
	 * @param contentString
	 * @return
	 */
	public static String getContent(String contentString) {
		String textString = "";

		try {
			textString = ArticleExtractor.INSTANCE.getText(contentString);
		} catch (BoilerpipeProcessingException e) {
			e.printStackTrace();
		}

		return textString;
	}

	/**
	 * Get title from content.
	 * 
	 * @param contentString
	 * @return
	 */
	public static String getTitle(String contentString) {
		int sign = contentString.indexOf("<title");
		if (!contentString.contains("<title")) {
			return "No Title";
		} else {
			String signContent = contentString.substring(sign);

			int start = signContent.indexOf(">");
			int end = signContent.indexOf("</title>");

			return signContent.substring(start + 1, end);
		}
	}

	/**
	 * Write content to file.
	 * 
	 * @param fileName
	 * @param contentString
	 */
	public static void writeFile(String fileName, String contentString) {
		File file = new File(fileName);

		BufferedWriter writer = null;

		File filedir = file.getParentFile();

		if (!filedir.exists()) {
			filedir.mkdirs();
		}

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
}
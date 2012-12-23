package trainer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class Utilities {
	// TODO: change the regular expression when crawling other site
	// private static String pat =
	// "http://www\\.theatlantic\\.com/politics/.*";
	// private static String pat =
	// "http://www\\.theatlantic\\.com/business/.*";
	// private static String pat =
	// "http://www\\.theatlantic\\.com/technology/.*";
	// private static String pat =
	// "http://www\\.theatlantic\\.com/national/.*";
	// private static String pat =
	// "http://www\\.theatlantic\\.com/international/.*";
	// private static String pat = "http://www\\.theatlantic\\.com/health/.*";
	private static String pat = "http://www\\.theatlantic\\.com/entertainment/.*";

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
		String[] elementsStrings = url.split("/");
		StringBuffer pathBuffer = new StringBuffer();
		File file = null;

		// About the element index
		// element[1 to elementsStrings.length-1] for the path
		// element[elementsStrings.length] for the file name
		// The elementsStrings[0] is "http:", so I ignore it and start from 1
		for (int i = 1; i < elementsStrings.length; i++) {
			// build file path
			if (i != elementsStrings.length - 1) {
				pathBuffer.append(elementsStrings[i]);

				file = new File(
						"/home/wheatmai/Data/Downloads/Downloads_Temp/crawler_data/"
								+ pathBuffer.toString());
			}

			// write to file
			// Why I put all docs in one folder:
			// It is not easy to distinguish the article and the article list.
			// For example, a link without ".html" might be an article or
			// article list. So It's not easy to decide whether
			// create a folder.
			if (i == elementsStrings.length - 1) {
				file = new File(
						"/home/wheatmai/Data/Downloads/Downloads_Temp/crawler_data/"
								+ pathBuffer.toString()
								+ elementsStrings[elementsStrings.length - 1]);

				System.out.println(file.getAbsoluteFile().toString());

				writeFile(file.getAbsoluteFile().toString(), content);
			}
		}
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
				finalHrefString = "http://www.theatlantic.com" + href;
			}
		}
		return finalHrefString;
	}

	// I decide to use the boilerpipe to get the main textual content of a web
	// page. Because different sites have different templates. And their main
	// textual contents are putted in <p>, <table>, <dir> and so on(Harder than
	// what I thought). It's not easy to get the content by myself in such a
	// short time.
	//
	// public static String getContent(String content)
	// {
	// int sign = content.indexOf("<p");
	// String signContent = content.substring(sign);
	//
	// int start = signContent.indexOf(">");
	// int end = signContent.indexOf("</p>");
	//
	// return signContent.substring(start + 1, end);
	// }

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
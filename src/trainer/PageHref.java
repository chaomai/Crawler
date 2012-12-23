package trainer;

import crawler.UnVisitedUrlQueue;
import crawler.VisitedUrlQueue;

public class PageHref {
	/**
	 * Get hyperlinks from web page content and put them into UnVisitedUrlQueue.
	 * 
	 * @param contentString
	 */
	public static void getHref(String contentString) {
		// get links in web pages manually
		// no JSOUP
		String[] contentsStrings = contentString.split("<a href=\"");

		for (int i = 1; i < contentsStrings.length; i++) {
			int endHref = contentsStrings[i].indexOf("\"");
			String aHrefString = Utilities.getFullHref(contentsStrings[i]
					.substring(0, endHref));

			if (null != aHrefString) {
				String href = Utilities.getFullHref(aHrefString);
				if (!UnVisitedUrlQueue.isContains(href)
						&& (!VisitedUrlQueue.isContains(href))) {
					UnVisitedUrlQueue.addElement(href);
				}
			}
		}
	}
}

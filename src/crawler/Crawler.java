package crawler;

public class Crawler {
	public static void main(String[] args) {
		// String urlString = "http://arstechnica.com/";
		// String urlString = "http://www.engadget.com/";
		// String urlString = "http://www.bbc.com/";
		String urlString = "http://www.theatlantic.com/";
		// String urlString = "http://www.economist.com/";
		// String urlString = "http://127.0.1.1/~wheatmai/testing_site/";
		UnVisitedUrlQueue.addElement(urlString);
		HandleUrl[] urls = new HandleUrl[4];

		System.out.println("start crawling");
		for (int i = 0; i < 4; i++) {
			urls[i] = new HandleUrl();
			new Thread(urls[i]).start();
		}
	}
}

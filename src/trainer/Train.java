package trainer;

import crawler.UnVisitedUrlQueue;

public class Train {
	public static void main(String[] args) {
		// String urlString =
		// "http://www.theatlantic.com/politics/";
		// String urlString =
		// "http://www.theatlantic.com/business/";
		// String urlString =
		// "http://www.theatlantic.com/technology/";
		// String urlString =
		// "http://www.theatlantic.com/national/";
		// String urlString =
		// "http://www.theatlantic.com/international/";
		// String urlString = "http://www.theatlantic.com/health/";
		String urlString = "http://www.theatlantic.com/entertainment/";

		UnVisitedUrlQueue.addElement(urlString);
		HandleUrl[] urls = new HandleUrl[1];

		System.out.println("start training");
		for (int i = 0; i < 1; i++) {
			urls[i] = new HandleUrl();
			new Thread(urls[i]).start();
		}
	}
}

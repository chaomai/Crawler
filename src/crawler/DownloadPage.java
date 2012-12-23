package crawler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import classifier.BayesClassifier;
import classifier.Classifier;

public class DownloadPage {
	/**
	 * Download contents from url.
	 * 
	 * @param url
	 * @return A string contains original content.
	 */
	public static String getContentFromUrl(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		String content = null;
		HttpResponse response;

		get.setHeader("User-Agent", "Mozilla/5.0");

		try {
			if (Utilities.isCreateFile(url)) {
				response = client.execute(get);
				HttpEntity entity = response.getEntity();

				ContentType contentType = ContentType.getOrDefault(entity);
				Charset charSet = contentType.getCharset();

				if (null != entity) {
					content = EntityUtils.toString(entity, charSet);

					ArrayList<String> stemmedString = ProcessDocs
							.stemming(ProcessDocs.tokenize(Utilities
									.getContent(content)));

					final Classifier<String, String> bayes = new BayesClassifier<String, String>();

					// If the url is what I want, add it to the VisitedUrlQueue
					VisitedUrlQueue.addElement(url);

					// write tokenized content to file
					Utilities.createFile(stemmedString.toString(), url);

					// add to words matrix
					WordsMatrix.addContent(stemmedString.toString(), url,
							Utilities.getTitle(content),
							bayes.classify(stemmedString).getCategory());

					showStatistic(url);
					checkStop(12);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}

		return content;
	}

	/**
	 * Show statistic information.
	 * 
	 * @param url
	 */
	private static void showStatistic(String url) {
		System.out.println(url);
		System.out.println("UnVisitedUrlQueue: " + UnVisitedUrlQueue.size());
		System.out.println("VisitedUrlQueue" + VisitedUrlQueue.size());
		System.out.println("-----------------------------------------------");
	}

	/**
	 * Set maxium page count and check whether stop crawling.
	 * 
	 * @param maxPages
	 */
	private static void checkStop(int maxPages) {
		if (VisitedUrlQueue.size() == maxPages) {
			RecordData recordData = new RecordData();
			recordData.record();

			System.exit(1);
		}
	}
}

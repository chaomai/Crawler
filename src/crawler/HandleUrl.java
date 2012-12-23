package crawler;

public class HandleUrl implements Runnable {
	/**
	 * Handle urls. Check whether download and save web pages.
	 * 
	 * @param url
	 */
	private void Handle(String url) {
		try {
			if (Utilities.isCreateFile(url)) {
				PageHref.getHref(DownloadPage.getContentFromUrl(url));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (!UnVisitedUrlQueue.isEmpty()) {
			try {
				Handle(UnVisitedUrlQueue.outElement());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

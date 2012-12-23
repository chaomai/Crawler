package trainer;

import crawler.UnVisitedUrlQueue;

public class HandleUrl implements Runnable {
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

package crawler;

import java.util.HashSet;

public class VisitedUrlQueue {
	private static HashSet<String> visitedUrlQueueSet = new HashSet<String>();
	
	public synchronized static void addElement(String url) {
		visitedUrlQueueSet.add(url);
	}
	
	public synchronized static boolean isContains(String url) {
		return visitedUrlQueueSet.contains(url);
	}
	
	public synchronized static int size() {
		return visitedUrlQueueSet.size();
	}
}

package crawler;

import java.util.LinkedList;

public class UnVisitedUrlQueue {
	private static LinkedList<String> urlLinkedList = new LinkedList<String>();
	
	public synchronized static void addElement(String url) {
		urlLinkedList.add(url);
	}
	
	public synchronized static String outElement() {
		return urlLinkedList.removeFirst();
	}
	
	public synchronized static boolean isEmpty() {
		return urlLinkedList.isEmpty();
	}
	
	public static int size() {
		return urlLinkedList.size();
	}
	
	public static boolean isContains(String url) {
		return urlLinkedList.contains(url);
	}
}

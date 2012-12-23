package crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RecordData {
	/**
	 * A database connection.
	 */
	private Connection conn = null;

	/**
	 * Set up database connection.
	 */
	public RecordData() {
		this.connectDb();
	}

	/**
	 * Connect to database.
	 */
	public void connectDb() {
		String url = "jdbc:mysql://localhost:3306/search_engine_data";
		String username = "root";
		String password = "mc19-92mc";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException cnfex) {
			cnfex.printStackTrace();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	/**
	 * Record data to database.
	 */
	public void record() {
		addToWordlist();
		addToUrllist();
		addToWordPosition();
	}

	/**
	 * Add AllWords to wordlist table.
	 */
	private void addToWordlist() {
		HashMap<String, Integer> allWords = WordsMatrix.getRawAllWords();
		Iterator<?> wordsIterator = allWords.entrySet().iterator();

		while (wordsIterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> wordEntry = (Map.Entry<String, Integer>) wordsIterator
					.next();
			String word = wordEntry.getKey();

			try {
				PreparedStatement statement = conn
						.prepareStatement("insert into wordlist values (null, ?)");
				statement.setString(1, word);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add all urls and eache url's category to urllist table.
	 */
	private void addToUrllist() {
		ArrayList<String> allUrls = WordsMatrix.getRawArticleUrlStrings();
		Iterator<String> allUrlsIterator = allUrls.iterator();

		ArrayList<String> articleCategory = WordsMatrix
				.getRawArticleCategoryArrayList();
		Iterator<String> articleCatagoryIterator = articleCategory.iterator();

		ArrayList<String> articleTitle = WordsMatrix
				.getRawArticleTitleStrings();
		Iterator<String> articleTitleIterator = articleTitle.iterator();

		while (allUrlsIterator.hasNext()) {
			String url = allUrlsIterator.next();
			String category = articleCatagoryIterator.next();
			String title = articleTitleIterator.next();

			try {
				PreparedStatement statement = conn
						.prepareStatement("insert into urllist values (null, ?, ?, ?)");
				statement.setString(1, url);
				statement.setString(2, title);
				statement.setString(3, category);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add all words position to databases table.
	 */
	private void addToWordPosition() {
		HashMap<String, ArrayList<Integer>> allWordsDocs = WordsMatrix
				.getRawAllWordsDocs();
		Iterator<?> wordsIterator = allWordsDocs.entrySet().iterator();

		while (wordsIterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, ArrayList<Integer>> wordEntry = (Map.Entry<String, ArrayList<Integer>>) wordsIterator
					.next();
			String word = wordEntry.getKey();
			int wordid = getWordId(word);

			Iterator<Integer> posIterator = allWordsDocs.get(word).iterator();

			String location = "";

			while (posIterator.hasNext()) {
				location = location + posIterator.next().toString() + " ";
			}

			try {
				PreparedStatement statement = conn
						.prepareStatement("insert into wordlocation values (?, ?)");
				statement.setInt(1, wordid);
				statement.setString(2, location);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get id from wordlist table.
	 * 
	 * @param value
	 * @return
	 */
	private int getWordId(String value) {
		int id = 0;
		try {
			PreparedStatement statement = conn
					.prepareStatement("select id from wordlist where word = ?");
			statement.setString(1, value);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}
}

package crawler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.jblas.DoubleMatrix;

public class WordsMatrix {

	/**
	 * (String, Integer): (word, count). all words frequency.
	 */
	private static HashMap<String, Integer> AllWordsMap = new HashMap<String, Integer>();

	/**
	 * (String, ArrayList): (word, position) all words position.
	 */
	private static HashMap<String, ArrayList<Integer>> AllWordsDocsMap = new HashMap<String, ArrayList<Integer>>();

	/**
	 * (String): (url).
	 */
	private static ArrayList<String> ArticleUrlStrings = new ArrayList<String>();

	/**
	 * (String): (title).
	 */
	private static ArrayList<String> ArticleTitleStrings = new ArrayList<String>();

	/**
	 * Each article's words.
	 */
	private static ArrayList<HashMap<String, Integer>> ArticleWordsArrayList = new ArrayList<HashMap<String, Integer>>();

	/**
	 * Each article's category.
	 */
	private static ArrayList<String> ArticleCategoryArrayList = new ArrayList<String>();

	// each article's summary
	// private static ArrayList<String> ArticleSummaryArrayList = new
	// ArrayList<String>();

	/**
	 * Artitles count.
	 */
	private static int articlecount = 0;

	/**
	 * Add content.
	 * 
	 * @param contentString
	 * @param url
	 * @param title
	 * @param category
	 */
	public synchronized static void addContent(String contentString,
			String url, String title, String category) {
		StringTokenizer delimiterTokenizer = new StringTokenizer(contentString,
				"[], ");

		// if all contents are stop-words, the contentString will be "[]". Then
		// the delimiterTokenizer's token is empey.
		if (delimiterTokenizer.countTokens() != 0) {
			while (delimiterTokenizer.hasMoreElements()) {
				String wordkeyString = delimiterTokenizer.nextToken();

				// add url to ArticleUrlStrings
				if (ArticleUrlStrings.size() == articlecount) {
					ArticleUrlStrings.add(url);
				}

				// add title to ArticleTitleStrings
				if (ArticleTitleStrings.size() == articlecount) {
					ArticleTitleStrings.add(title);
				}

				// add words to AllWordsMap
				if (AllWordsMap.get(wordkeyString) != null) {
					int value1 = ((Integer) AllWordsMap.get(wordkeyString))
							.intValue();
					value1 = value1 + 1;
					AllWordsMap.put(wordkeyString, new Integer(value1));
				} else {
					AllWordsMap.put(wordkeyString, new Integer(1));
				}

				// add words to AllWordsDocsMap
				// TODO: Save wordId, instead of word itselt.
				if (AllWordsDocsMap.get(wordkeyString) != null) {
					if (!AllWordsDocsMap.get(wordkeyString).contains(
							articlecount + 1)) {
						AllWordsDocsMap.get(wordkeyString)
								.add(articlecount + 1);
					}
				} else {
					ArrayList<Integer> wordIntegers = new ArrayList<Integer>();
					wordIntegers.add(articlecount + 1);
					AllWordsDocsMap.put(wordkeyString, wordIntegers);
				}

				// add words to ArticleWordsArrayList
				if (ArticleWordsArrayList.size() == articlecount) {
					HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
					wordMap.put(wordkeyString, new Integer(1));
					ArticleWordsArrayList.add(wordMap);
				} else {
					if (ArticleWordsArrayList.get(articlecount).get(
							wordkeyString) != null) {
						int value1 = (Integer) ArticleWordsArrayList.get(
								articlecount).get(wordkeyString);
						value1 = value1 + 1;
						ArticleWordsArrayList.get(articlecount).put(
								wordkeyString, new Integer(value1));
					} else {
						ArticleWordsArrayList.get(articlecount).put(
								wordkeyString, new Integer(1));
					}
				}

				// add category to ArticleCategoryArrayList
				if (ArticleCategoryArrayList.size() == articlecount) {
					ArticleCategoryArrayList.add(category);
				}
			}

			articlecount = articlecount + 1;
		}
	}

	/**
	 * Get articles' urls.
	 * 
	 * @return
	 */
	public static String getArticleUrls() {
		Iterator<String> articleUrlIterator = ArticleUrlStrings.iterator();

		String resultString = articleUrlIterator.next().toString() + '\n';

		while (articleUrlIterator.hasNext()) {
			resultString = resultString + articleUrlIterator.next().toString()
					+ '\n';
		}

		return resultString;
	}

	/**
	 * Get all articles' words.
	 * 
	 * @return
	 */
	public static String getAllWords() {
		Map<String, Integer> wordMap = new TreeMap<String, Integer>(AllWordsMap);
		Iterator<?> wordIterator = wordMap.entrySet().iterator();

		String resultString = "";

		while (wordIterator.hasNext()) {
			resultString = resultString + wordIterator.next().toString() + '\n';
		}

		return resultString;
	}

	/**
	 * Get each article's words.
	 * 
	 * @return
	 */
	public static String getArticleWords() {
		Iterator<HashMap<String, Integer>> artileWordsIterator = ArticleWordsArrayList
				.iterator();

		String resultString = "";

		while (artileWordsIterator.hasNext()) {
			Map<String, Integer> wordMap = new TreeMap<String, Integer>(
					artileWordsIterator.next());
			Iterator<?> wordIterator = wordMap.entrySet().iterator();

			resultString = resultString + wordIterator.next().toString() + ' ';

			while (wordIterator.hasNext()) {
				resultString = resultString + wordIterator.next().toString()
						+ ' ';
			}
			resultString = resultString + '\n' + '\n' + '\n';
		}

		return resultString;
	}

	/**
	 * Get each word's location.
	 * 
	 * @return
	 */
	public static String getAllWordsDocs() {
		Iterator<?> wordsIterator = AllWordsDocsMap.entrySet().iterator();
		String resultString = "";

		while (wordsIterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, ArrayList<Integer>> wordEntry = (Map.Entry<String, ArrayList<Integer>>) wordsIterator
					.next();
			String word = wordEntry.getKey();
			resultString = resultString + word + ' ';

			Iterator<Integer> posIterator = AllWordsDocsMap.get(word)
					.iterator();

			while (posIterator.hasNext()) {
				resultString = resultString + posIterator.next().toString()
						+ ' ';
			}
			resultString = resultString + '\n';
		}

		return resultString;
	}

	public static HashMap<String, Integer> getRawAllWords() {
		return AllWordsMap;
	}

	public static HashMap<String, ArrayList<Integer>> getRawAllWordsDocs() {
		return AllWordsDocsMap;
	}

	public static ArrayList<String> getRawArticleUrlStrings() {
		return ArticleUrlStrings;
	}

	public static ArrayList<String> getRawArticleTitleStrings() {
		return ArticleTitleStrings;
	}

	public static ArrayList<HashMap<String, Integer>> geRawtArticleWordsArrayList() {
		return ArticleWordsArrayList;
	}

	public static ArrayList<String> getRawArticleCategoryArrayList() {
		return ArticleCategoryArrayList;
	}

	/**
	 * Get counts for all words in all artiles and generate a matrix.
	 * 
	 * @return
	 */
	private static DoubleMatrix makeWordMatrix() {
		double wordsArray[][];
		int articleCount = 0;
		int wordCount = 0;

		wordsArray = new double[ArticleUrlStrings.size()][AllWordsMap.size()];

		Iterator<?> articleUrlIterator = ArticleUrlStrings.iterator();

		while (articleUrlIterator.hasNext()) {
			// If I don't use articleTitleIterator.next(), the while will be
			// infinite.
			articleUrlIterator.next();

			Iterator<?> wordsIterator = AllWordsMap.entrySet().iterator();
			wordCount = 0;

			while (wordsIterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, Integer> wordEntry = (Map.Entry<String, Integer>) wordsIterator
						.next();
				String word = wordEntry.getKey();

				if (ArticleWordsArrayList.get(articleCount).get(word) == null) {
					wordsArray[articleCount][wordCount] = 0;
				} else {
					wordsArray[articleCount][wordCount] = ArticleWordsArrayList
							.get(articleCount).get(word);
				}

				wordCount = wordCount + 1;
			}

			articleCount = articleCount + 1;
		}

		return new DoubleMatrix(wordsArray);
	}

	/**
	 * Cost function.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static double difCost(DoubleMatrix a, DoubleMatrix b) {
		double dif = 0;
		for (int i = 0; i < a.rows; i++) {
			for (int j = 0; j < a.columns; j++) {
				dif = dif + Math.pow(a.get(i, j) - b.get(i, j), 2);
			}
		}

		return dif;
	}

	/**
	 * The Weights Matrix. The values state how much each feature applies to
	 * each article.
	 */
	private static DoubleMatrix w;

	/**
	 * The Fatures Matirx. The values indicate how important a word is to a
	 * feature.
	 */
	private static DoubleMatrix h;

	/**
	 * Genate the Fatures Matirx and the Weights Matrix I use the Euclidean
	 * distance as the cost function and the Multiplicative update rules to
	 * update the Fatures Matirx and the Weights Matrix. Following
	 * http://hebb.mit.edu/people/seung/papers/nmfconverge.pdf for more detials.
	 * The value of pc should be smaller than v.rows and v.columns, so that w
	 * and h are smaller than v.
	 * 
	 * @param pc
	 * @param iterateNum
	 */
	private static void factorize(int pc, int iterateNum) {
		DoubleMatrix v = makeWordMatrix();

		// use random values to initialize the Fatures Matirx and the Weights
		// Matrix
		w = DoubleMatrix.rand(v.rows, pc);
		h = DoubleMatrix.rand(pc, v.columns);

		double cost = 0;

		for (int i = 0; i < iterateNum; i++) {
			cost = difCost(v, w.mmul(h));

			// If the cost is NaN here, that means the cost is too small to
			// express.
			if (Double.isNaN(cost)) {
				cost = 0;
			}

			if (i % 10 == 0) {
				System.out.println("iterated time: " + i + " cost: " + cost);
			}

			if (cost == 0) {
				break;
			}

			// The Euclidean distance ||V-WH|| is nonincreasing under the update
			// rules.
			h = h.mul((w.transpose().mmul(v).div(w.transpose().mmul(w).mmul(h))));
			w = w.mul((v.mmul(h.transpose())).div(w.mmul(h).mmul(h.transpose())));
		}

		System.out.println("Value of the cost function: " + cost + '\n');
	}

	/**
	 * Each feature's words.
	 */
	private static ArrayList<Map<String, Double>> allFeatureWords = new ArrayList<Map<String, Double>>();

	/**
	 * Each article's feature.
	 */
	private static ArrayList<Map<Integer, Double>> allArticleFeatures = new ArrayList<Map<Integer, Double>>();

	/**
	 * Generate features and save them into allFeatureWords and
	 * allArticleFeatures.
	 */
	private static void generateFeatures() {
		// TODO: change the valuse of pc
		factorize(50, 500);

		// words to feature
		for (int i = 0; i < h.rows; i++) {
			// (String, Double): (words, values)
			HashMap<String, Double> featureWordHashMap = new HashMap<String, Double>();
			String tmpString;

			for (int j = 0; j < h.columns; j++) {
				tmpString = getWordById(j);
				featureWordHashMap.put(tmpString, h.get(i, j));
			}

			// sort the featureWordHashMap on the values
			ValueComparator1 bvc = new ValueComparator1(featureWordHashMap);
			TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(
					bvc);
			sorted_map.putAll(featureWordHashMap);

			allFeatureWords.add(sorted_map);
		}

		System.out.println("allFeatureWords is generated");

		// features to article
		for (int i = 0; i < w.rows; i++) {
			// (Integer, Double): (features, values)
			HashMap<Integer, Double> articleFeatureHashMap = new HashMap<Integer, Double>();

			for (int j = 0; j < w.columns; j++) {
				articleFeatureHashMap.put(j, w.get(i, j));
			}

			// sort the featureArticleHashMap on the values
			ValueComparator2 bvc = new ValueComparator2(articleFeatureHashMap);
			TreeMap<Integer, Double> sorted_map = new TreeMap<Integer, Double>(
					bvc);
			sorted_map.putAll(articleFeatureHashMap);

			allArticleFeatures.add(sorted_map);
		}

		System.out.println("articleFeatureHashMap is generated");
	}

	/**
	 * Get each article's features.
	 * 
	 * @return
	 */
	public static String showArticles() {
		generateFeatures();
		String resultString = "";

		for (int i = 0; i < ArticleTitleStrings.size(); i++) {
			resultString = resultString + ArticleTitleStrings.get(i) + '\n';
			resultString = resultString + ArticleUrlStrings.get(i) + '\n';

			Iterator<?> articleIterator = allArticleFeatures.get(i).entrySet()
					.iterator();
			int count = 0;

			while (articleIterator.hasNext()) {
				// TODO: output features count
				if (count == 10) {
					break;
				}
				@SuppressWarnings("unchecked")
				Map.Entry<Integer, Double> featureEntry = (Map.Entry<Integer, Double>) articleIterator
						.next();

				resultString = resultString
						+ featureEntry.getValue().toString() + ' '
						+ getFeatureWords(featureEntry.getKey()) + '\n';

				count = count + 1;
			}
			resultString = resultString + '\n' + '\n' + '\n';
		}

		return resultString;
	}

	/**
	 * Get word's id in the AllWordsMap.
	 * 
	 * @param id
	 * @return
	 */
	private static String getWordById(int id) {
		Iterator<?> wordIterator = AllWordsMap.entrySet().iterator();

		String resultString = "";
		int count = 0;

		while (wordIterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> wordEntry = (Map.Entry<String, Integer>) wordIterator
					.next();
			resultString = wordEntry.getKey();
			if (count == id) {
				break;
			}
			count = count + 1;
		}

		return resultString;
	}

	/**
	 * Get each feature's words.
	 * 
	 * @param id
	 * @return
	 */
	private static String getFeatureWords(int id) {
		Iterator<?> featureIterator = allFeatureWords.get(id).entrySet()
				.iterator();

		String resultString = "";
		int count = 0;

		while (featureIterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> wordEntry = (Map.Entry<String, Integer>) featureIterator
					.next();
			resultString = resultString + wordEntry.getKey() + ' ';
			// TODO: output words count
			if (count == 20) {
				break;
			}
			count = count + 1;
		}

		return resultString;
	}
}

class ValueComparator1 implements Comparator<String> {

	Map<String, Double> base;

	public ValueComparator1(Map<String, Double> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}

class ValueComparator2 implements Comparator<Integer> {

	Map<Integer, Double> base;

	public ValueComparator2(Map<Integer, Double> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(Integer a, Integer b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}
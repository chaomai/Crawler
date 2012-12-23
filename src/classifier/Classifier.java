package classifier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public abstract class Classifier<T, K> implements IFeatureProbability<T, K> {

	/**
	 * Initial capacity of category dictionaries.
	 */
	private static final int INITIAL_CATEGORY_DICTIONARY_CAPACITY = 16;

	/**
	 * Initial capacity of feature dictionaries. It should be quite big, because
	 * the features will quickly outnumber the categories.
	 */
	private static final int INITIAL_FEATURE_DICTIONARY_CAPACITY = 32;

	/**
	 * A dictionary mapping features to their number of occurrences in each
	 * known category.
	 */
	private Dictionary<K, Dictionary<T, Integer>> featureCountPerCategory;

	/**
	 * A dictionary mapping features to their number of occurrences.
	 */
	private Dictionary<T, Integer> totalFeatureCount;

	/**
	 * A dictionary mapping categories to their number of occurrences.
	 */
	private Dictionary<K, Integer> totalCategoryCount;

	/**
	 * A database connection.
	 */
	private Connection conn = null;

	/**
	 * Constructs a new classifier without any trained knowledge.
	 */
	public Classifier() {
		this.reset();
		this.setDb();
	}

	/**
	 * Resets the <i>learned</i> feature and category counts.
	 */
	public void reset() {
		this.featureCountPerCategory = new Hashtable<K, Dictionary<T, Integer>>(
				Classifier.INITIAL_CATEGORY_DICTIONARY_CAPACITY);
		this.totalFeatureCount = new Hashtable<T, Integer>(
				Classifier.INITIAL_FEATURE_DICTIONARY_CAPACITY);
		this.totalCategoryCount = new Hashtable<K, Integer>(
				Classifier.INITIAL_CATEGORY_DICTIONARY_CAPACITY);
	}

	/**
	 * Set database connection and get the <i>learned</i> feature and category
	 * counts from database.
	 */
	public void setDb() {
		connectDb();
		getDbCategories();
		getDbFeatures();
		getDbfeatureCountPerCategory();
	}

	/**
	 * Connect to database.
	 */
	public void connectDb() {
		String url = "jdbc:mysql://localhost:3306/bayes_trainning_data";
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
	 * Get category count from database.
	 */
	@SuppressWarnings("unchecked")
	public void getDbCategories() {
		try {
			PreparedStatement statement = conn
					.prepareStatement("select * from category_count");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String category = resultSet.getString(1);
				Integer count = resultSet.getInt(2);
				this.totalCategoryCount.put((K) category, count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get features count from database.
	 */
	@SuppressWarnings("unchecked")
	public void getDbFeatures() {
		try {
			PreparedStatement statement = conn
					.prepareStatement("select * from feature_count");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String feature = resultSet.getString(1);
				Integer count = resultSet.getInt(2);
				this.totalFeatureCount.put((T) feature, count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get each category features count from database.
	 */
	@SuppressWarnings("unchecked")
	public void getDbfeatureCountPerCategory() {
		try {
			PreparedStatement statement = conn
					.prepareStatement("select * from feature_count_per_category");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String feature = resultSet.getString(1);
				String category = resultSet.getString(2);
				Integer count = resultSet.getInt(3);

				Dictionary<T, Integer> features = this.featureCountPerCategory
						.get(category);
				if (features == null) {
					this.featureCountPerCategory
							.put((K) category,
									new Hashtable<T, Integer>(
											Classifier.INITIAL_FEATURE_DICTIONARY_CAPACITY));
					features = this.featureCountPerCategory.get(category);
				}

				Integer countTmp = features.get(feature);
				if (countTmp == null) {
					features.put((T) feature, 0);
					countTmp = features.get(feature);
				}
				features.put((T) feature, count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write totalFeatureCount, totalCategoryCount and featureCountPerCategory
	 * to database after classifying is terminated.
	 */
	public void writeCountsToDb() {
		writeFeatureCountsToDb();
		writeCategoryCountsToDb();
		writefeatureCountPerCategoryToDb();
	}

	/**
	 * Write totalFeatureCount to database after classifying is terminated.
	 */
	@SuppressWarnings("unchecked")
	public void writeFeatureCountsToDb() {
		Enumeration<String> featureKeys = (Enumeration<String>) totalFeatureCount
				.keys();
		String feature = null;
		while (featureKeys.hasMoreElements()) {
			feature = (String) featureKeys.nextElement();
			Integer count = totalFeatureCount.get(feature);

			try {
				PreparedStatement statement = conn
						.prepareStatement("insert into feature_count values (?, ?)");
				statement.setString(1, feature);
				statement.setInt(2, count);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Write totalCategoryCount to database after classifying is terminated.
	 */
	@SuppressWarnings("unchecked")
	public void writeCategoryCountsToDb() {
		Enumeration<String> categoryKeys = (Enumeration<String>) totalCategoryCount
				.keys();
		String category = null;
		while (categoryKeys.hasMoreElements()) {
			category = (String) categoryKeys.nextElement();
			Integer count = totalCategoryCount.get(category);

			try {
				PreparedStatement statement = conn
						.prepareStatement("insert into category_count values (?, ?)");
				statement.setString(1, category);
				statement.setInt(2, count);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Write featureCountPerCategory to database after classifying is
	 * terminated. private Dictionary<K, Dictionary<T, Integer>>
	 * featureCountPerCategory;
	 */
	@SuppressWarnings("unchecked")
	public void writefeatureCountPerCategoryToDb() {
		Enumeration<String> perCategoryKeys = (Enumeration<String>) featureCountPerCategory
				.keys();
		String category = null;
		while (perCategoryKeys.hasMoreElements()) {
			category = (String) perCategoryKeys.nextElement();
			Dictionary<T, Integer> features = this.featureCountPerCategory
					.get(category);

			Enumeration<String> featureKeys = (Enumeration<String>) features
					.keys();
			String feature = null;
			while (featureKeys.hasMoreElements()) {
				feature = (String) featureKeys.nextElement();
				Integer count = totalFeatureCount.get(feature);

				try {
					PreparedStatement statement = conn
							.prepareStatement("insert into feature_count_per_category values (?, ?, ?)");
					statement.setString(1, feature);
					statement.setString(2, category);
					statement.setInt(3, count);
					statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Returns a <code>Set</code> of features the classifier knows about.
	 * 
	 * @return The <code>Set</code> of features the classifier knows about.
	 */
	public Set<T> getFeatures() {
		return ((Hashtable<T, Integer>) this.totalFeatureCount).keySet();
	}

	/**
	 * Returns a <code>Set</code> of categories the classifier knows about.
	 * 
	 * @return The <code>Set</code> of categories the classifier knows about.
	 */
	public Set<K> getCategories() {
		return ((Hashtable<K, Integer>) this.totalCategoryCount).keySet();
	}

	/**
	 * Retrieves the total number of categories the classifier knows about.
	 * 
	 * @return The total category count.
	 */
	public int getCategoriesTotal() {
		int toReturn = 0;
		for (Enumeration<Integer> e = this.totalCategoryCount.elements(); e
				.hasMoreElements();) {
			toReturn += e.nextElement();
		}
		return toReturn;
	}

	/**
	 * Increments the count of a given feature in the given category. This is
	 * equal to telling the classifier, that this feature has occurred in this
	 * category.
	 * 
	 * @param feature
	 *            The feature, which count to increase.
	 * @param category
	 *            The category the feature occurred in.
	 */
	public void incrementFeature(T feature, K category) {
		Dictionary<T, Integer> features = this.featureCountPerCategory
				.get(category);
		if (features == null) {
			this.featureCountPerCategory.put(category,
					new Hashtable<T, Integer>(
							Classifier.INITIAL_FEATURE_DICTIONARY_CAPACITY));
			features = this.featureCountPerCategory.get(category);
		}
		Integer count = features.get(feature);
		if (count == null) {
			features.put(feature, 0);
			count = features.get(feature);
		}
		features.put(feature, ++count);

		Integer totalCount = this.totalFeatureCount.get(feature);
		if (totalCount == null) {
			this.totalFeatureCount.put(feature, 0);
			totalCount = this.totalFeatureCount.get(feature);
		}
		this.totalFeatureCount.put(feature, ++totalCount);
	}

	/**
	 * Increments the count of a given category. This is equal to telling the
	 * classifier, that this category has occurred once more.
	 * 
	 * @param category
	 *            The category, which count to increase.
	 */
	public void incrementCategory(K category) {
		Integer count = this.totalCategoryCount.get(category);
		if (count == null) {
			this.totalCategoryCount.put(category, 0);
			count = this.totalCategoryCount.get(category);
		}
		this.totalCategoryCount.put(category, ++count);
	}

	/**
	 * Retrieves the number of occurrences of the given feature in the given
	 * category.
	 * 
	 * @param feature
	 *            The feature, which count to retrieve.
	 * @param category
	 *            The category, which the feature occurred in.
	 * @return The number of occurrences of the feature in the category.
	 */
	public int featureCount(T feature, K category) {
		Dictionary<T, Integer> features = this.featureCountPerCategory
				.get(category);
		if (features == null)
			return 0;
		Integer count = features.get(feature);
		return (count == null) ? 0 : count.intValue();
	}

	/**
	 * Retrieves the number of occurrences of the given category.
	 * 
	 * @param category
	 *            The category, which count should be retrieved.
	 * @return The number of occurrences.
	 */
	public int categoryCount(K category) {
		Integer count = this.totalCategoryCount.get(category);
		return (count == null) ? 0 : count.intValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float featureProbability(T feature, K category) {
		if (this.categoryCount(category) == 0)
			return 0;
		return (float) this.featureCount(feature, category)
				/ (float) this.categoryCount(category);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with
	 * overall weight of <code>1.0</code> and an assumed probability of
	 * <code>0.5</code>. The probability defaults to the overall feature
	 * probability.
	 * 
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureProbability(Object,
	 *      Object)
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureWeighedAverage(Object,
	 *      Object, IFeatureProbability, float, float)
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category) {
		return this.featureWeighedAverage(feature, category, null, 1.0f, 0.5f);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with
	 * overall weight of <code>1.0</code>, an assumed probability of
	 * <code>0.5</code> and the given object to use for probability calculation.
	 * 
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureWeighedAverage(Object,
	 *      Object, IFeatureProbability, float, float)
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @param calculator
	 *            The calculating object.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category,
			IFeatureProbability<T, K> calculator) {
		return this.featureWeighedAverage(feature, category, calculator, 1.0f,
				0.5f);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with the
	 * given weight and an assumed probability of <code>0.5</code> and the given
	 * object to use for probability calculation.
	 * 
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureWeighedAverage(Object,
	 *      Object, IFeatureProbability, float, float)
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @param calculator
	 *            The calculating object.
	 * @param weight
	 *            The feature weight.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category,
			IFeatureProbability<T, K> calculator, float weight) {
		return this.featureWeighedAverage(feature, category, calculator,
				weight, 0.5f);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with the
	 * given weight, the given assumed probability and the given object to use
	 * for probability calculation.
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @param calculator
	 *            The calculating object.
	 * @param weight
	 *            The feature weight.
	 * @param assumedProbability
	 *            The assumed probability.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category,
			IFeatureProbability<T, K> calculator, float weight,
			float assumedProbability) {

		/*
		 * use the given calculating object or the default method to calculate
		 * the probability that the given feature occurred in the given
		 * category.
		 */
		final float basicProbability = (calculator == null) ? this
				.featureProbability(feature, category) : calculator
				.featureProbability(feature, category);

		Integer totals = this.totalFeatureCount.get(feature);
		if (totals == null)
			totals = 0;
		return (weight * assumedProbability + totals * basicProbability)
				/ (weight + totals);
	}

	/**
	 * Train the classifier by telling it that the given features resulted in
	 * the given category.
	 * 
	 * @param category
	 *            The category the features belong to.
	 * @param features
	 *            The features that resulted in the given category.
	 */
	public void learn(K category, Collection<T> features) {
		this.learn(new Classification<T, K>(features, category));
	}

	/**
	 * Train the classifier by telling it that the given features resulted in
	 * the given category.
	 * 
	 * @param classification
	 *            The classification to learn.
	 */
	public void learn(Classification<T, K> classification) {

		for (T feature : classification.getFeatureset()) {
			this.incrementFeature(feature, classification.getCategory());
			// this.incrementFeatureDb(feature, classification.getCategory());
		}
		this.incrementCategory(classification.getCategory());
		// this.incrementCategoryDb(classification.getCategory());
	}

	/**
	 * The classify method. It will retrieve the most likely category for the
	 * features given and depends on the concrete classifier implementation.
	 * 
	 * @param features
	 *            The features to classify.
	 * @return The category most likely.
	 */
	public abstract Classification<T, K> classify(Collection<T> features);

}
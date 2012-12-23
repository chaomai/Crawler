package crawler;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import snowball.SnowballStemmer;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class ProcessDocs {

	/**
	 * Tokennize a source string.
	 * 
	 * @param source
	 * @return
	 */
	public static ArrayList<String> tokenize(String source) {
		Reader reader = new StringReader(source);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		PTBTokenizer ptbTokenizer = new PTBTokenizer(reader,
				new CoreLabelTokenFactory(),
				"normalizeParentheses=false, normalizeOtherBrackets=false");

		ArrayList<String> tokens = new ArrayList<String>();

		for (CoreLabel label; ptbTokenizer.hasNext();) {
			label = (CoreLabel) ptbTokenizer.next();
			String tempWord = label.value().toLowerCase();

			if (!isStopWord(tempWord) && !isPunctuation(tempWord)) {
				tokens.add(tempWord);
			}
		}

		return tokens;
	}

	/**
	 * Stem tokens.
	 * 
	 * @param tokens
	 *            Words that are tokenized.
	 * @return Words are stemmed.
	 */
	public static ArrayList<String> stemming(ArrayList<String> tokens) {
		SnowballStemmer stemmer = null;

		try {
			Class<?> stemClass = Class.forName("snowball.englishStemmer");
			stemmer = (SnowballStemmer) stemClass.newInstance();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" Error when initializing Stemmer! ");
			System.exit(1);
		}

		try {
			for (int i = 0; i < tokens.size(); i++) {
				stemmer.setCurrent(tokens.get(i));
				stemmer.stem();
				tokens.set(i, stemmer.getCurrent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tokens;
	}

	/**
	 * Check whether a word is stopsord.
	 * 
	 * @param word
	 *            Word that isn't tokenized and stemmed.
	 * @return Whether a word is stopword.
	 */
	public static boolean isStopWord(String word) {
		// stop words from MySQL
		String stopWords[] = { "a", "able", "about", "above", "according",
				"accordingly", "across", "actually", "after", "afterwards",
				"again", "against", "ain", "all", "allow", "allows", "almost",
				"alone", "along", "already", "also", "although", "always",
				"am", "among", "amongst", "an", "and", "another", "any",
				"anybody", "anyhow", "anyone", "anything", "anyway", "anyways",
				"anywhere", "apart", "appear", "appreciate", "appropriate",
				"are", "aren", "around", "as", "aside", "ask", "asking",
				"associated", "at", "available", "away", "awfully", "be",
				"became", "because", "become", "becomes", "becoming", "been",
				"before", "beforehand", "behind", "being", "believe", "below",
				"beside", "besides", "best", "better", "between", "beyond",
				"both", "brief", "but", "by", "c", "came", "can", "cannot",
				"cant", "cause", "causes", "certain", "certainly", "changes",
				"clearly", "co", "com", "come", "comes", "concerning",
				"consequently", "consider", "considering", "contain",
				"containing", "contains", "corresponding", "could", "couldn",
				"course", "currently", "d", "definitely", "described",
				"despite", "did", "didn", "different", "do", "does", "doesn",
				"doing", "don", "done", "down", "downwards", "during", "each",
				"edu", "eg", "eight", "either", "else", "elsewhere", "enough",
				"entirely", "especially", "et", "etc", "even", "ever", "every",
				"everybody", "everyone", "everything", "everywhere", "ex",
				"exactly", "example", "except", "far", "few", "fifth", "first",
				"five", "followed", "following", "follows", "for", "former",
				"formerly", "forth", "four", "from", "further", "furthermore",
				"get", "gets", "getting", "given", "gives", "go", "goes",
				"going", "gone", "got", "gotten", "greetings", "had", "hadn",
				"happens", "hardly", "has", "hasn", "have", "haven", "having",
				"he", "hello", "help", "hence", "her", "here", "hereafter",
				"hereby", "herein", "hereupon", "hers", "herself", "hi", "him",
				"himself", "his", "hither", "hopefully", "how", "howbeit",
				"however", "i", "ie", "if", "ignored", "immediate", "in",
				"inasmuch", "inc", "indeed", "indicate", "indicated",
				"indicates", "inner", "insofar", "instead", "into", "inward",
				"is", "isn", "it", "its", "itself", "just", "keep", "keeps",
				"kept", "know", "known", "knows", "last", "lately", "later",
				"latter", "latterly", "least", "less", "lest", "let", "let",
				"like", "liked", "likely", "little", "ll", "look", "looking",
				"looks", "ltd", "m", "mainly", "many", "may", "maybe", "me",
				"mean", "meanwhile", "merely", "might", "mon", "more",
				"moreover", "most", "mostly", "much", "must", "my", "myself",
				"name", "namely", "nd", "near", "nearly", "necessary", "need",
				"needs", "neither", "never", "nevertheless", "new", "next",
				"nine", "no", "nobody", "non", "none", "noone", "nor",
				"normally", "not", "nothing", "novel", "now", "nowhere",
				"obviously", "of", "off", "often", "oh", "ok", "okay", "old",
				"on", "once", "one", "ones", "only", "onto", "or", "other",
				"others", "otherwise", "ought", "our", "ours", "ourselves",
				"out", "outside", "over", "overall", "own", "particular",
				"particularly", "per", "perhaps", "placed", "please", "plus",
				"possible", "presumably", "probably", "provides", "que",
				"quite", "qv", "rather", "rd", "re", "really", "reasonably",
				"regarding", "regardless", "regards", "relatively",
				"respectively", "right", "s", "said", "same", "saw", "say",
				"saying", "says", "second", "secondly", "see", "seeing",
				"seem", "seemed", "seeming", "seems", "seen", "self", "selves",
				"sensible", "sent", "serious", "seriously", "seven", "several",
				"shall", "she", "should", "shouldn", "since", "six", "so",
				"some", "somebody", "somehow", "someone", "something",
				"sometime", "sometimes", "somewhat", "somewhere", "soon",
				"sorry", "specified", "specify", "specifying", "still", "sub",
				"such", "sup", "sure", "t", "take", "taken", "tell", "tends",
				"th", "than", "thank", "thanks", "thanx", "that", "thats",
				"the", "their", "theirs", "them", "themselves", "then",
				"thence", "there", "there", "thereafter", "thereby",
				"therefore", "therein", "theres", "thereupon", "these", "they",
				"think", "third", "this", "thorough", "thoroughly", "those",
				"though", "three", "through", "throughout", "thru", "thus",
				"to", "together", "too", "took", "toward", "towards", "tried",
				"tries", "truly", "try", "trying", "twice", "two", "un",
				"under", "unfortunately", "unless", "unlikely", "until",
				"unto", "up", "upon", "us", "use", "used", "useful", "uses",
				"using", "usually", "value", "various", "ve", "very", "via",
				"viz", "vs", "want", "wants", "was", "wasn", "way", "we",
				"welcome", "well", "went", "were", "weren", "what", "whatever",
				"when", "whence", "whenever", "where", "whereafter", "whereas",
				"whereby", "wherein", "whereupon", "wherever", "whether",
				"which", "while", "whither", "who", "whoever", "whole", "whom",
				"whose", "why", "will", "willing", "wish", "with", "within",
				"without", "won", "wonder", "would", "wouldn", "yes", "yet",
				"you", "your", "yours", "yourself", "yourselves", "zero", "a",
				"b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
				"z", "a.", "b.", "c.", "d.", "e.", "f.", "g.", "h.", "i.",
				"j.", "k.", "l.", "m.", "n.", "o.", "p.", "q.", "r.", "s.",
				"t.", "u.", "v.", "w.", "x.", "y.", "z.", "n't", "'s", "m.",
				"ve", "'d", "â", "'m" };

		Set<String> stopWordsSet = new HashSet<String>(Arrays.asList(stopWords));

		return stopWordsSet.contains(word);
	}

	/**
	 * Check whether a word is punctuation.
	 * 
	 * @param word
	 *            Word that isn't tokenized and stemmed.
	 * @return Whether a word is punctuation.
	 */
	public static boolean isPunctuation(String word) {
		String punctuations[] = { "~", "`", "!", "@", "#", "$", "%", "^", "&",
				"*", "(", ")", "_", "+", "-", "=", "{", "}", "|", "[", "]",
				"\\", ":", "\"", ";", "'", "<", ">", "?", ",", ".", "/", "``",
				"''", "--", "\\/", "?!", "__", "...", "™", "\\*\\*\\*\\*",
				"\\*\\*\\*", "\\*", "¯", "_______________" };

		Set<String> punctuationsSet = new HashSet<String>(
				Arrays.asList(punctuations));

		return punctuationsSet.contains(word);
	}

	/**
	 * Check whether a word is number.
	 * 
	 * @param word
	 *            Word that isn't tokenized and stemmed.
	 * @return Whether a word is number.
	 */
	public static boolean isNumber(String word) {
		Pattern pattern1 = Pattern.compile("\\d{2}:\\d{2}");
		Pattern pattern2 = Pattern
				.compile("^(\\d+(,\\d\\d\\d)*(\\.\\d+)?|\\d+(\\.\\d+)?)$");
		if (pattern1.matcher(word).matches()
				|| pattern2.matcher(word).matches()) {
			return true;
		} else {
			return false;
		}
	}
}

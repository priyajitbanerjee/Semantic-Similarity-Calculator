/**
 * 
 */
package edu.ju.ssc;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import edu.ju.ssc.dao.SentencePairDAO;
import edu.ju.ssc.exception.SSCException;
import edu.ju.ssc.feature.FeatureCalculator;
import edu.ju.ssc.util.IOUtils;
import edu.ju.ssc.util.SSCUtils;

/**
 * @author Priyajit Banerjee
 *
 */
public class SemanticSimilarityCalculationDemo {
	private static final Logger logger = LogManager
			.getLogger(SemanticSimilarityCalculationDemo.class);
	private static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			Constants.CONTEXT_CONFIG_FILE);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SSCException {
		if (args.length > 0) {
			updateConfiguration(args[0]);
		}
		startApplication();
	}

	/**
	 * 
	 * @param inputDataFileName
	 */
	public static void updateConfiguration(String inputDataFileName) {
		WS4JConfiguration.getInstance().setLeskNormalize(true);
		WS4JConfiguration.getInstance().setStopList(
				SSCConfiguration.getInstance().getProperties()
						.getProperty(Constants.STOP_WORDS_LIST_FILE));
		SSCConfiguration configuration = SSCConfiguration.getInstance();
		configuration.setInputDataFile(inputDataFileName);
	}

	/**
	 * 
	 * @throws SSCException
	 */
	public static void startApplication() throws SSCException {
		try {
			List<Sentence> sentenceList = SSCUtils.getAllLinesAsSentenceList(
					SSCConfiguration.getInstance().getInputDataFile(),
					SSCConfiguration.getInstance().getLinesToSkipFromTop());
			FeatureCalculator featureCalculator = new FeatureCalculator(
					sentenceList, SSCUtils.getFeatureNameRelatednessCalcMap());
			featureCalculator.calculate();
			ConcurrentLinkedDeque<SentencePair> sentencePairList = featureCalculator
					.getSentencePairConcurrDeque();
			IOUtils.storeSentencePairList(sentencePairList,
					Constants.OUTPUT_DATA_FILE_NAME);
			SentencePairDAO sentencePairDAO = new SentencePairDAO();
			sentencePairDAO.setSentencePairList(sentencePairList);
			sentencePairDAO.save(appContext.getBean(JdbcTemplate.class));

		} catch (Exception e) {
			logger.debug(Constants.DEBUG_EXCEPTION_MSG, e);
			throw new SSCException(Constants.DEBUG_EXCEPTION_MSG, e);
		}
	}
}

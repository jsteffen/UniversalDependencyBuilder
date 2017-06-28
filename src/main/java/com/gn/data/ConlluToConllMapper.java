package com.gn.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import de.dfki.mlt.gnt.config.ConfigKeys;
import de.dfki.mlt.gnt.config.CorpusConfig;
import de.dfki.mlt.gnt.config.ModelConfig;
import de.dfki.mlt.gnt.data.Pair;

public class ConlluToConllMapper {

	private static CorpusConfig corpusConfig = new CorpusConfig(new PropertiesConfiguration());
	private static ModelConfig modelConfig = new ModelConfig(new PropertiesConfiguration());
	
	/*
	 * Define file names for corpusProps and dataProps
	 */
	public  static String getCorpusConfigFileName(String languageName, String languageID){
		return UDlanguages.conllPath + languageName + "/" + languageID + ".corpus.conf";
	}
	
	public  static String getModelConfigFileName(String languageName, String languageID){
		return UDlanguages.
				conllPath + languageName + "/" + languageID + ".model.conf";
	}
	
	public  static String getGNTmodelZipFileName(String languageName, String languageID){
		return UDlanguages.
				conllPath + languageName + "/" + languageID + "-GNTmodel.zip";
	}
	
	public static String getMDPmodelZipFileName(String languageName, String languageID) {
		return UDlanguages.
				conllPath + languageName + "/" + languageID + "-MDPmodel.zip";
	}
	
	public static String getConllMDPresultFile(String testFile) {
		return testFile.split("\\.conll")[0]+"-result.conll";
	}
	
	public static String getConllTrainFile(String languageName, String languageID) {
		return UDlanguages.
				conllPath + languageName + "/" + languageID + "-ud-train.conll";
	}
	public static String getConllTestFile(String languageName, String languageID) {
		return UDlanguages.
				conllPath + languageName + "/" + languageID + "-ud-test.conll";
	}

	public static String getConllDevFile(String languageName, String languageID) {
		return UDlanguages.
				conllPath + languageName + "/" + languageID + "-ud-dev.conll";
	}

	/* Create corpusProp.xml file
	 * E.g., conll/Arabic/arabicCorpusProps.xml
	 * -> only used during training
	 * 
	 * define it global so that it can be incrementally added with conll and sentence file names
	 */


	/*
	 * <!-- Tagger name -->
	<entry key="taggerName">FRUNIPOS</entry>

	<!-- HERE ARE THE MAIN CORPUS BASED SETTINGS -->

	<!-- Corpus access parameters: Index of relevant tags in conll format -->
	<entry key="wordFormIndex">1</entry>
	<entry key="posTagIndex">3</entry>
	 */
	private static void initLanguageCorpusConfig(String languageID){
		ConlluToConllMapper.corpusConfig.setProperty(
		    ConfigKeys.TAGGER_NAME, languageID.toUpperCase()+"UNIPOS");
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.WORD_FORM_INDEX, 1);
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.TAG_INDEX, 3);
	}
	
	private static void writeCorpusConfig(String languageName, String languageID) throws IOException{
		String corpusConfigFileName = 
		    ConlluToConllMapper.getCorpusConfigFileName(languageName, languageID);
		try (Writer out = Files.newBufferedWriter(Paths.get(corpusConfigFileName))) {
		  ConlluToConllMapper.corpusConfig.write(Files.newBufferedWriter(Paths.get("configFilename")));
		} catch (ConfigurationException e) {
      e.printStackTrace();
    }
	}

	/* Create dataProps.xml file
	 * e.g., conll/Arabic/arabicDataProps.xml
	 * -> will be copied to standard name and packed in archive during training
	 * -> copy complex model file name to simple model file name like arabic-upos.zip
	 */

	/*
	 * Example from FrUniPosTagger.xml
	 * /GNT/src/main/resources/dataProps/FrUniPosTagger.xml

	 * <entry key="saveModelInputFile">false</entry>

	<!-- Store wrong tag assignment in file resources/eval/*.debug -->
	<entry key="debug">false</entry>

	<!-- Liblinear settings -->
	<entry key="solverType">MCSVM_CS</entry>
	<entry key="c">0.1</entry>
	<entry key="eps">0.3</entry>

	<!-- Control parameters -->
	<entry key="windowSize">2</entry>
	<entry key="numberOfSentences">-1</entry>
	<entry key="dim">0</entry>
	<entry key="subSamplingThreshold">0.000000001</entry>

	<!-- features (not) activated -->
	<entry key="withWordFeats">false</entry>
	<entry key="withShapeFeats">true</entry>
	<entry key="withSuffixFeats">true</entry>
	<entry key="withClusterFeats">false</entry>

	<entry key="WordSuffixFeatureFactory.ngram">false</entry>
	 */

	//NOTE - SAME for ALL languages !
	private static void initLanguageModelConfig(String languageID){
		ConlluToConllMapper.modelConfig.setProperty(
		    ConfigKeys.TAGGER_NAME, languageID.toUpperCase()+"UNIPOS");
		// <!-- Liblinear settings -->
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.SOLVER_TYPE, "MCSVM_CS");
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.C, 0.1);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.EPS, 0.3);
		// <!-- Control parameters -->
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.WINDOW_SIZE, 2);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.NUMBER_OF_SENTENCES, -1);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.DIM, 0);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.SUB_SAMPLING_THRESHOLD, 0.000000001);
		// <!-- features (not) activated -->
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.WITH_WORD_FEATS, false);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.WITH_SHAPE_FEATS, true);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.WITH_SUFFIX_FEATS, true);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.WITH_CLUSTER_FEATS, false);
		ConlluToConllMapper.modelConfig.setProperty(ConfigKeys.WITH_LABEL_FEATS, false);
	}

	private static void writeModelConfig(String languageName, String languageID) throws IOException{
		String modelConfigFileName = ConlluToConllMapper.getModelConfigFileName(languageName, languageID);
		try (Writer out = Files.newBufferedWriter(Paths.get(modelConfigFileName))) {
		  ConlluToConllMapper.modelConfig.write(out);
		} catch (ConfigurationException e) {
      e.printStackTrace();
    }
	}

	// Just create it initially with taggername
	// and directly write it out

	// Create the CONLL files

	/**
	 * Example
	 * 
	 * Input: "/Users/gune00/data/UniversalDependencies/" "Arabic" "ar" "dev"
	 * 
	 * Output: "/Users/gune00/data/UniversalDependencies/UD_Arabic-master/ar-ud-dev.conllu"
	 * 
	 * @param conlluPath 
	 * @param languageName
	 * @param languageID
	 * @param mode
	 * @return
	 */
	private  static String makeConlluFileName (String languageName, String languageID, String mode){
		String fileName = "";
		if (UDlanguages.version.equals("1_2")){
			fileName = UDlanguages.conlluPath + "UD_" + languageName + "-master/" + languageID + "-ud-" + mode + ".conllu";
		}
		else
			if (UDlanguages.version.equals("1_3")){
				fileName = UDlanguages.conlluPath + "UD_" + languageName  + "/" + languageID + "-ud-" + mode + ".conllu";
			}
		return fileName;
	}

	/**
	 * Creates 
	 * "/Users/gune00/data/UniversalDependencies/conll/Arabic/ar-ud-dev.conll";
	 * @param languageName
	 * @param languageID
	 * @param mode
	 * @return
	 */
	private  static String makeConllFileName (String languageName, String languageID, String mode){
		String conllDirName = UDlanguages.conllPath + languageName +"/";
		File conllLangDir = new File(conllDirName);
		if (!conllLangDir.exists()) conllLangDir.mkdir();
		String fileName = conllDirName + languageID + "-ud-" + mode + ".conll";
		return fileName;
	}

	private static String makeSentenceFileName(String conllFileName){
		return conllFileName.split("\\.conll")[0]+"-sents.txt";
	}

	/**
	 * bascially maps a conllu to conll format - very simple process so far.
	 * @param sourceFileName
	 * @param targetFileName
	 * @throws IOException
	 */
	private static void transformConlluToConllFile(String sourceFileName, String targetFileName)
			throws IOException {

		String sourceEncoding = "utf-8";
		String targetEncoding = "utf-8";
		// init reader for CONLL style file
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(sourceFileName),
						sourceEncoding));

		// init writer for line-wise file
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(targetFileName),
						targetEncoding));

		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()) 
				writer.newLine();
			else
			{
				// Normalize line which is assumed to correspond to a sentence.
				if (!line.startsWith("#")){
					writer.write(line);
					writer.newLine();
				}
			}
		}
		reader.close();
		writer.close();
	}

	private static void transcodeConllToSentenceFile(String sourceFileName, String targetFileName)
			throws IOException {
		String sourceEncoding = "utf-8";
		String targetEncoding = "utf-8";
		// init reader for CONLL style file

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(sourceFileName),
						sourceEncoding));

		// init writer for line-wise file
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(targetFileName),
						targetEncoding));

		String line = "";
		List<String> tokens = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				// If we read a newline it means we know we have just extracted the words
				// of a sentence, so write them to file
				if (!tokens.isEmpty()){
					writer.write(sentenceToString(tokens)+"\n");
					tokens = new ArrayList<String>();
				}
			}
			else
			{
				// Extract the word from each CONLL token line
				String[] tokenizedLine = line.split("\t");
				tokens.add(tokenizedLine[1]);
			}
		}
		reader.close();
		writer.close();
	}

	private static String sentenceToString(List<String> tokens){
		String sentenceString = "";
		for (int i=0; i < tokens.size()-1; i++){
			sentenceString = sentenceString + tokens.get(i)+" ";
		}
		return sentenceString+tokens.get(tokens.size()-1);
	}



	/**
	 * Transform files for train/test/dev and call them in own main caller
	 * @param languageName
	 * @param languageID
	 * @throws IOException
	 */
	private static void transformerTrain(String languageName, String languageID) throws IOException{
		String conlluFile = ConlluToConllMapper.makeConlluFileName(languageName, languageID, "train");
		String conllFile = ConlluToConllMapper.makeConllFileName(languageName, languageID, "train");
		String sentFile = ConlluToConllMapper.makeSentenceFileName(conllFile);
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.TRAINING_LABELED_DATA, conllFile);	
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.TRAINING_UNLABELED_DATA, sentFile);
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.TRAINING_FILE, conllFile);

		ConlluToConllMapper.transformConlluToConllFile(conlluFile, conllFile);
		ConlluToConllMapper.transcodeConllToSentenceFile(conllFile, sentFile);
	}

	private static void transformerDev(String languageName, String languageID) throws IOException{
		String conlluFile = ConlluToConllMapper.makeConlluFileName(languageName, languageID, "dev");
		String conllFile = ConlluToConllMapper.makeConllFileName(languageName, languageID, "dev");
		String sentFile = ConlluToConllMapper.makeSentenceFileName(conllFile);
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.DEV_LABELED_DATA, conllFile);
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.DEV_UNLABELED_DATA, sentFile);

		ConlluToConllMapper.transformConlluToConllFile(conlluFile, conllFile);
		ConlluToConllMapper.transcodeConllToSentenceFile(conllFile, sentFile);
	}

	private static void transformerTest(String languageName, String languageID) throws IOException{
		String conlluFile = ConlluToConllMapper.makeConlluFileName(languageName, languageID, "test");
		String conllFile = ConlluToConllMapper.makeConllFileName(languageName, languageID, "test");
		String sentFile = ConlluToConllMapper.makeSentenceFileName(conllFile);
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.TEST_LABELED_DATA, conllFile);
		ConlluToConllMapper.corpusConfig.setProperty(ConfigKeys.TEST_UNLABELED_DATA, sentFile);

		ConlluToConllMapper.transformConlluToConllFile(conlluFile, conllFile);
		ConlluToConllMapper.transcodeConllToSentenceFile(conllFile, sentFile);
	}

	public static void transformer(String languageName, String languageID) throws IOException{
		ConlluToConllMapper.transformerTrain(languageName, languageID);
		ConlluToConllMapper.transformerDev(languageName, languageID);
		ConlluToConllMapper.transformerTest(languageName, languageID);
	}
	
	public static void runUDversion_1_2() throws IOException{
		UDlanguages.setVersion_1_2();
		
		UDlanguages.addLanguages();
		for (Pair<String, String> language : UDlanguages.languages){
			System.out.println("Processing: " + language);
			initLanguageCorpusConfig(language.getRight());
			initLanguageModelConfig(language.getRight());

			transformer(language.getLeft(), language.getRight());

			writeCorpusConfig(language.getLeft(), language.getRight());
			writeModelConfig(language.getLeft(), language.getRight());
		}
	}
	
	public static void runUDversion_1_3() throws IOException{
		UDlanguages.setVersion_1_3();
		
		UDlanguages.addLanguages();
		for (Pair<String, String> language : UDlanguages.languages){
			System.out.println("Processing: " + language);
			initLanguageCorpusConfig(language.getRight());
			initLanguageModelConfig(language.getRight());

			transformer(language.getLeft(), language.getRight());

			writeCorpusConfig(language.getLeft(), language.getRight());
			writeModelConfig(language.getLeft(), language.getRight());
		}
	}

	/**
	 * Loop across languages
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		runUDversion_1_3();
	}
}

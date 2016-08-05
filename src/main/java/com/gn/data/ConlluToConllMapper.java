package com.gn.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import data.Pair;

public class ConlluToConllMapper {

	public static String conlluPath = "/Users/gune00/data/UniversalDependencies/";
	public static String conllPath = "/Users/gune00/data/UniversalDependencies/conll/";

	private static Properties corpusProps = new Properties();
	private static Properties dataProps = new Properties();
	
	/*
	 * Define file names for corpusProps and dataProps
	 */
	public  static String getCorpusPropsFile(String languageName, String languageID){
		return ConlluToConllMapper.
				conllPath + languageName + "/" + languageID + "-corpusProps.xml";
	}
	
	public  static String getDataPropsFile(String languageName, String languageID){
		return ConlluToConllMapper.
				conllPath + languageName + "/" + languageID + "-dataProps.xml";
	}
	
	public  static String getGNTmodelZipFileName(String languageName, String languageID){
		return ConlluToConllMapper.
				conllPath + languageName + "/" + languageID + "-GNTmodel.zip";
	}
	
	public static String getMDPmodelZipFileName(String languageName, String languageID) {
		return ConlluToConllMapper.
				conllPath + languageName + "/" + languageID + "-MDPmodel.zip";
	}
	
	public static String getConllMDPresultFile(String testFile) {
		return testFile.split("\\.conll")[0]+"-result.conll";
	}
	
	public static String getConllTrainFile(String languageName, String languageID) {
		return ConlluToConllMapper.
				conllPath + languageName + "/" + languageID + "-ud-train.conll";
	}
	public static String getConllTestFile(String languageName, String languageID) {
		return ConlluToConllMapper.
				conllPath + languageName + "/" + languageID + "-ud-test.conll";
	}

	public static String getConllDevFile(String languageName, String languageID) {
		return ConlluToConllMapper.
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
	private static void initLanguageCorpusPropsFile(String languageID){
		ConlluToConllMapper.corpusProps.setProperty("taggerName", languageID.toUpperCase()+"UNIPOS");
		ConlluToConllMapper.corpusProps.setProperty("wordFormIndex", "1");
		ConlluToConllMapper.corpusProps.setProperty("posTagIndex", "3");
	}
	
	private static void writeLanguageCorpusPropsFile(String languageName, String languageID) throws IOException{
		String configFilename = ConlluToConllMapper.getCorpusPropsFile(languageName, languageID);
		File file = new File(configFilename);
		FileOutputStream fileOut = new FileOutputStream(file);
		ConlluToConllMapper.corpusProps.storeToXML(fileOut, "Settings for corpus props");
		fileOut.close();		
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

	//NOTE same for all languages !
	private static void initLanguageDataPropsFile(String languageID){
		ConlluToConllMapper.dataProps.setProperty("taggerName", languageID.toUpperCase()+"UNIPOS");
		ConlluToConllMapper.dataProps.setProperty("saveModelInputFile", "false");
		// <!-- Liblinear settings -->
		ConlluToConllMapper.dataProps.setProperty("solverType", "MCSVM_CS");
		ConlluToConllMapper.dataProps.setProperty("c", "0.1");
		ConlluToConllMapper.dataProps.setProperty("eps", "0.3");
		// <!-- Control parameters -->
		ConlluToConllMapper.dataProps.setProperty("windowSize", "2");
		ConlluToConllMapper.dataProps.setProperty("numberOfSentences", "-1");
		ConlluToConllMapper.dataProps.setProperty("dim", "0");
		ConlluToConllMapper.dataProps.setProperty("subSamplingThreshold", "0.000000001");
		ConlluToConllMapper.dataProps.setProperty("debug", "false");
		// <!-- features (not) activated -->
		ConlluToConllMapper.dataProps.setProperty("withWordFeats", "false");
		ConlluToConllMapper.dataProps.setProperty("withShapeFeats", "true");
		ConlluToConllMapper.dataProps.setProperty("withSuffixFeats", "true");
		ConlluToConllMapper.dataProps.setProperty("withClusterFeats", "false");
		ConlluToConllMapper.dataProps.setProperty("withLabelFeats", "false");
	}

	private static void writeLanguageDataPropsFile(String languageName, String languageID) throws IOException{
		String configFilename = ConlluToConllMapper.getDataPropsFile(languageName, languageID);
		File file = new File(configFilename);
		FileOutputStream fileOut = new FileOutputStream(file);
		ConlluToConllMapper.dataProps.storeToXML(fileOut, "Settings for data props");
		fileOut.close();		
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
		String fileName =
				ConlluToConllMapper.conlluPath + "UD_" + languageName + "-master/" + languageID + "-ud-" + mode + ".conllu";
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
		String conllDirName = ConlluToConllMapper.conllPath + languageName +"/";
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
	 * Transform files fro train/test/dev and call them in onw main caller
	 * @param languageName
	 * @param languageID
	 * @throws IOException
	 */
	private static void transformerTrain(String languageName, String languageID) throws IOException{
		String conlluFile = ConlluToConllMapper.makeConlluFileName(languageName, languageID, "train");
		String conllFile = ConlluToConllMapper.makeConllFileName(languageName, languageID, "train");
		String sentFile = ConlluToConllMapper.makeSentenceFileName(conllFile);
		ConlluToConllMapper.corpusProps.setProperty("trainingLabeledData", conllFile);	
		ConlluToConllMapper.corpusProps.setProperty("trainingUnLabeledData", sentFile);
		ConlluToConllMapper.corpusProps.setProperty("trainingFile", conllFile);

		ConlluToConllMapper.transformConlluToConllFile(conlluFile, conllFile);
		ConlluToConllMapper.transcodeConllToSentenceFile(conllFile, sentFile);
	}

	private static void transformerDev(String languageName, String languageID) throws IOException{
		String conlluFile = ConlluToConllMapper.makeConlluFileName(languageName, languageID, "dev");
		String conllFile = ConlluToConllMapper.makeConllFileName(languageName, languageID, "dev");
		String sentFile = ConlluToConllMapper.makeSentenceFileName(conllFile);
		ConlluToConllMapper.corpusProps.setProperty("devLabeledData", conllFile);
		ConlluToConllMapper.corpusProps.setProperty("devUnLabeledData", sentFile);

		ConlluToConllMapper.transformConlluToConllFile(conlluFile, conllFile);
		ConlluToConllMapper.transcodeConllToSentenceFile(conllFile, sentFile);
	}

	private static void transformerTest(String languageName, String languageID) throws IOException{
		String conlluFile = ConlluToConllMapper.makeConlluFileName(languageName, languageID, "test");
		String conllFile = ConlluToConllMapper.makeConllFileName(languageName, languageID, "test");
		String sentFile = ConlluToConllMapper.makeSentenceFileName(conllFile);
		ConlluToConllMapper.corpusProps.setProperty("testLabeledData", conllFile);
		ConlluToConllMapper.corpusProps.setProperty("testUnLabeledData", sentFile);

		ConlluToConllMapper.transformConlluToConllFile(conlluFile, conllFile);
		ConlluToConllMapper.transcodeConllToSentenceFile(conllFile, sentFile);
	}

	public static void transformer(String languageName, String languageID) throws IOException{
		ConlluToConllMapper.transformerTrain(languageName, languageID);
		ConlluToConllMapper.transformerDev(languageName, languageID);
		ConlluToConllMapper.transformerTest(languageName, languageID);
	}

	/**
	 * Loop across languages
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		UDlanguages.addLanguages();
		for (Pair<String, String> language : UDlanguages.languages){
			System.out.println("Processing: " + language);
			ConlluToConllMapper.initLanguageCorpusPropsFile(language.getR());
			ConlluToConllMapper.initLanguageDataPropsFile(language.getR());

			ConlluToConllMapper.transformer(language.getL(), language.getR());

			ConlluToConllMapper.writeLanguageCorpusPropsFile(language.getL(), language.getR());
			ConlluToConllMapper.writeLanguageDataPropsFile(language.getL(), language.getR());
		}
	}


}

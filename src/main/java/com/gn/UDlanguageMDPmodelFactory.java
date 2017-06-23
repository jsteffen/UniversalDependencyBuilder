package com.gn;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.gn.data.ConlluToConllMapper;
import com.gn.data.UDlanguages;
import com.gn.performance.MDPperformance;
import com.gn.performance.UDlanguagePerformance;

import de.dfki.mlt.gnt.data.Pair;
import de.bwaldvogel.liblinear.InvalidInputDataException;
import de.dfki.lt.mdparser.caller.MDPrunner;
import de.dfki.lt.mdparser.caller.MDPtrainer;
import de.dfki.lt.mdparser.eval.Eval;

public class UDlanguageMDPmodelFactory {

 public UDlanguageMDPmodelFactory(String version){
		UDlanguages.version = version;
		UDlanguages.addLanguages();
	}

	private void trainLanguage(String languageName, String languageID) 
			throws IOException, NoSuchAlgorithmException, InvalidInputDataException{

		String trainFile = ConlluToConllMapper.getConllTrainFile(languageName, languageID);
		String modelZipFileName = ConlluToConllMapper.getMDPmodelZipFileName(languageName, languageID);

		System.out.println("MDP training: " + trainFile + " into ModelFile: " + modelZipFileName);

		MDPtrainer.train(trainFile, modelZipFileName);
	}

	// TODO: this is basically the same as in UDlanguageGNTmodelFactory
	private void trainAllLanguages() 
			throws IOException, NoSuchAlgorithmException, InvalidInputDataException{
		long time1;
		long time2;
		time1 = System.currentTimeMillis();
		for (Pair<String, String> language : UDlanguages.languages){
			System.out.println("Training of: " + language);
			this.trainLanguage(language.getLeft(), language.getRight());	
		}
		time2 = System.currentTimeMillis();
		System.out.println("Complete training for " + UDlanguages.languages.size() + " languages:");
		System.out.println("System time (msec): " + (time2-time1));
	}

	private Eval testLanguage(String languageName, String languageID) throws IOException{

		String testFile = ConlluToConllMapper.getConllTestFile(languageName, languageID);
		String modelZipFileName = ConlluToConllMapper.getMDPmodelZipFileName(languageName, languageID);
		String mdpResultFile = ConlluToConllMapper.getConllMDPresultFile(testFile);

		return MDPrunner.conllFileParsingAndEval(testFile, mdpResultFile, modelZipFileName);
	}

	private void testAllLanguages() throws IOException{
		UDlanguagePerformance udPerformance = new UDlanguagePerformance();
		long time1;
		long time2;
		time1 = System.currentTimeMillis();
		for (Pair<String, String> language : UDlanguages.languages){
			System.out.println("Testing of: " + language);
			Eval eval = this.testLanguage(language.getLeft(), language.getRight());
			System.out.println("\n");

			MDPperformance mdpPerformance = new MDPperformance(eval);
			udPerformance.addNewLanguageMDPperformance(language.getRight(), mdpPerformance);
		}
		time2 = System.currentTimeMillis();
		System.out.println("Complete testing for " + UDlanguages.languages.size() + " languages:");
		System.out.println("System time (msec): " + (time2-time1));
		System.out.println(udPerformance.toMDPString());
	}

	public static void main(String[] args) 
			throws IOException, NoSuchAlgorithmException, InvalidInputDataException{
		UDlanguageMDPmodelFactory udFactory = new UDlanguageMDPmodelFactory("1_3");
		UDlanguages.ignore = true;
		udFactory.trainAllLanguages();
		udFactory.testAllLanguages();
	}
}

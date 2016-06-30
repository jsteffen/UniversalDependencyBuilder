package com.gn;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.gn.data.ConlluToConllMapper;
import com.gn.data.UDlanguages;
import com.gn.performance.MDPperformance;
import com.gn.performance.UDlanguagePerformance;

import data.Pair;
import de.bwaldvogel.liblinear.InvalidInputDataException;
import de.dfki.lt.mdparser.caller.MDPrunner;
import de.dfki.lt.mdparser.caller.MDPtrainer;
import de.dfki.lt.mdparser.eval.Eval;

public class UDlanguageMDPmodelFactory {
	
	private Eval eval;
	
	private void trainLanguage(String languageName, String languageID) 
			throws IOException, NoSuchAlgorithmException, InvalidInputDataException{
		
		String trainFile = ConlluToConllMapper.getConllTrainFile(languageName, languageID);
		String modelZipFileName = ConlluToConllMapper.getMDPmodelZipFileName(languageName, languageID);
		
		MDPtrainer mdpTrainer = new MDPtrainer();
		
		System.out.println("MDP training: " + trainFile + " into ModelFile: " + modelZipFileName);
		
		
		mdpTrainer.trainer(trainFile, modelZipFileName);
	}
	
	// TODO: this is basically the same as in UDlanguageGNTmodelFactory
	private void trainAllLanguages() 
			throws IOException, NoSuchAlgorithmException, InvalidInputDataException{
		long time1;
		long time2;
		time1 = System.currentTimeMillis();
		for (Pair<String, String> language : UDlanguages.getLanguages()){
			System.out.println("Training of: " + language);
			this.trainLanguage(language.getL(), language.getR());	
		}
		time2 = System.currentTimeMillis();
		System.out.println("Complete training for " + UDlanguages.getLanguages().size() + " languages:");
		System.out.println("System time (msec): " + (time2-time1));
	}
	
	private void testLanguage(String languageName, String languageID) throws IOException{

		String testFile = ConlluToConllMapper.getConllTestFile(languageName, languageID);
		String modelZipFileName = ConlluToConllMapper.getMDPmodelZipFileName(languageName, languageID);
		String mdpResultFile = ConlluToConllMapper.getConllMDPresultFile(testFile);
		
		MDPrunner mdpRunner = new MDPrunner();

		mdpRunner.conllFileParsingAndEval(testFile, mdpResultFile, modelZipFileName);
		
		this.eval = mdpRunner.getEvaluator();
	}
	
	private void testAllLanguages() throws IOException{
		UDlanguagePerformance udPerformance = new UDlanguagePerformance();
		for (Pair<String, String> language : UDlanguages.getLanguages()){
			System.out.println("Testing of: " + language);
			this.testLanguage(language.getL(), language.getR());
			
			MDPperformance mdpPerformance = new MDPperformance(this.eval);
			udPerformance.addNewLanguageMDPperformance(language.getR(), mdpPerformance);
		}
		System.out.println(udPerformance.toMDPString());
	}

	public static void main(String[] args) 
			throws IOException, NoSuchAlgorithmException, InvalidInputDataException{
		UDlanguageMDPmodelFactory udFactory = new UDlanguageMDPmodelFactory();
		udFactory.trainAllLanguages();
	}
}

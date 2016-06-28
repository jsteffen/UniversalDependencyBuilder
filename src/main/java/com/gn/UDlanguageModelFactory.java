package com.gn;

import java.io.IOException;

import com.gn.data.ConlluToConllMapper;
import com.gn.data.UDlanguages;

import caller.RunTagger;
import caller.TrainTagger;
import data.Pair;

/**
 * Call GN trainer for each language in  ConlluToConllMapper.conllPath:
 * 
 * - get corpusPropsFile
 * - get dataPropsFile 
 * - call trainer
 * - create modelfile.ZIP
 * @author gune00
 *
 */
public class UDlanguageModelFactory {
	
	
	private void trainLanguage(String languageName, String languageID) throws IOException{
		String corpusFilename = ConlluToConllMapper.getCorpusPropsFile(languageName, languageID);
		String dataFilename = ConlluToConllMapper.getDataPropsFile(languageName, languageID);
		String modelZipFileName = ConlluToConllMapper.getGNTmodelZipFileName(languageName, languageID);
		String archiveTxtName = modelZipFileName.split("\\.zip")[0]+".txt";
		
		TrainTagger gntTrainer = new TrainTagger();
		
		System.out.println(corpusFilename);
		
		gntTrainer.trainer(dataFilename, corpusFilename, modelZipFileName, archiveTxtName);
	}
	
	private void trainAllLanguages() throws IOException{
		for (Pair<String, String> language : UDlanguages.getLanguages()){
			System.out.println("Training of: " + language);
			this.trainLanguage(language.getL(), language.getR());
			
		}
	}
	
	private void testLanguage(String languageName, String languageID) throws IOException{

		String corpusFilename = ConlluToConllMapper.getCorpusPropsFile(languageName, languageID);
		String modelZipFileName = ConlluToConllMapper.getGNTmodelZipFileName(languageName, languageID);
		String archiveTxtName = modelZipFileName.split("\\.zip")[0]+".txt";
		
		RunTagger.runner(modelZipFileName, corpusFilename, archiveTxtName);
	}
	
	private void testAllLanguages() throws IOException{
		for (Pair<String, String> language : UDlanguages.getLanguages()){
			System.out.println("Training of: " + language);
			this.testLanguage(language.getL(), language.getR());
			
		}
	}
	
	public static void main(String[] args) throws IOException{
		UDlanguageModelFactory udFactory = new UDlanguageModelFactory();
		udFactory.testAllLanguages();
	}

}

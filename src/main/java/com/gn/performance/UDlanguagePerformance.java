package com.gn.performance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import data.Pair;

public class UDlanguagePerformance {
	// <languageId, GNTperformance>
	private List<Pair<String,GNTperformance>> gntLanguagesPerformance = new ArrayList<Pair<String,GNTperformance>>();
	// <languageId, MDPperformance>
	private List<Pair<String,MDPperformance>> mdpLanguagesPerformance = new ArrayList<Pair<String,MDPperformance>>();

	public List<Pair<String, GNTperformance>> getGntLanguagesPerformance() {
		return gntLanguagesPerformance;
	}
	public void setGntLanguagesPerformance(List<Pair<String, GNTperformance>> gntLanguagesPerformance) {
		this.gntLanguagesPerformance = gntLanguagesPerformance;
	}
	public List<Pair<String, MDPperformance>> getMdpLanguagesPerformance() {
		return mdpLanguagesPerformance;
	}
	public void setMdpLanguagesPerformance(List<Pair<String, MDPperformance>> mdpLanguagesPerformance) {
		this.mdpLanguagesPerformance = mdpLanguagesPerformance;
	}
	
	public void addNewLanguageGNTperformance(String languageID, GNTperformance gntPerformance){
		Pair<String,GNTperformance> newPair = new Pair<String,GNTperformance>(languageID, gntPerformance);
		gntLanguagesPerformance.add(newPair);

	}
	
	public void addNewLanguageMDPperformance(String languageID, MDPperformance mdpPerformance){
		Pair<String,MDPperformance> newPair = new Pair<String,MDPperformance>(languageID, mdpPerformance);
		mdpLanguagesPerformance.add(newPair);
	}
	
	public String toGNTString(){
		String output = "Lang |  Acc   |  OOV   |  INV   |  Tok/Sec\n";
		output +=       "------------------------------------------\n";
		DecimalFormat formatter = new DecimalFormat("#0.00");
		double avgAcc = 0.0;
		double avgOOV = 0.0;
		
		for (Pair<String,GNTperformance> pair : gntLanguagesPerformance){
			output += pair.getL() + "   | " + pair.getR().toString() + "\n";
			avgAcc += pair.getR().getAcc();
			avgOOV += pair.getR().getAccOOV();
		}
		
		output +=       "------------------------------------------\n";
		// Compute average values
		output += "Avg  |  " + formatter.format((avgAcc / gntLanguagesPerformance.size())*100) + 
				" |  " + formatter.format((avgOOV / gntLanguagesPerformance.size())*100);
		
		return output;
	}
	
	public String toMDPString() {
		String output = "Lang |  Acc   |  OOV   |  INV   |  Tok/Sec\n";
		output +=       "------------------------------------------\n";
		DecimalFormat formatter = new DecimalFormat("#0.00");
		double avgUnAcc = 0.0;
		double avgLabAcc = 0.0;
		
		for (Pair<String,MDPperformance> pair : mdpLanguagesPerformance){
			output += pair.getL() + "   | " + pair.getR().toString() + "\n";
			avgUnAcc += pair.getR().getUnlabeledAcc();
			avgLabAcc += pair.getR().getLabeledAcc();
		}
		
		output +=       "------------------------------------------\n";
		// Compute average values
		output += "Avg  |  " + formatter.format((avgUnAcc / gntLanguagesPerformance.size())*100) + 
				" |  " + formatter.format((avgLabAcc / gntLanguagesPerformance.size())*100);
		
		return output;
	}

}

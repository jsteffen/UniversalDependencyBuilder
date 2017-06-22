package com.gn.performance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import de.dfki.mlt.gnt.data.Pair;

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
		String output = StringUtils.rightPad("Lang", 14, ' ') +" |  Acc   |  OOV   |  INV   |  Tok/Sec\n";
		output +=       "------------------------------------------\n";
		DecimalFormat formatter = new DecimalFormat("#0.00");
		double avgAcc = 0.0;
		double avgOOV = 0.0;
		
		for (Pair<String,GNTperformance> pair : gntLanguagesPerformance){
			output += StringUtils.rightPad(pair.getLeft(), 14, ' ')  + " | " + pair.getRight().toString() + "\n";
			avgAcc += pair.getRight().getAcc();
			avgOOV += pair.getRight().getAccOOV();
		}
		
		output +=       "------------------------------------------\n";
		// Compute average values
		output += StringUtils.rightPad("Avg", 14, ' ') +" | " + formatter.format((avgAcc / gntLanguagesPerformance.size())*100) + 
				" |  " + formatter.format((avgOOV / gntLanguagesPerformance.size())*100);
		output += "\n#Languages: "+ gntLanguagesPerformance.size();
		
		return output;
	}
	
	public String toMDPString() {
		String output = StringUtils.rightPad("Lang", 14, ' ') +" |  UAS   |  LAS   |  Speed tot. \n";
		output +=       "--------------------------------------------\n";
		DecimalFormat formatter = new DecimalFormat("#0.00");
		double avgUnAcc = 0.0;
		double avgLabAcc = 0.0;
		
		for (Pair<String,MDPperformance> pair : mdpLanguagesPerformance){
			output += StringUtils.rightPad(pair.getLeft(), 14, ' ')  + " | " + pair.getRight().toString() + "\n";
			avgUnAcc += pair.getRight().getUnlabeledAcc();
			avgLabAcc += pair.getRight().getLabeledAcc();
		}
		
		output +=       "--------------------------------------------\n";
		// Compute average values
		output += StringUtils.rightPad("Avg", 14, ' ') + " | " + formatter.format((avgUnAcc / mdpLanguagesPerformance.size())*100) + 
				" |  " + formatter.format((avgLabAcc / mdpLanguagesPerformance.size())*100);
		output += "\n#Languages: "+ mdpLanguagesPerformance.size();
		return output;
	}

}

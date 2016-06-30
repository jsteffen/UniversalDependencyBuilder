package com.gn.performance;

import java.text.DecimalFormat;

import corpus.EvalConllFile;
import tagger.GNTagger;

public class GNTperformance {
	private double acc;
	private double accOOV;
	private double accInV;
	private long tokenPerSec = 0;
	
	
	public long getTokenPerSec() {
		return tokenPerSec;
	}
	public void setTokenPerSec(long tokenPerSec) {
		this.tokenPerSec = tokenPerSec;
	}
	public double getAcc() {
		return acc;
	}
	public void setAcc(double acc) {
		this.acc = acc;
	}
	public double getAccOOV() {
		return accOOV;
	}
	public void setAccOOV(double accOOV) {
		this.accOOV = accOOV;
	}
	public double getAccInV() {
		return accInV;
	}
	public void setAccInV(double accInV) {
		this.accInV = accInV;
	}
	
	public GNTperformance(){
		acc = EvalConllFile.acc;
		accOOV = EvalConllFile.accOOV;
		accInV = EvalConllFile.accInV;
		tokenPerSec = GNTagger.tokenPersec;
	}
	
	
	public String toString(){
		String output = "";
		DecimalFormat formatter = new DecimalFormat("#0.00");

		output += " " + formatter.format(acc*100) +" | ";
		output += " " + formatter.format(accOOV*100) +" | ";
		output += " " + formatter.format(accInV*100) +" | ";
		output += " " + tokenPerSec;
		return output;
	}


	
}

package com.gn.performance;

import java.text.DecimalFormat;

import de.dfki.mlt.gnt.corpus.ConllEvaluator;

public class GNTperformance {
	private double acc;
	private double accOOV;
	private double accInV;
	
	
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
	
	public GNTperformance(ConllEvaluator evaluator){

	  this.acc = evaluator.getAcc();
	  this.accOOV = evaluator.getAccOOV();
	  this.accInV = evaluator.getAccInV();
	}
	
	
	public String toString(){
		String output = "";
		DecimalFormat formatter = new DecimalFormat("#0.00");

		output += " " + formatter.format(acc*100) +" | ";
		output += " " + formatter.format(accOOV*100) +" | ";
		output += " " + formatter.format(accInV*100) +" | ";
		return output;
	}


	
}

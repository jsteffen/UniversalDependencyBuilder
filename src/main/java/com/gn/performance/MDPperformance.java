package com.gn.performance;

import java.text.DecimalFormat;

import de.dfki.lt.mdparser.eval.Eval;
import de.dfki.lt.mdparser.parser.Parser;

public class MDPperformance {
	private double unlabeledAcc;
	private double labeledAcc;
	private double speed;
	
	public double getUnlabeledAcc() {
		return unlabeledAcc;
	}
	public void setUnlabeledAcc(double unlabeledAcc) {
		this.unlabeledAcc = unlabeledAcc;
	}
	public double getLabeledAcc() {
		return labeledAcc;
	}
	public void setLabeledAcc(double labeledAcc) {
		this.labeledAcc = labeledAcc;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public MDPperformance(){
		this.speed = Parser.time;
	}
	
	public MDPperformance(Eval eval){
		this.speed = Parser.time;
		this.unlabeledAcc = eval.getParentsAccuracy();
		this.labeledAcc = eval.getLabelsAccuracy();
	}
	
	public String toString(){
		String output = "";
		DecimalFormat formatter = new DecimalFormat("#0.00");

		output += " " + formatter.format(this.unlabeledAcc*100) +" | ";
		output += " " + formatter.format(this.labeledAcc*100) +" | ";
		output += " " + this.speed;
		return output;
	}
	

}

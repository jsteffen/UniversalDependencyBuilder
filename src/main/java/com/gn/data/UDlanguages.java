package com.gn.data;

import java.util.ArrayList;
import java.util.List;

import data.Pair;

public class UDlanguages {

	private static List<Pair<String,String>> languages = new ArrayList<Pair<String,String>>();

	public static List<Pair<String,String>> getLanguages(){
		languages.add(new Pair<String,String>("Arabic", "ar"));
		languages.add(new Pair<String,String>("Bulgarian", "bg"));
		languages.add(new Pair<String,String>("Czech", "cs"));
		languages.add(new Pair<String,String>("Danish", "da"));
		languages.add(new Pair<String,String>("German", "de"));
		languages.add(new Pair<String,String>("English", "en"));
		languages.add(new Pair<String,String>("Spanish", "es"));
		languages.add(new Pair<String,String>("Basque", "eu"));
		languages.add(new Pair<String,String>("Persian", "fa"));
		languages.add(new Pair<String,String>("Finnish", "fi"));
		languages.add(new Pair<String,String>("French", "fr"));
		languages.add(new Pair<String,String>("Hebrew", "he"));
		languages.add(new Pair<String,String>("Hindi", "hi"));
		languages.add(new Pair<String,String>("Croatian", "hr"));
		languages.add(new Pair<String,String>("Indonesian", "id"));
		languages.add(new Pair<String,String>("Italian", "it"));
		languages.add(new Pair<String,String>("Dutch", "nl"));
		languages.add(new Pair<String,String>("Norwegian", "no"));
		languages.add(new Pair<String,String>("Polish", "pl"));
		languages.add(new Pair<String,String>("Portuguese", "pt"));
		languages.add(new Pair<String,String>("Slovenian", "sl"));
		languages.add(new Pair<String,String>("Swedish", "sv"));
		return languages;
	}

}

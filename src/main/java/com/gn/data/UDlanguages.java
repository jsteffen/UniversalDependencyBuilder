package com.gn.data;

import java.util.ArrayList;
import java.util.List;

import de.dfki.mlt.gnt.data.Pair;

public class UDlanguages {

	public static String conlluPath = null;
	public static String conllPath = null;
	public static String version = null;

	public static boolean ignore = false;

	public static List<Pair<String,String>> languages = new ArrayList<Pair<String,String>>();

	public static void setVersion_1_2(){
		conlluPath = "/Users/gune00/data/UniversalDependencies/ud-treebanks-v1.2/";
		conllPath = "/Users/gune00/data/UniversalDependencies/conll/";
		version = "1_2";
	}

	public static void setVersion_1_3(){
		conlluPath = "/Users/gune00/data/UniversalDependencies/ud-treebanks-v1.3/";
		conllPath = "/Users/gune00/data/UniversalDependencies/conll3/";
		version = "1_3";	
	}

	private static List<Pair<String,String>> addLanguages_1_2(){
		setVersion_1_2();
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

	private static List<Pair<String, String>> addLanguages_1_3() {
		setVersion_1_3();
		languages.add(new Pair<String,String>("Ancient_Greek", "grc"));
		languages.add(new Pair<String,String>("Ancient_Greek-PROIEL", "grc_proiel"));
		languages.add(new Pair<String,String>("Arabic", "ar"));
		languages.add(new Pair<String,String>("Basque", "eu"));
		languages.add(new Pair<String,String>("Bulgarian", "bg"));
		languages.add(new Pair<String,String>("Catalan", "ca"));
		languages.add(new Pair<String,String>("Chinese", "zh"));
		languages.add(new Pair<String,String>("Croatian", "hr"));
		languages.add(new Pair<String,String>("Czech", "cs"));
		languages.add(new Pair<String,String>("Czech-CAC", "cs_cac"));
		languages.add(new Pair<String,String>("Czech-CLTT", "cs_cltt"));
		languages.add(new Pair<String,String>("Danish", "da"));
		languages.add(new Pair<String,String>("Dutch", "nl"));
		languages.add(new Pair<String,String>("Dutch-LassySmall", "nl_lassysmall"));
		languages.add(new Pair<String,String>("English", "en"));
		//	languages.add(new Pair<String,String>("English-ESL", "en_esl")); // causes very low recall; also not used by Google
		languages.add(new Pair<String,String>("English-LinES", "en_lines"));
		languages.add(new Pair<String,String>("Estonian", "et"));
		languages.add(new Pair<String,String>("Finnish", "fi"));
		languages.add(new Pair<String,String>("Finnish-FTB", "fi_ftb"));
		languages.add(new Pair<String,String>("French", "fr"));
		languages.add(new Pair<String,String>("Galician", "gl"));
		languages.add(new Pair<String,String>("German", "de"));
		languages.add(new Pair<String,String>("Gothic", "got"));
		languages.add(new Pair<String,String>("Greek", "el"));
		languages.add(new Pair<String,String>("Hebrew", "he"));
		languages.add(new Pair<String,String>("Hindi", "hi"));
		languages.add(new Pair<String,String>("Hungarian", "hu"));
		languages.add(new Pair<String,String>("Indonesian", "id"));
		languages.add(new Pair<String,String>("Irish", "ga"));
		languages.add(new Pair<String,String>("Italian", "it"));
		// languages.add(new Pair<String,String>("Japanese-KTC", "ja_ktc")); // text corpus needed; ; also not used by Google
		languages.add(new Pair<String,String>("Kazakh", "kk"));
		languages.add(new Pair<String,String>("Latin", "la"));
		languages.add(new Pair<String,String>("Latin-ITTB", "la_ittb"));
		// Latin-PROIEL causes unvalid label error in MDP parser and LibLinear, so ignore eventually
		if (UDlanguages.ignore){
			languages.add(new Pair<String,String>("Latin-PROIEL", "la_proiel")); 
		};
		languages.add(new Pair<String,String>("Latvian", "lv"));
		languages.add(new Pair<String,String>("Norwegian", "no"));
		languages.add(new Pair<String,String>("Old_Church_Slavonic", "cu"));
		languages.add(new Pair<String,String>("Persian", "fa"));
		languages.add(new Pair<String,String>("Polish", "pl"));
		languages.add(new Pair<String,String>("Portuguese", "pt"));
		languages.add(new Pair<String,String>("Portuguese-BR", "pt_br"));
		languages.add(new Pair<String,String>("Romanian", "ro"));
		languages.add(new Pair<String,String>("Russian", "ru"));
		languages.add(new Pair<String,String>("Russian-SynTagRus", "ru_syntagrus"));
		languages.add(new Pair<String,String>("Slovenian", "sl"));
		languages.add(new Pair<String,String>("Slovenian-SST", "sl_sst"));
		languages.add(new Pair<String,String>("Spanish", "es"));
		languages.add(new Pair<String,String>("Spanish-AnCora", "es_ancora"));
		languages.add(new Pair<String,String>("Swedish", "sv"));
		languages.add(new Pair<String,String>("Swedish-LinES", "sv_lines"));
		languages.add(new Pair<String,String>("Tamil", "ta"));
		languages.add(new Pair<String,String>("Turkish", "tr"));
		return languages;
	}

	public static void setUdVersion(String udVersion){
		switch (udVersion){
		case "1_2": UDlanguages.setVersion_1_2();
		case "1_3": UDlanguages.setVersion_1_3();
		}
	}

	public static List<Pair<String,String>> addLanguages(){
		switch (UDlanguages.version){
		case "1_2": return addLanguages_1_2();
		case "1_3": return addLanguages_1_3();
		default: return null;
		}
	}
}

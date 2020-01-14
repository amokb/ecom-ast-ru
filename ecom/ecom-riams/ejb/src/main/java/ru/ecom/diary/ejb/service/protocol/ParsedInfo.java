package ru.ecom.diary.ejb.service.protocol;

import java.util.List;

public class ParsedInfo {

	/** Номер пробирки */
	public String getBarcode() {return theBarcode;}
	public void setBarcode(String aBarcode) {theBarcode = aBarcode;}
	/** Номер пробирки */
	private String theBarcode;
	
	/** Список показателей */
	public List<ParsedInfoResult> getResults() {return theResults;}
	public void setResults(List<ParsedInfoResult> aResults) {theResults = aResults;}
	/** Список показателей */
	private List<ParsedInfoResult> theResults;

}

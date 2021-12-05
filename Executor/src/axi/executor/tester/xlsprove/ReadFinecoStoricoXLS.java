package axi.executor.tester.xlsprove;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import axi.executor.dto.StoricoDatiFineco;

public class ReadFinecoStoricoXLS {
	ArrayList<StoricoDatiFineco> StoricoDatiFinecoList = new ArrayList<>();
	
	/**
	 * 
	 * @param xlsFile
	 * @throws IOException
	 * @throws ParseException
	 */
	void process(File xlsFile) throws IOException, ParseException {
		Document doc = Jsoup.parse(xlsFile,null);

		Element table = doc.select("table").get(0); //select the first table.
		Elements rows = table.select("tr");

		for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
		    Element row = rows.get(i);
		    Elements cols = row.select("td");

		    StoricoDatiFinecoList.add(new StoricoDatiFineco(
		    		cols.get(StoricoDatiFineco.FIELD_IDX_DATA).text(),
		    		cols.get(StoricoDatiFineco.FIELD_IDX_TIME).text(),
		    		cols.get(StoricoDatiFineco.FIELD_IDX_APERTURA).text(),
		    		cols.get(StoricoDatiFineco.FIELD_IDX_MASSIMO).text(),
		    		cols.get(StoricoDatiFineco.FIELD_IDX_MINIMO).text(),
		    		cols.get(StoricoDatiFineco.FIELD_IDX_CHIUSURA).text(),
		    		cols.get(StoricoDatiFineco.FIELD_IDX_VARPERCENTUALE).text(),
		    		cols.get(StoricoDatiFineco.FIELD_IDX_VOLUME).text()));
		}
	}
	
	/**
	 * 
	 */
	void dispayData() {
		System.out.println(StoricoDatiFineco.printFieldHeader());
		for (StoricoDatiFineco d : StoricoDatiFinecoList) {
			System.out.println(d.toCsv(","));
		}
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		try {
			File fileHttpSource = new File("wrk\\CLF2_M1_20211203.xls");
			ReadFinecoStoricoXLS p = new ReadFinecoStoricoXLS();
			p.process(fileHttpSource);
			p.dispayData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package axi.executor.tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class Tester {
	//float[] stockData;
	float tickValue = 10;
	float commissionValue = 0.079f;
	static String inputPath;
	static String csvTestFile;
	List<String[]> stockData;
	
	float floatRound2Dec(float f) {
		return (float)Math.round(f*100)/100;
	}
	
	void calculate() throws ParseException {
		NumberFormat nf =new DecimalFormat("#.00",  DecimalFormatSymbols.getInstance(Locale.ITALIAN));
		Float f1=null, f2=null;

		float maxDiff = 0f;
		int maxDiffx = 0;
		int stockDataSize = stockData.size();
		for(int x=1;x<stockDataSize;x++) {
			float diff=0f;
			for(int i=0;i<stockDataSize;i=i+x) {
				f2 = f1;
				f1 = nf.parse(stockData.get(i)[2]).floatValue();
				if(f1 != null && f2 != null){diff += floatRound2Dec(Math.abs(f1-f2)-commissionValue);}
				//System.out.println("("+ i +") : "+floatRound2Dec(diff));
			}
			if(maxDiff<diff) {maxDiff=diff; maxDiffx=x;}
			System.out.println("x("+ x +") : "+floatRound2Dec(diff));
		}
		System.out.println("maxDiff("+maxDiffx+") -  € : "+floatRound2Dec(maxDiff)*1000);
		 //System.out.println("Margine diff € : "+floatRound2Dec(diff)*1000);
	}
	
	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CsvException
	 * @throws ParseException 
	 */
	void process() throws FileNotFoundException, IOException, CsvException, ParseException {
		Path testFile = Paths.get(inputPath + File.separator + csvTestFile);
		readCSV(testFile);
		calculate();
	}
	
	/**
	 * 
	 * @param fileCSV
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CsvException
	 */
	void readCSV(Path fileCSV) throws FileNotFoundException, IOException, CsvException {
		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (var br = Files.newBufferedReader(fileCSV,  StandardCharsets.UTF_8);
                var reader = new CSVReaderBuilder(br).withCSVParser(parser)
                        .build())  {
			  stockData = reader.readAll();
			  //stockData.forEach(x -> System.out.println(Arrays.toString(x)));
		  }
		  
		  System.out.println("Found : " + stockData.size() + " records - File: " + fileCSV);
	}
	
	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CsvException
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, CsvException, ParseException {
		inputPath = "C:\\WORK-AREA\\DEVELOP-AREA\\AXI\\DDE_CLIENT_SERVER\\DDE\\DIST\\DDE_CLIENT\\output";
		csvTestFile = "FBTP1221_20211025_9-18.csv";
		//csvTestFile = "FGBL1221_20211025_9-18.csv";
		new Tester().process();
		
	}
}

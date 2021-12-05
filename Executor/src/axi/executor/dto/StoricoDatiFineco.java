package axi.executor.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StoricoDatiFineco {
	//public static final String FORMATO_DATA_ORA = "dd/MM/yyyy HH:mm:ss";
	public static final SimpleDateFormat DATATIME_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	public static final SimpleDateFormat DATATIME_FORMAT_CSV = new SimpleDateFormat();

	public static int FIELD_IDX_DATA = 0; 
	public static int FIELD_IDX_TIME = 1;
	public static int FIELD_IDX_APERTURA = 2; 
	public static int FIELD_IDX_MASSIMO = 3; 
	public static int FIELD_IDX_MINIMO = 4; 
	public static int FIELD_IDX_CHIUSURA = 5;
	public static int FIELD_IDX_VARPERCENTUALE = 6; 
	public static int FIELD_IDX_VOLUME = 7;
	
	
	Date data;
	Float apertura;
	Float massimo;
	Float minimo;
	Float chiusura;
	Float varPercentuale;
	Integer volume;

	public StoricoDatiFineco(
			Date data, 
			Float apertura, 
			Float massimo, 
			Float minimo, 
			Float chiusura,
			Float varPercentuale, 
			Integer volume) {
		super();
		this.data = data;
		this.apertura = apertura;
		this.massimo = massimo;
		this.minimo = minimo;
		this.chiusura = chiusura;
		this.varPercentuale = varPercentuale;
		this.volume = volume;
	}

	public StoricoDatiFineco(
			String data, 
			String time,
			String apertura, 
			String massimo, 
			String minimo, 
			String chiusura,
			String varPercentuale, 
			String volume) throws ParseException {
		super();
		this.data = DATATIME_FORMAT.parse(data + " " + time);
		this.apertura = Float.parseFloat(apertura.replace(",", "."));
		this.massimo = Float.parseFloat(massimo.replace(",", "."));
		this.minimo = Float.parseFloat(minimo.replace(",", "."));
		this.chiusura = Float.parseFloat(chiusura.replace(",", "."));
		this.varPercentuale = Float.parseFloat(varPercentuale.replace(",", "."));
		this.volume = Integer.parseInt(volume);
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Float getApertura() {
		return apertura;
	}

	public void setApertura(Float apertura) {
		this.apertura = apertura;
	}

	public Float getMassimo() {
		return massimo;
	}

	public void setMassimo(Float massimo) {
		this.massimo = massimo;
	}

	public Float getMinimo() {
		return minimo;
	}

	public void setMinimo(Float minimo) {
		this.minimo = minimo;
	}

	public Float getChiusura() {
		return chiusura;
	}

	public void setChiusura(Float chiusura) {
		this.chiusura = chiusura;
	}

	public Float getVarPercentuale() {
		return varPercentuale;
	}

	public void setVarPercentuale(Float varPercentuale) {
		this.varPercentuale = varPercentuale;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return DATATIME_FORMAT.format(data) + " " +this.apertura + " " +this.massimo + " " +this.minimo + " " +this.chiusura + " " +this.varPercentuale + " " +this.volume;
	}

	public static String printFieldHeader() {
		return "data, apertura, massimo, minimo, chiusura, varPercentuale, volume";
	}
	
	public String toCsv() {
		return toCsv(",");
	}

	public String toCsv(String csvSeparator) {
		DATATIME_FORMAT_CSV.applyPattern("dd/MM/yy" + csvSeparator + "HH:mm:ss");
		return DATATIME_FORMAT_CSV.format(data) + csvSeparator + this.apertura + csvSeparator + this.massimo + csvSeparator + this.minimo + csvSeparator + this.chiusura + csvSeparator + this.varPercentuale + csvSeparator + this.volume;
	}

}

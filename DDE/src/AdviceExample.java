/*
 * Copyright 2009 www.pretty-tools.com. All rights reserved.
 */

import com.pretty_tools.dde.ClipboardFormat;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.DDEMLException;
import com.pretty_tools.dde.client.DDEClientConversation;
import com.pretty_tools.dde.client.DDEClientEventListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Advice Example.
 *
 * @author Alexander Kozlov
 */
public class AdviceExample
{
    private static final String SERVICE = "FDF";
    private static final String TOPIC = "Q";
    //private static final String ITEM = "FBTP0621.NaE;last";
    
    static SimpleDateFormat dayFormat =new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat timeFormat =new SimpleDateFormat("HH:mm:ss");
    
    static File fileName;
	static String item, item2;
	static List<String> itemList = new ArrayList<String>();
	static Float dataCache = null;
	
    public static void main(String[] args){
    	// TEST
//    	try {
//    		playTicSound("152,4");
//			Thread.sleep(1000);
//			playTicSound("152,5");
//			Thread.sleep(1000);
//			playTicSound("152,4");
//			Thread.sleep(1000);
//			playTicSound("152,4");
//		} catch (InterruptedException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
    	
    	
        try{       
        	
        	if(args.length<2) {
        		System.err.println("Errore parametri mancanti - Sintassi: FDF_Reader <Output File Name> <Item> ");
        		System.err.println("                             Esempio: FDF_Reader FBTP.csv FBTP0621.NaE;last ");
        		System.exit(1);
        	} else {
        		fileName = new File(args[0]);        		
        		item = args[1];
        		//item2 = args[2];
        		for(int i=1; i<args.length; i++) {itemList.add(args[i]);}
        	}
            // event to wait disconnection
            final CountDownLatch eventDisconnect = new CountDownLatch(1);

            // DDE client
            final DDEClientConversation conversation = new DDEClientConversation();
            // We can use UNICODE format if server prefers it
            //conversation.setTextFormat(ClipboardFormat.CF_UNICODETEXT);

            conversation.setEventListener(new DDEClientEventListener(){
                public void onDisconnect(){
                    System.out.println("onDisconnect()");
                    eventDisconnect.countDown();
                }

                public void onItemChanged(String topic, String item, String data){
                	try {
						persistData(fileName, data);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                	
                    System.out.println("onItemChanged(" + topic + "," + item + "," + data + ")");
                    try{
                        if ("stop".equalsIgnoreCase(data)) {
                            conversation.stopAdvice(item);
                            System.out.println("server stop signal (" + topic + "," + item + "," + data + ")");
                        }
                    }
                    catch (DDEException e){
                        System.out.println("Exception: " + e);
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("Connecting...");
            conversation.connect(SERVICE, TOPIC);
            for(String it : itemList) {conversation.startAdvice(it);}
//            conversation.startAdvice(item);
//            conversation.startAdvice(item2);

            System.out.println("Waiting event...");
            eventDisconnect.await();
            System.out.println("Disconnecting...");
            conversation.disconnect();
            System.out.println("Exit from thread");
        }
        catch (DDEMLException e){
            System.out.println("DDEMLException: 0x" + Integer.toHexString(e.getErrorCode()) + " " + e.getMessage());
        }
        catch (DDEException e){
            System.out.println("DDEClientException: " + e.getMessage());
        }
        catch (Exception e){
        	e.printStackTrace();
            //System.out.println("Exception: " + e);
        }
    }
    
    
    private static void persistData(File fileName, String data) throws IOException {
		if(!fileName.getParentFile().exists()) { 
			if(fileName.getParentFile().mkdirs()) {
				System.out.println("File di output dei dati : "+ fileName.getAbsolutePath());
			} else {
        		System.err.println("Errore - Impossibile creare il file : "+ fileName.getAbsolutePath());
        		System.exit(2);
			}
		};
    	String day = dayFormat.format(new Date());
    	String time = timeFormat.format(new Date());
    	FileWriter fw = new FileWriter(fileName, true);
    	fw.write(day +";" + time + ";" + data.replace(";", "-") +"\n");
    	fw.close();
    	
    	playTicSound(data);
    	
    }
    
    private static void playTicSound(String data) {
    	Float fd = Float.parseFloat(data.replace(",", "."));
    	try {
    		if(fd != null && dataCache != null) {
    			if(fd > dataCache) {
    				TicSong.songSignal(1);
    			} else if(fd < dataCache) {
    				TicSong.songSignal(-1);
    			} else {
    				TicSong.songSignal(0);
    			}
    		}
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}

    	dataCache = fd;
    }
}


import javax.sound.sampled.*;

public class TicSong implements Runnable{

	public static float SAMPLE_RATE = 32000f; // Espresso in Hz
	public static int TONE_MSECS = 50 * (int)SAMPLE_RATE / 1000 ;
	public static int VOLUME = 1 ;
	public int mode = 0; 
	public static int FREQUENZA_TONE_1 = 500;
	public static int FREQUENZA_TONE_2 = 1000;
	public static int FREQUENZA_TONE_3 = 2000;
	public static byte[][] tone1 = new byte[3][TONE_MSECS];
	
	static AudioFormat af = new AudioFormat(
			SAMPLE_RATE, // sampleRate
			8,           // sampleSizeInBits
			1,           // channels
			true,        // signed
			false);      // bigEndian
	
	static SourceDataLine sdl;
	
	static {
		for (int i=0; i < TONE_MSECS; i++) {
			//double angle = i / (SAMPLE_RATE / FREQUENZA_TONE_1) * 2.0 * Math.PI;
			tone1[0][i] = (byte)(Math.sin(i / (SAMPLE_RATE / FREQUENZA_TONE_1) * 2.0 * Math.PI) * 127.0 * VOLUME);
			tone1[1][i] = (byte)(Math.sin(i / (SAMPLE_RATE / FREQUENZA_TONE_2) * 2.0 * Math.PI) * 127.0 * VOLUME);
			tone1[2][i] = (byte)(Math.sin(i / (SAMPLE_RATE / FREQUENZA_TONE_3) * 2.0 * Math.PI) * 127.0 * VOLUME);
		}
		//System.out.println("FINE preset toni");
		try {
			sdl = AudioSystem.getSourceDataLine(af);
			sdl.open(af);
			sdl.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public TicSong(int mode) {
		super();
		this.mode = mode;
	}

	public static void tone(int hz, int msecs) 
			throws LineUnavailableException 
	{
		tone(hz, msecs, 1.0);
	}

	public static void tone(int hz, int msecs, double vol)
			throws LineUnavailableException 
	{
		byte[] buf = new byte[1];
		AudioFormat af = 
				new AudioFormat(
						SAMPLE_RATE, // sampleRate
						8,           // sampleSizeInBits
						1,           // channels
						true,        // signed
						false);      // bigEndian
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		for (int i=0; i < msecs*8; i++) {
			double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
			buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
			sdl.write(buf,0,1);
		}
		sdl.drain();
		sdl.stop();
		sdl.close();
	}
	
	public static void tone2(int toneIdx)
			throws LineUnavailableException 
	{
//		AudioFormat af = new AudioFormat(
//				SAMPLE_RATE, // sampleRate
//				8,           // sampleSizeInBits
//				1,           // channels
//				true,        // signed
//				false);      // bigEndian
		
//		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		
		//sdl.open(af);
		sdl.start();

		sdl.write(tone1[toneIdx-1],0,TONE_MSECS);
		
		sdl.drain();
		sdl.flush();
		sdl.stop();
		//sdl.close();
	}
	
	public static void songSignal(int mode) {
		new Thread(new TicSong(mode)).start();
	}

	@Override
	public void run() {
		try {
			if(mode==1) {
				TicSong.tone2(1);
				TicSong.tone2(2);
				TicSong.tone2(3);
			} else if(mode==-1){
				TicSong.tone2(3);
				TicSong.tone2(2);
				TicSong.tone2(1);	
			} else {
				TicSong.tone2(2);
				TicSong.tone2(2);
				TicSong.tone2(2);
			}
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
        int[] sec = {1,1,1,0,0,-1,-1,1,-1,0,0,0,0};  
		for (int i = 0; i < sec.length; i++) {
			songSignal(sec[i]);	
			Thread.sleep(1000);
		}
	}

}

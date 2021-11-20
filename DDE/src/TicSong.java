

import javax.sound.sampled.*;

public class TicSong implements Runnable{

	public static float SAMPLE_RATE = 8000f;
	public int mode = 0; 

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
	
	public static void songSignal(int mode) {
		new Thread(new TicSong(mode)).start();
	}

	public static void main(String[] args) throws Exception {
        int[] sec = {1,1,1,0,0,-1,-1,1,-1,0,0,0,0};  
		for (int i = 0; i < sec.length; i++) {
			songSignal(sec[i]);	
			Thread.sleep(1000);
		}
	}

	@Override
	public void run() {
		int tmp = 50;
		try {
			if(mode==1) {
				TicSong.tone(500,tmp);
				TicSong.tone(1000,tmp);
				TicSong.tone(2000,tmp);
			} else if(mode==-1){
				TicSong.tone(2000,tmp);
				TicSong.tone(1000,tmp);
				TicSong.tone(500,tmp);	
			} else {
				TicSong.tone(1000,tmp);
				TicSong.tone(1000,tmp);
				TicSong.tone(1000,tmp);
			}
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

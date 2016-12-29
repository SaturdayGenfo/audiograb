import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;
public class Feed {
	private static void captureAudio(){
		
		Scanner in = new Scanner(System.in);
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format =  new AudioFormat(sampleRate, 
		  sampleSizeInBits, channels, signed, bigEndian);
		Mixer.Info[] mixerInfo = null;
	    try{
	      mixerInfo = AudioSystem.getMixerInfo();
	      System.out.println("Available mixers:");
	      for(int cnt = 0; cnt < mixerInfo.length; cnt++){
	      	System.out.println(cnt+":"+ mixerInfo[cnt].
	      	                              getName());
	      }
	    }
	    finally{}
	    int chosenMixer = in.nextInt();
	    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	    TargetDataLine line = null;
	    try {
			line = AudioSystem.getTargetDataLine(format, mixerInfo[chosenMixer]);
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	    try {
			line.open(format);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    line.start();
	    int bufferSize = (int)format.getSampleRate() * format.getFrameSize();
	    byte buffer[] = new byte[bufferSize];
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    boolean externalTrigger = true;
		while (externalTrigger ) {
	      int count = line.read(buffer, 0, buffer.length);
	      if (count > 0) {
	        out.write(buffer, 0, count);
	      }
	    }
	    try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		
		
	}
}

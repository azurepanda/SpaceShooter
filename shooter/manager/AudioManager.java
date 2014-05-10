package manager;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager implements Runnable{
	
	private final String[] songs = { "song1.wav", "song1.wav", "song1.wav", "song1.wav" };
	private Clip clip;

	public AudioManager(){
		
	}
	
	public void newSong(){
		Random r = new Random();
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sfx/" + songs[r.nextInt(songs.length)]));
			clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		if(clip == null || !clip.isRunning()){
			newSong();
		}		
	}
	
	public void playBlast(){
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sfx/blast2.wav"));
			Clip clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playShot(){
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sfx/shot.wav"));
			Clip clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSound {
	private static File bombSound = new File("src//audio//bombSound.wav");
	private static File clickSound = new File("src//audio//clickSound.wav");
	private static File illegalInputSound = new File("src//audio//IllegalInput.wav");
	private static File backgroundMusic = new File("src//audio//backgroundMusic.wav");
	private static File flagSound = new File("src//audio//flagSound.wav");
	private static File winSound = new File("src//audio//winSound.wav");
	
	
	public static void playFlagSound() {
		playAudio(flagSound);
	}
	
	public static void playMineSound() {
		playAudio(bombSound);
	}
	
	public static void playIllegalInputSound() {
		playAudio(illegalInputSound);
	}
	
	public static void playWinSound() {
		playAudio(winSound);
	}
	
	public static void playClickSound() {
		playAudio(clickSound);
	}
	
	public static File getBackgroundMusic() {
		return backgroundMusic;
	}
	public static void playAudio(File Sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();

		} catch (Exception e) {}
	}

	public static Clip playAudioloop(File Sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			return clip;
		} catch (Exception e) {
			return null;
		}
	}

	public static void stopAudioloop(Clip clip) {

		try {
			clip.stop();
		} catch (Exception e) {}
	}
	
	
}



import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSound {
	
	public GameSound() {
		
	}
	
	public static void playAudio(File Sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();

		} catch (Exception e) {
		}

	}

	public static Clip playAudioloop(File Sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			return clip;
		} catch (Exception e) {
		}
		return null;
	}

	public static void stopAudioloop(Clip clip) {

		try {
			clip.stop();
		} catch (Exception e) {
		}
	}
}

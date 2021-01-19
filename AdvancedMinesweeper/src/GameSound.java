import javafx.scene.media.AudioClip;

public class GameSound {
	private static AudioClip bombSound = new AudioClip(ClassLoader.getSystemResource("audio/bombSound.wav").toString());
	private static AudioClip clickSound = new AudioClip(ClassLoader.getSystemResource("audio/clickSound.wav").toString());
	private static AudioClip illegalInputSound = new AudioClip(ClassLoader.getSystemResource("audio/illegalInput.wav").toString());
	private static AudioClip backgroundMusic = new AudioClip(ClassLoader.getSystemResource("audio/backgroundMusic.wav").toString());
	private static AudioClip flagSound = new AudioClip(ClassLoader.getSystemResource("audio/flagSound.wav").toString());
	private static AudioClip winSound = new AudioClip(ClassLoader.getSystemResource("audio/winSound.wav").toString());
	
	
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
	
	public static AudioClip getBackgroundMusic() {
		return backgroundMusic;
	}
	public static void playAudio(AudioClip Sound) {

		try {
			Sound.play();

		} catch (Exception e) {}
	}

	public static AudioClip playAudioloop(AudioClip Sound) {

		try {
			Sound.setCycleCount(AudioClip.INDEFINITE);
			Sound.play();
			return Sound;
		} catch (Exception e) {
			return null;
		}
	}

	public static void stopAudioloop(AudioClip clip) {
		try {
			clip.stop();
		} catch (Exception e) {}
	}
}



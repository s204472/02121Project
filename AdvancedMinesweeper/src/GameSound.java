import javafx.scene.media.AudioClip;

public class GameSound {
	private static AudioClip bombSound = new AudioClip(ClassLoader.getSystemResource("audio/bombSound.wav").toString());
	private static AudioClip clickSound = new AudioClip(ClassLoader.getSystemResource("audio/clickSound.wav").toString());
	private static AudioClip illegalInputSound = new AudioClip(ClassLoader.getSystemResource("audio/illegalInput.wav").toString());
	private static AudioClip backgroundMusic = new AudioClip(ClassLoader.getSystemResource("audio/backgroundMusic.wav").toString());
	private static AudioClip flagSound = new AudioClip(ClassLoader.getSystemResource("audio/flagSound.wav").toString());
	private static AudioClip winSound = new AudioClip(ClassLoader.getSystemResource("audio/winSound.wav").toString());
	private static boolean mute=false;
	private static boolean backGroundPlaying=false;
	
	public static void playFlagSound() {
		flagSound.setVolume(0.15);
		playAudio(flagSound);
	}
	
	public static void playMineSound() {
		bombSound.setVolume(0.3);
		playAudio(bombSound);
	}
	
	public static void playIllegalInputSound() {
		illegalInputSound.setVolume(0.3);
		playAudio(illegalInputSound);
	}
	
	public static void playWinSound() {
		winSound.setVolume(0.2);
		playAudio(winSound);
	}
	
	public static void playClickSound() {
		clickSound.setVolume(0.3);
		playAudio(clickSound);
	}
	
	public static AudioClip getBackgroundMusic() {
		return backgroundMusic;
	}
	public static void playAudio(AudioClip Sound) {

		try {
		if(mute) {
			Sound.setVolume(0);
		}
			Sound.play();

		} catch (Exception e) {}
	}

	public static AudioClip playAudioloop(AudioClip Sound) {

		try {
			Sound.setVolume(0.25);
			Sound.setCycleCount(AudioClip.INDEFINITE);
			Sound.play();
			backGroundPlaying=true;
			return Sound;
		} catch (Exception e) {
			return null;
		}
	}

	public static void stopAudioloop(AudioClip clip) {
		try {
			if(!mute) {
			clip.stop();
			backGroundPlaying=false;
			}
		} catch (Exception e) {}
	}
	
	public static void muteAndUnmute(AudioClip clip, boolean gameover, int displayedFields) {
		if(!mute && backGroundPlaying) {
			backGroundPlaying=false;
			clip.stop();
		}
		if(mute && !backGroundPlaying && !gameover && displayedFields!=0) {
			clip.setVolume(0.25);
			clip.setCycleCount(AudioClip.INDEFINITE);
			clip.play();
			backGroundPlaying=true;
		}
	}
	
	public static boolean getmuted() {
		return mute;
	}
	
	public static void togglemute() {
		mute = !mute;
	}
}



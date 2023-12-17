package gameplay;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundHandler {
	
	//for sound related
	public static MediaPlayer mediaPlayer;
	public static Media gameplayMusic = new Media(new File("src/sounds/wildScreenBaroque.wav").toURI().toString());
	public static Media deathSound = new Media(new File("src/sounds/deathSound.wav").toURI().toString());
	public static Media menuMusic = new Media(new File("src/sounds/The Hey Song.mp3").toURI().toString());

}

package no.torsteinv.MS2.Sound;

import java.io.ByteArrayInputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Player {

	public static void play(Sound s) throws Exception {
		AudioPlayer.player.start(new AudioStream(new ByteArrayInputStream(s.sound.toByteArray())));
	}
}

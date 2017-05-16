package tts;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.modules.synthesis.Voice;

public class TextToSpeech {

	private AudioPlayer		tts;
	private MaryInterface	marytts;

	//Constructor
	public TextToSpeech() {
		try {
			marytts = new LocalMaryInterface();

		} catch (MaryConfigurationException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Collection<Voice> getAvailableVoices() {
		return Voice.getAvailableVoices();
	}
	public void setVoice(String voice) {
		marytts.setVoice(voice);
	}
	
	public void speak(String text, float gainValue, boolean daemon, boolean join) {

		stopSpeaking();

		try (AudioInputStream audio = marytts.generateAudio(text)) {


			tts = new AudioPlayer();
			tts.setAudio(audio);
			tts.setGain(gainValue);
			tts.setDaemon(daemon);
			tts.start();
			if (join)
				tts.join();

		} catch (SynthesisException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Error saying phrase.", ex);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "IO Exception", ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Interrupted ", ex);
			tts.interrupt();
		}
	}

	/**
	 * Stop the MaryTTS from Speaking
	 */
	public void stopSpeaking() {

		if (tts != null)
			tts.cancel();
	}

}

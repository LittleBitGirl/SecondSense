package model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import marytts.modules.synthesis.Voice;
import tts.TextToSpeech;

public class SpeechCalculator {

	TextToSpeech textToSpeech = new TextToSpeech();

	// Logger (messages)
	private Logger logger = Logger.getLogger(getClass().getName());

	// Variables
	public String result;

	// Threads
	Thread	speechThread;
	Thread	resourcesThread;

	// LiveRecognizer
	private LiveSpeechRecognizer recognizer;

	private volatile boolean recognizerStopped = true;

	/**
	 * Constructor
	 */
	public SpeechCalculator() {

		logger.log(Level.INFO, "Loading..\n");

		// Configuration
		Configuration configuration = new Configuration();

		// Load model voice from a jar file (spinx)
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		//Load dictionary of our model voice
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

		// Grammar
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);

		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}

		// Start recognition process pruning previously cached data.
		// recognizer.startRecognition(true);

		// that we have added on the class path
		Voice.getAvailableVoices().stream().forEach(voice -> System.out.println("Voice: " + voice));
		textToSpeech.setVoice("cmu-slt-hsmm");

		// Start the Thread
		// startSpeechThread()
		recognizer.startRecognition(true);
		startResourcesThread();
	}

	/**
	 * Starting the main thread of speech recognition
	 */
	public void startSpeechThread() {

		System.out.println("Entering start speech thread");

		if (speechThread != null && speechThread.isAlive())
			return;

		// initialise
		speechThread = new Thread(() -> {

			// Allocate the resources
			recognizerStopped = false;
			logger.log(Level.INFO, "You can start to speak...\n");

			try {
				while (!recognizerStopped) {
					//This method will determine the end of speech.
					SpeechResult speechResult = recognizer.getResult();
					if (speechResult != null) {

						result = speechResult.getHypothesis();
						System.out.println("You said: [" + result + "]\n");
						makeDecision(result, speechResult.getWords());
						// logger.log(Level.INFO, "You said: " + result + "\n")
					} else
						logger.log(Level.INFO, "I can't understand what you said.\n");

				}
			} catch (Exception ex) {
				logger.log(Level.WARNING, null, ex);
				recognizerStopped = true;
			}

			logger.log(Level.INFO, "SpeechThread has exited...");
		});

		// Start
		speechThread.start();

	}

	//Stop speech
	public void stopSpeechThread() {
		if (speechThread != null && speechThread.isAlive()) {
			recognizerStopped = true;
			//recognizer.stopRecognition();
		}
	}

	//start speech
	public void startResourcesThread() {

		if (resourcesThread != null && resourcesThread.isAlive())
			return;

		resourcesThread = new Thread(() -> {
			try {

				// check microphone
				while (true) {
					if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
						// logger.log(Level.INFO, "Microphone is available.\n")
					} else {
						logger.log(Level.INFO, "Microphone is not available.\n");
					}

					// Sleep some period
					Thread.sleep(350);
				}

			} catch (InterruptedException ex) {
				logger.log(Level.WARNING, null, ex);
				resourcesThread.interrupt();
			}
		});

		// Start
		resourcesThread.start();
	}
	
	public void sayWelcome(){
		textToSpeech.speak("Welcome to the second sense! Please say something to continue or press the start button.", 1.9f, false, true);
		//textToSpeech.speak(text, gainValue, daemon, join);
	}
	
	public boolean startSaid(String speech){
		if(speech.contains("start")) return true;
		else return false;
	}
	public void makeDecision(String speech, List<WordResult> speechWords) {

		if (speech.contains("how are you")) {
			textToSpeech.speak("Nice, thanx!", 1.9f, false, true);
			return;
		} else if (speech.contains("who is your boss")) {
			textToSpeech.speak("You boss", 1.5f, false, true);
		} else if (speech.contains("hey boss")) {
			textToSpeech.speak("May i have the pizza pliz", 1.4f, false, true);

		} else if (speech.contains("hello")) {
			textToSpeech.speak("Hello Friends", 1.9f, false, true);
			return;
		} else if (speech.contains("say amazing")) {
			textToSpeech.speak("WoW it's amazing!", 1.5f, false, true);
			return;
		} else if (speech.contains("what day is today")) {
			textToSpeech.speak("A good day", 1.5f, false, true);
			return;
		} else if (speech.contains("Where iz lav")){
			textToSpeech.speak("I love my mom", 1.9f, false, true);
			return;
		}else if (speech.contains("change to voice one")) {
			textToSpeech.setVoice("cmu-slt-hsmm");
			textToSpeech.speak("Done", 1.5f, false, true);
			return;
		} else if (speech.contains("change to voice two")) {
			textToSpeech.setVoice("dfki-poppy-hsmm");
			textToSpeech.speak("Done", 1.5f, false, true);
		} else if (speech.contains("change to voice three")) {
			textToSpeech.setVoice("cmu-rms-hsmm");
			textToSpeech.speak("Done", 1.5f, false, true);
		} 

	}

	/*public static void main(String[] args) {
	//
	// // // Be sure that the user can't start this application by not giving
	// // the
	// // correct entry string
	if (args.length == 1 && "SPEECH".equalsIgnoreCase(args[0]))
		new Main();
	else
		Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Give me the correct entry string..");
	
	}*/

}
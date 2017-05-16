package application;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import tts.TextToSpeech;

public class SpeechRecognition {
	
	TextToSpeech textToSpeech = new TextToSpeech();
	
	private LiveSpeechRecognizer recognizer;
	
	private Logger logger = Logger.getLogger(getClass().getName());

	private String speechRecognitionResult;
	

	private SimpleBooleanProperty ignoreSpeechRecognitionResults = new SimpleBooleanProperty(false);

	private SimpleBooleanProperty speechRecognizerThreadRunning = new SimpleBooleanProperty(false);
	
	private boolean resourcesThreadRunning;
	
	private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);

	public SpeechRecognition() {

		logger.log(Level.INFO, "Loading Speech Recognizer...\n");

		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		
		// configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin")
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);
		
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		
		startResourcesThread();
		//startSpeechRecognition();
	}
	
	public synchronized void startSpeechRecognition() {
		
		//Check lock
		if (speechRecognizerThreadRunning.get())
			logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
		else
			eventsExecutorService.submit(() -> {

				Platform.runLater(() -> {
					speechRecognizerThreadRunning.set(true);
					ignoreSpeechRecognitionResults.set(false);
				});

				recognizer.startRecognition(true);
	
				logger.log(Level.INFO, "You can start to speak...\n");
				
				try {
					while (speechRecognizerThreadRunning.get()) {
						SpeechResult speechResult = recognizer.getResult();
						
						if (!ignoreSpeechRecognitionResults.get()) {
							
							if (speechResult == null)
								logger.log(Level.INFO, "I can't understand what you said.\n");
							else {
								
								speechRecognitionResult = speechResult.getHypothesis();
								
								System.out.println("You said: [" + speechRecognitionResult + "]\n");
								
								makeDecision(speechRecognitionResult, speechResult.getWords());
								
							}
						} else
							logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
						
					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, null, ex);
					Platform.runLater(() -> speechRecognizerThreadRunning.set(false));
				}
				
				logger.log(Level.INFO, "SpeechThread has exited...");
				
			});
	}

	public synchronized void stopIgnoreSpeechRecognitionResults() {
		
		//Platform.runLater(() ->	ignoreSpeechRecognitionResults.set(false));
		ignoreSpeechRecognitionResults.set(false);
		logger.log(Level.INFO, "SpeechThread is not ignoring anymore...");
	}
	
	public synchronized void ignoreSpeechRecognitionResults() {
		//Platform.runLater(() -> ignoreSpeechRecognitionResults.set(true));
		ignoreSpeechRecognitionResults.set(true);
		logger.log(Level.INFO, "SpeechThread is ignoging...");
		
	}

	public void startResourcesThread() {

		if (resourcesThreadRunning)
			logger.log(Level.INFO, "Resources Thread already running...\n");
		else
			eventsExecutorService.submit(() -> {
				try {

					resourcesThreadRunning = true;

					while (true) {

						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
							logger.log(Level.INFO, "Microphone is not available.\n");

						Thread.sleep(350);
					}
					
				} catch (InterruptedException ex) {
					logger.log(Level.WARNING, null, ex);
					resourcesThreadRunning = false;
				}
			});
	}
	
	public void makeDecision(String speech , List<WordResult> speechWords) {
		System.out.println("Checking answers");
		if(speech.contains(Nature.answer)){
			textToSpeech.speak("Yes! You are right!", 1.9f, false, true);
			Nature.BottomLabel.setText("Well done!");
		}else{
			textToSpeech.speak("No! The answer is" + Nature.answer + ".", 1.9f, false, true);
			Nature.BottomLabel.setText("Try again!");
		}
		
	}
	
	public SimpleBooleanProperty ignoreSpeechRecognitionResultsProperty() {
		return ignoreSpeechRecognitionResults;
	}
	
	public SimpleBooleanProperty speechRecognizerThreadRunningProperty() {
		return speechRecognizerThreadRunning;
	}
	
}

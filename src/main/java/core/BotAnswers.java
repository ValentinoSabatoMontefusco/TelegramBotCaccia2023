package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.stanford.nlp.util.StringUtils;

public class BotAnswers {
	
	private static ArrayList<String> botQueries = new ArrayList<String>(
			Arrays.asList("Complimenti per avermi raggiunto sin qui, pisciabroro che non sei altro. Ma la mia missione Ã¨ troppo importante per affidarmi a chiunque. Dovrai (dovrete? xd) dimostrarmi di essere un alleato degli Avengers. Partiamo dal tuo nome. Come ti chiami?",
					"Bando ai convenevoli! Dove si Ã¨ schiantato il bombardiere dove mi ha ritrovato Nicola Furia?",
					"Mmh, corretto. Tutti sanno che il capo dell'Hydra mio nemico Ã¨ comunemente chiamato Teschio Rosso. Ma qual Ã¨ il suo vero nome?",
					"Te possino, 'sto fessa! Conosci anche il nome del mio migliore amico e alleato?",
					"In realtÃ  era Tammuzzo, ma anche questa risposta va bene. Pe' mmo Ã¨ tutto, po' virimmo che ato c'amma mette 'nda 'sto coso, statti bbuono."));
	
	private  static List<String>[] correctAnswers = new List[] {null, List.of("polo nord", "artico"), List.of("johann schmidt", "schmidt"), 
																List.of("bucky", "bucky barnes", "james buchanan barnes")};
	
	private static ArrayList<String> wrongSfottò = new ArrayList<>(Arrays.asList("Ngul e quanta fai schif! Non m'o ccr'rev...", "Vabbuò stai fore, non ci pozzo pensa\'", 
																	"Ma quante cacate rici?", "Manco Valintino è accossì fessa", "Si continui accossì 'o tesoro 'o viri co'o cannocchiale",
																	"L'amma rice? E ricimmilo. Acciriti"));
	
	
	private static ArrayList<String> closeSfottò = new ArrayList<>(Arrays.asList("Wa 'o ssa' ci stivi quasi, riprova co' quaccosa 'e simile che stai llane", 
																"Non ha' ritto proprio 'na stronzata, ci sei quasi ja", "Quasi. Non poplio. Aggiusta quaccosina",
																"Appara 'na ntecchietella e ha azzeccato, non si lontano", "Avissi sbagliato a scrive'? Controlla 'no poco"));
	
	public static String wrongAnswer() {
		
		return wrongSfottò.get(new Random().nextInt(wrongSfottò.size()));
	}
	
	public static String closeAnswer() {
		
		return closeSfottò.get(new Random().nextInt(closeSfottò.size()));
	}
	
	public static String getQuery(int index) {
		
		return botQueries.get(index);
	}
	
	public static int checkAnswer(String input, int status) {
		
		
		
		int answer_length;
		List<String> answers = correctAnswers[status];
		List<String> tokens;
		int minimumEditDistance = Integer.MAX_VALUE;
		for (String answer : answers) {
			
			answer_length = answer.split("\\s").length;
			
			tokens = tokenize(input, answer_length);
			
			
			for (String token : tokens) {
				
				minimumEditDistance = Math.min(minimumEditDistance, StringUtils.editDistance(answer, token));
				if (minimumEditDistance == 0)
					break;
			}
			
			if (minimumEditDistance == 0)
				break;
		}
		
		return minimumEditDistance;
	}
	
	public static List<String> tokenize(String input, int token_length) {
		
		String words[] = input.split("\\s");
		for (int i = 0; i < words.length; i++) {
			String newWord = words[i].toLowerCase();
			newWord.replaceAll("[^a-z0-9]", "");
			words[i] = newWord;
			
		}
		
		List<String> tokens = new ArrayList<String>();
		int i = 0;
		StringBuilder token = new StringBuilder();
		for (i = 0; i <= words.length - token_length; i++) {
			token.setLength(0);
			token.append(words[i]);
			for (int j = 1; j < token_length; j++) {
				
				token.append(" ").append(words[i+j]);
			}
			
			tokens.add(token.toString());
		}
		if (i == 0) {
			token.setLength(0);
			for (String word : words) {
				token.append(word);
			}
			tokens.add(token.toString());
		}
		
		return tokens;
		
		
	}
}

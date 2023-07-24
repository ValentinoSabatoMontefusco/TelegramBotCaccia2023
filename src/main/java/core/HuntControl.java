package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import edu.stanford.nlp.util.StringUtils;

public class HuntControl {
	
private static HashMap<Long, User> userMap = new HashMap<Long, User>();
	
	private static ArrayList<String> botQueries = new ArrayList<String>(
			Arrays.asList("Complimenti per avermi raggiunto sin qui, pisciabroro che non sei altro. Ma la mia missione è troppo importante per affidarmi a chiunque. Dovrai (dovrete? xd) dimostrarmi di essere un alleato degli Avengers. Partiamo dal tuo nome. Come ti chiami?",
					"Bando ai convenevoli! Dove si è schiantato il bombardiere dove mi ha ritrovato Nicola Furia?",
					"Mmh, corretto. Tutti sanno che il capo dell'Hydra mio nemico è comunemente chiamato Teschio Rosso. Ma qual è il suo vero nome?",
					"Te possino, 'sto fessa! Conosci anche il nome del mio migliore amico e alleato?",
					"In realtà era Tammuzzo, ma anche questa risposta va bene. Pe' mmo è tutto, po' virimmo che ato c'amma mette 'nda 'sto coso, statti bbuono."));
	
	private  static List<String>[] correctAnswers = new List[] {null, List.of("polo nord", "artico"), List.of("johann schmidt", "schmidt"), 
																List.of("bucky", "bucky barnes", "james buchanan barnes")};
	
	public static Boolean contains(Long id) {
		
		return userMap.containsKey(id);
	}
	
	public static void insertNewUser(Long id, User user ) {
		
		
		userMap.putIfAbsent(id, user);
	}
	
	public static void clear() {
		
		userMap.clear();
	}
	
	public static int checkAnswer(String input, List<String> answers) {
		
		int answer_length;
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
	
	public static SendMessage getSendMessage(Update update) {
		
		System.err.println("Mannaggia cristo?");
		Long chatId = update.getMessage().getChatId();
		String inputText = update.getMessage().getText();
		User ctxUser = userMap.get(chatId);
		System.out.println("Messaggio ricevuto da " + (ctxUser != null ? ctxUser.getName() : "unknown") + " (" + chatId + "): " + inputText);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		if (ctxUser == null) {
			ctxUser = new User("unknown", chatId, 0);
			insertNewUser(chatId, ctxUser);
			sendMessage.setText(botQueries.get(ctxUser.getStatus()));
			
		} else {
			
			switch(ctxUser.getStatus()) {
			
			case 0: ctxUser.setName(inputText);
					ctxUser.increaseStatus();
					sendMessage.setText("Appicciti " + ctxUser.getName() + "! " + botQueries.get(ctxUser.getStatus()));
					break;
					
			case 1: if(checkAnswer(inputText, correctAnswers[ctxUser.getStatus()]) == 0) {
						ctxUser.increaseStatus();
						sendMessage.setText(botQueries.get(ctxUser.getStatus()));
					} else if (checkAnswer(inputText, correctAnswers[ctxUser.getStatus()]) <= 2) {
						
						sendMessage.setText("Wa 'o ssa' ci stivi quasi, riprova co' quaccosa 'e simile che stai llane");
					}
					break;
					
			case 2: if(checkAnswer(inputText, correctAnswers[ctxUser.getStatus()]) == 0) {
						ctxUser.increaseStatus();
						sendMessage.setText(botQueries.get(ctxUser.getStatus()));
					} else if (checkAnswer(inputText, correctAnswers[ctxUser.getStatus()]) <= 2) {
						
						sendMessage.setText("Wa 'o ssa' ci stivi quasi, riprova co' quaccosa 'e simile che stai llane");
					}
					break;
					
			case 3: if(checkAnswer(inputText, correctAnswers[ctxUser.getStatus()]) == 0) {
						ctxUser.increaseStatus();
						sendMessage.setText(botQueries.get(ctxUser.getStatus()));
					} else if (checkAnswer(inputText, correctAnswers[ctxUser.getStatus()]) <= 2) {
						
						sendMessage.setText("Wa 'o ssa' ci stivi quasi, riprova co' quaccosa 'e simile che stai llane");
					}
				break;
				
			case 4: if(inputText.toLowerCase().contains("/reset")) {
						ctxUser.setStatus(1);
						sendMessage.setText(botQueries.get(ctxUser.getStatus()));
			} else {
				
				sendMessage.setText("Se vuoi resettare il bot alla prima domanda diggita il comando /reset");
				
			} break;
			
			default: System.err.println("Status utente non rikonoskiuto");
					
		}
		
	}
	
	if (sendMessage.getText() == null || sendMessage.getText().length() == 0) {
		
		sendMessage.setText("Ma manco pe' ssogno, che cazzo stai ricenno? Piensici meglio");
	}
		
		return sendMessage;
		
	
	
	}
}

	
	
	/*
	switch(ctxUser.getStatus()) {
	
	case 0: ctxUser.setName(inputText);
			ctxUser.increaseStatus();
			sendMessage.setText("Appicciti " + ctxUser.getName() + "! " + botQueries.get(ctxUser.getStatus()));
			break;
			
	case 1: if(inputText.toLowerCase().contains("polo nord") || inputText.toLowerCase().contains("artico")) {
				ctxUser.increaseStatus();
				sendMessage.setText(botQueries.get(ctxUser.getStatus()));
			}
			break;
			
	case 2: if(inputText.toLowerCase().contains("johann schmidt") || inputText.toLowerCase().contains("schmidt")) {
			ctxUser.increaseStatus();
			sendMessage.setText(botQueries.get(ctxUser.getStatus()));
			}
			break;
			
	case 3: if(inputText.toLowerCase().contains("bucky") || inputText.toLowerCase().contains("bucky barnes") || inputText.toLowerCase().contains("james buchanan barnes")) {
		ctxUser.increaseStatus();
		sendMessage.setText(botQueries.get(ctxUser.getStatus()));
		}
		break;
		
	case 4: if(inputText.toLowerCase().contains("/reset")) {
				ctxUser.setStatus(1);
				sendMessage.setText(botQueries.get(ctxUser.getStatus()));
	} else {
		
		sendMessage.setText("Se vuoi resettare il bot alla prima domanda diggita il comando /reset");
		
	} break;
	
	default: System.err.println("Status utente non rikonoskiuto");
			
}

}

if (sendMessage.getText() == null || sendMessage.getText().length() == 0) {

sendMessage.setText("E sei una merda e fai schifo. Riprova.");
}
	
	*/
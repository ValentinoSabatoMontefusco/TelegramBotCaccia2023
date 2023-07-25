package core;



import java.util.HashMap;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;



public class HuntControl {
	
private static HashMap<Long, User> userMap = new HashMap<Long, User>();
	
	
	
	public static Boolean contains(Long id) {
		
		return userMap.containsKey(id);
	}
	
	public static void insertNewUser(Long id, User user ) {
		
		
		userMap.putIfAbsent(id, user);
	}
	
	public static void clear() {
		
		userMap.clear();
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
			sendMessage.setText(BotAnswers.getQuery(ctxUser.getStatus()));
			
		} else {
			
			switch(ctxUser.getStatus()) {
			
			case 0: ctxUser.setName(inputText);
					ctxUser.increaseStatus();
					sendMessage.setText("Appicciti " + ctxUser.getName() + "! " + BotAnswers.getQuery(ctxUser.getStatus()));
					break;
					
			case 1: if(BotAnswers.checkAnswer(inputText, ctxUser.getStatus()) == 0) {    
						ctxUser.increaseStatus();
						sendMessage.setText(BotAnswers.getQuery(ctxUser.getStatus()));
					} else if (BotAnswers.checkAnswer(inputText, ctxUser.getStatus()) <= 2) {
						
						sendMessage.setText(BotAnswers.closeAnswer());
					}
					break;
					
			case 2: if(BotAnswers.checkAnswer(inputText, ctxUser.getStatus()) == 0) {
						ctxUser.increaseStatus();
						sendMessage.setText(BotAnswers.getQuery(ctxUser.getStatus()));
					} else if (BotAnswers.checkAnswer(inputText, ctxUser.getStatus()) <= 2) {
						
						sendMessage.setText(BotAnswers.closeAnswer());
					}
					break;
					
			case 3: if(BotAnswers.checkAnswer(inputText, ctxUser.getStatus()) == 0) {
						ctxUser.increaseStatus();
						sendMessage.setText(BotAnswers.getQuery(ctxUser.getStatus()));
					} else if (BotAnswers.checkAnswer(inputText, ctxUser.getStatus()) <= 2) {
						
						sendMessage.setText(BotAnswers.closeAnswer());
					}
				break;
				
			case 4: if(inputText.toLowerCase().contains("/reset")) {
						ctxUser.setStatus(1);
						sendMessage.setText(BotAnswers.getQuery(ctxUser.getStatus()));
			} else {
				
				sendMessage.setText("Se vuoi resettare il bot alla prima domanda diggita il comando /reset");
				
			} break;
			
			default: System.err.println("Status utente non rikonoskiuto");
					
		}
		
	}
	
	if (sendMessage.getText() == null || sendMessage.getText().length() == 0) {
		
		sendMessage.setText(BotAnswers.wrongAnswer());
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
package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class InfinityHuntBot extends TelegramLongPollingBot {
	
	
	private static final String USERNAME = "InfinityHuntBot";
	private static final String TOKEN = "6047256742:AAFxlU0_XW80QEpB4K_ynaX-eyms8zXOe2s";
	

	public void onUpdateReceived(Update update) {
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			System.out.println("Update ricevuto :(");
			SendMessage sendMessage = HuntControl.getSendMessage(update);
			System.err.println("Qui si è arrivati, e sendMessage è: " + sendMessage.getText());
		    		
		    try {
		      execute(sendMessage);
		    } catch (TelegramApiException e) {
		      e.printStackTrace();
		    }
		  }
		
	}
	

	public String getBotUsername() {
		
		return USERNAME;
	}
	
	public String getBotToken() {
		
		return TOKEN;
		
	}
	


}

/*
 *
 		    if (userStatus.containsKey(chatId)) {
		    	switch (receivedText.toLowerCase()) {
			    
			    case "acciriti": message.setText("Mmocc'a mammeta");
			    				break;
			    case "pacciuotico": if (fessa < 8)
			    						fessa++;
			    					message.setText("Tu e i " + fessa + " organizzatori");
			    					break;
			    
			    default: message.setText("O che cazzo stai ricenno?");
			    		break;
			    
			    
			    }
		    } else {
		    	message.setText("Stiemmo scarsi a fessa oì!");
		    	userStatus.put(chatId, 0);
		    }
					
 * 
 */
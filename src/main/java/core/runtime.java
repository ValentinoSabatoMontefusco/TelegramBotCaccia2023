package core;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class runtime {

	
	public static void main(String[] args) {
	    try {
	      TelegramBotsApi botsApi = new TelegramBotsApi( DefaultBotSession.class );
	      botsApi.registerBot(new InfinityHuntBot());
	      System.out.println("Boh, pare che almeno qui ci so' arrivato");
	    } catch (TelegramApiException e) {
	      e.printStackTrace();
	    }
	  }
	
}


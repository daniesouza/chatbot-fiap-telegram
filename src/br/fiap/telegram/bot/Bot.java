
package br.fiap.telegram.bot;

import br.fiap.telegram.command.Comando;
import br.fiap.telegram.command.CommandFactory;
import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot{
	
	private static final Logger LOGGER = Logger.getGlobal();

	private TelegramBot bot;
	private int offset;

	public Bot() {
		
		LOGGER.log(Level.INFO,"Iniciando bot...");
		
		this.bot = BotManager.getBotInstance();
		List<Update> updates = bot.execute(new GetUpdates().offset(offset)).updates();

		if (updates != null && !updates.isEmpty()) {
			setProximoOffset(updates.get(updates.size() - 1));
		}
		
		LOGGER.log(Level.INFO,"Bot inicializado com sucesso!");
	}

	private void setProximoOffset(Update u) {
		offset = u.updateId() + 1;
	}

	/**
	 * Executa a Thread
	 */

	public void run() {
		
				
        ExecutorService es = Executors.newCachedThreadPool();

		while (true) {

			try{
				List<Update> updates = getUpdates();
				updates.forEach(update -> es.execute(() -> processaMensagem(update.message()))); // processamento em paralelo
			}catch (Exception ex){
				LOGGER.log(Level.SEVERE,ex.getMessage(),ex);
			}

		}

	}

	/**
	 * Lê mensagem
	 */
	public List<Update> getUpdates() {

		List<Update> updates = null;

		while (updates == null || updates.isEmpty()) {

			updates = bot.execute(new GetUpdates().offset(offset)).updates();

			if (!updates.isEmpty()) {
				setProximoOffset(updates.get(updates.size() - 1));
				return updates;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return updates;

	}

	/**
	 * Processa mensagem
	 * 
	 * @param mensagem
	 */
	public void processaMensagem(Message mensagem) {

		Cliente cliente;
		try {
			cliente = SessionManager.getClient(mensagem.from().id());
			
			LOGGER.log(Level.INFO,"Processando mensagem : "+ cliente.toString());
			
			cliente.setMensagemAtual(mensagem.text());
			SessionManager.addClient(cliente);
			Comando comando = CommandFactory.getComando(cliente);
			comando.processar(cliente);
			
		} catch (Exception ex) {
            LOGGER.log(Level.SEVERE,ex.getMessage(),ex);;
		}

	}

}

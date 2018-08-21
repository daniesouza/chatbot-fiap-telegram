
package br.fiap.telegram.bot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;

import br.fiap.telegram.command.Comando;
import br.fiap.telegram.command.CommandFactory;
import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;

public class Bot extends Thread {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

	private TelegramBot bot;
	private int offset;

	public Bot() {
		this.bot = BotManager.getBotInstance();
		List<Update> updates = bot.execute(new GetUpdates().offset(offset)).updates();

		if (!updates.isEmpty()) {
			setProximoOffset(updates.get(updates.size() - 1));
		}
	}

	private void setProximoOffset(Update u) {
		offset = u.updateId() + 1;
	}

	/**
	 * Executa a Thread
	 */

	public void run() {

		while (true) {

			List<Update> updates = getUpdates();
			updates.stream().forEach(update -> processaMensagem(update.message()));
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
				sleep(1000);
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
			
			LOGGER.info("Cliente: {}.", cliente);
			
			cliente.setMensagemAtual(mensagem.text());
			SessionManager.addClient(cliente);
			Comando comando = CommandFactory.getComando(cliente);
			comando.processar(cliente);
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception: {}.", e.getMessage());
		}

	}

}

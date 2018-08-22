
package br.fiap.telegram.manager;

import java.time.LocalDateTime;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import br.fiap.telegram.constants.BotConstants;
import br.fiap.telegram.model.Cliente;
import br.fiap.telegram.prevayler.ClientesDaoPrevayler;
import br.fiap.telegram.utils.ListaCliente;

public class SessionManager {
	
	public static Cliente getClient(int chatId) throws Exception {

		ListaCliente listaCliente = getPrevayler().prevalentSystem();
		Cliente cliente = listaCliente.getByChatId(chatId);

		if (cliente.getChatId() == 0) {
			cliente.setChatId(chatId);
			addClient(cliente);
		}
			

		return cliente;
	}

	public static void addClient(Cliente cliente) throws Exception {

		cliente.setLastUpdate(LocalDateTime.now());
		getPrevayler().execute(new ClientesDaoPrevayler(cliente));

	}

	private static Prevayler<ListaCliente> getPrevayler() throws Exception {
		PrevaylerFactory<ListaCliente> factory = new PrevaylerFactory<>();
		factory.configurePrevalenceDirectory(BotConstants.BOT_DB_DIRECTORY);
		factory.configurePrevalentSystem(new ListaCliente());
		return factory.create();

	}

}

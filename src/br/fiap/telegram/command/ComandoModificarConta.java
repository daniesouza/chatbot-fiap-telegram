
package br.fiap.telegram.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;

public class ComandoModificarConta implements Comando {

	@Override
	public void processar(Cliente cliente) throws Exception {

		if (!clientePossuiConta(cliente)) {
			BotManager.enviarMensagem(cliente.getChatId(),
			        "Você não possui conta cadastrada!" + "\nCrie uma conta antes de continuar. /abrirconta");
			cliente.setComandoAtual(null);

			SessionManager.addClient(cliente);

			ComandoStart.mostrarMenu(cliente);
			return;
		}

		boolean isComando = ComandoEnum.getByCodigo(cliente.getMensagemAtual()) != null;
		;

		if (cliente.getEstadoAtual() == null || "".equals(cliente.getEstadoAtual()) || isComando) {
			cliente.setMensagemAtual("");
			cliente.setEstadoAtual("selecionar_item");
			SessionManager.addClient(cliente);
			mostrarMenu(cliente);
			return;
		}

		if ("selecionar_item".equals(cliente.getEstadoAtual())) {

			List<String> opt = Arrays.asList("nome", "cpf", "rg", "data de nascimento");
			if (opt.contains(cliente.getMensagemAtual())) { // selecionou item
				cliente.setEstadoAtual(cliente.getMensagemAtual());
				cliente.setMensagemAtual("");
				SessionManager.addClient(cliente);
				modificarConta(cliente);
			} else {
				BotManager.enviarMensagem(cliente.getChatId(), "Opção inválida!");
				mostrarMenu(cliente);
			}
		} else {
			modificarConta(cliente);
		}

	}

	private boolean clientePossuiConta(Cliente cliente) {
		return cliente.getConta() != null;
	}

	/**
	 * Mostra o Menu de opÃ§Ãµes
	 */
	private void mostrarMenu(Cliente cliente) {

		Keyboard keyboard =
		        new ReplyKeyboardMarkup(new String[] { "nome", "cpf" }, new String[] { "rg", "data de nascimento" });

		BotManager.enviarMensagem(cliente.getChatId(), "Qual informação deseja atualizar?", keyboard);
	}

	private void modificarConta(Cliente cliente) throws Exception {

		String estadoAtual = cliente.getEstadoAtual();

		switch (estadoAtual) {

			case "nome": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe seu nome completo");
					return;
				} else {
					cliente.setNome(cliente.getMensagemAtual());
					SessionManager.addClient(cliente);
				}

				break;
			}

			case "cpf": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe seu CPF (apenas numeros)");
					return;
				} else {
					try {
						long cpf = Long.valueOf(cliente.getMensagemAtual());
						cliente.setCpf(cpf);
						SessionManager.addClient(cliente);
					} catch (Exception e) {
						BotManager.enviarMensagem(cliente.getChatId(), "CPF Inválido! Digite novamente!");
						return;
					}

				}

				break;
			}

			case "rg": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe seu RG");
					return;
				} else {
					cliente.setRg(cliente.getMensagemAtual());
					SessionManager.addClient(cliente);
				}

				break;
			}

			case "data de nascimento": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe sua data de nascimento (dd/MM/aaaa)");
					return;
				} else {

					try {

						// Obtendo a data de nascimento do cliente
						LocalDate dataNasc =
						        LocalDate.parse(cliente.getMensagemAtual(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						cliente.setDataNascimento(dataNasc);
						SessionManager.addClient(cliente);

					} catch (Exception ex) {
						BotManager.enviarMensagem(cliente.getChatId(),
						        "Data de nascimento inválida! Digite novamente (dd/MM/aaaa) !");
						return;
					}
				}

				break;
			}

		}

		cliente.setMensagemAtual(null);
		cliente.setEstadoAtual(null);
		cliente.setComandoAtual(null);

		SessionManager.addClient(cliente);

		String msg = "Sua conta foi modificada com sucesso! :)"
		        + "\nAg: "
		        + cliente.getConta().getNumAg()
		        + "\nCc: "
		        + cliente.getConta().getNumCc()
		        + "\nNome: "
		        + cliente.getNome()
		        + "\nRG: "
		        + cliente.getRg()
		        + "\nCpf: "
		        + cliente.getCpf()
		        + "\nData Nasc: "
		        + cliente.getDataNascFormat();

		BotManager.enviarMensagem(cliente.getChatId(), msg);

		ComandoStart.mostrarMenu(cliente);

	}

}

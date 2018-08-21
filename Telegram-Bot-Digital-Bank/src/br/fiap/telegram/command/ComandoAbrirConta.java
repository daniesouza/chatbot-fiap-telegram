
package br.fiap.telegram.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;
import br.fiap.telegram.model.Conta;

public class ComandoAbrirConta implements Comando {

	@Override
	public void processar(Cliente cliente) throws Exception {

		if (clientePossuiConta(cliente)) {
			cliente.setComandoAtual(null);
			SessionManager.addClient(cliente);
			BotManager.enviarMensagem(cliente.getChatId(), "Você ja possui uma conta cadastrada!");
			ComandoStart.mostrarMenu(cliente);
			return;
		}

		boolean isComando = ComandoEnum.getByCodigo(cliente.getMensagemAtual()) != null;

		if (cliente.getEstadoAtual() == null || "".equals(cliente.getEstadoAtual()) || isComando) {
			cliente.setMensagemAtual("");
			cliente.setEstadoAtual("informar_nome");

			SessionManager.addClient(cliente);
		}

		abrirConta(cliente);

	}

	private boolean clientePossuiConta(Cliente cliente) {
		return cliente.getConta() != null;
	}

	private void abrirConta(Cliente cliente) throws Exception {

		String estadoAtual = cliente.getEstadoAtual();

		switch (estadoAtual) {

			case "informar_nome": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe seu nome completo");
					return;
				} else {
					cliente.setNome(cliente.getMensagemAtual());
					cliente.setMensagemAtual("");
					cliente.setEstadoAtual("informar_cpf");
					SessionManager.addClient(cliente);
				}
			}

			case "informar_cpf": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe seu CPF (apenas numeros)");
					return;
				} else {
					try {
						long cpf = Long.valueOf(cliente.getMensagemAtual());
						cliente.setCpf(cpf);
						cliente.setMensagemAtual("");
						cliente.setEstadoAtual("informar_rg");
						SessionManager.addClient(cliente);
					} catch (Exception e) {
						BotManager.enviarMensagem(cliente.getChatId(), "CPF Invalido! Digite novamente!");
						return;
					}

				}
			}

			case "informar_rg": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe seu RG");
					return;
				} else {
					cliente.setRg(cliente.getMensagemAtual());
					cliente.setMensagemAtual("");
					cliente.setEstadoAtual("informar_data_nascimento");
					SessionManager.addClient(cliente);
				}
			}

			case "informar_data_nascimento": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe sua data de nascimento (dd/MM/aaaa)");
					return;
				} else {

					try {

						// Obtendo a data de nascimento do cliente
						LocalDate dataNasc =
						        LocalDate.parse(cliente.getMensagemAtual(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						cliente.setDataNascimento(dataNasc);
						cliente.setMensagemAtual("");
						SessionManager.addClient(cliente);

					} catch (Exception ex) {
						BotManager.enviarMensagem(cliente.getChatId(),
						        "Data de nascimento Inválida! Digite novamente (dd/MM/aaaa) !");
						return;
					}
				}
			}

		}

		cliente.setConta(new Conta());
		cliente.setMensagemAtual(null);
		cliente.setEstadoAtual(null);
		cliente.setComandoAtual(null);

		SessionManager.addClient(cliente);

		String msg = "Sua conta foi criada com sucesso! :)"
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

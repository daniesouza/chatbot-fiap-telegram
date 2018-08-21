
package br.fiap.telegram.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;
import br.fiap.telegram.model.Dependente;

public class ComandoIncluirDependentes implements Comando {

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
			cliente.setEstadoAtual("informar_nome");

			SessionManager.addClient(cliente);

		}

		incluirDependente(cliente);

	}

	private boolean clientePossuiConta(Cliente cliente) {
		return cliente.getConta() != null;
	}

	private void incluirDependente(Cliente cliente) throws Exception {

		String estadoAtual = cliente.getEstadoAtual();

		switch (estadoAtual) {

			case "informar_nome": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe o nome do dependente");
					return;
				} else {
					cliente.getDependente().setNome(cliente.getMensagemAtual());
					cliente.setMensagemAtual("");
					cliente.setEstadoAtual("informar_cpf");
					SessionManager.addClient(cliente);
				}
			}

			case "informar_cpf": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe o CPF do dependente (apenas numeros)");
					return;
				} else {
					try {
						long cpf = Long.valueOf(cliente.getMensagemAtual());
						cliente.getDependente().setCpf(cpf);
						cliente.setMensagemAtual("");
						cliente.setEstadoAtual("informar_rg");
						SessionManager.addClient(cliente);
					} catch (Exception e) {
						BotManager.enviarMensagem(cliente.getChatId(), "CPF do dependente Invalido! Digite novamente!");
						return;
					}

				}
			}

			case "informar_rg": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe o RG do dependente");
					return;
				} else {
					cliente.getDependente().setRg(cliente.getMensagemAtual());
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

						// Obtendo a data de nascimento do dependente
						LocalDate dataNasc =
						        LocalDate.parse(cliente.getMensagemAtual(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						cliente.getDependente().setDataNascimento(dataNasc);
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

		Dependente dependente = new Dependente(cliente.getDependente().getNome(),
		        cliente.getDependente().getCpf(),
		        cliente.getDependente().getRg(),
		        cliente.getDependente().getDataNascimento(),
		        cliente);

		cliente.addDependente(dependente);

		cliente.setMensagemAtual(null);
		cliente.setEstadoAtual(null);
		cliente.setComandoAtual(null);

		SessionManager.addClient(cliente);

		String msg = "Dependente adicionado com sucesso! :)"
		        + "\nNome: "
		        + cliente.getDependente().getNome()
		        + "\nRG: "
		        + cliente.getDependente().getRg()
		        + "\nCpf: "
		        + cliente.getDependente().getCpf()
		        + "\nData Nasc: "
		        + cliente.getDependente().getDataNascFormat();

		BotManager.enviarMensagem(cliente.getChatId(), msg);

		ComandoStart.mostrarMenu(cliente);

	}
}

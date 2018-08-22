
package br.fiap.telegram.command;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;
import br.fiap.telegram.model.Emprestimo;

public class ComandoEmprestimo implements Comando {

	@Override
	public void processar(Cliente cliente) throws Exception {

		if (!clientePossuiConta(cliente)) {
			BotManager.enviarMensagem(cliente.getChatId(),
			        "VocÍ n„o possui conta cadastrada!" + "\nCrie uma conta antes de continuar. /abrirconta");
			cliente.setComandoAtual(null);

			SessionManager.addClient(cliente);

			ComandoStart.mostrarMenu(cliente);
			return;
		}

		boolean isComando = ComandoEnum.getByCodigo(cliente.getMensagemAtual()) != null;

		if (cliente.getEstadoAtual() == null || "".equals(cliente.getEstadoAtual()) || isComando) {
			cliente.setMensagemAtual("");
			cliente.setEstadoAtual("informar_valor");
			SessionManager.addClient(cliente);
		}

		emprestar(cliente);

	}

	private boolean clientePossuiConta(Cliente cliente) {
		return cliente.getConta() != null;
	}

	private void emprestar(Cliente cliente) throws Exception {

		String estadoAtual = cliente.getEstadoAtual();

		switch (estadoAtual) {

			case "informar_valor": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe o valor do emprÈstimo");
					return;
				} else {
					try {
						Double valorContratado = Double.parseDouble(cliente.getMensagemAtual());

						if (valorContratado > (cliente.getConta().getSaldo() * 40)) {

							BotManager.enviarMensagem(cliente.getChatId(),
							        "Valor do emprÈstimo deve ser menor ou igual a 40x o saldo da sua conta.\nSaldo atual: R$ "
							                + cliente.getConta().getSaldo());
							return;

						} else {
							cliente.getConta().getEmprestimo().setValorContratado(valorContratado);
							cliente.setMensagemAtual("");
							cliente.setEstadoAtual("informar_meses");
							SessionManager.addClient(cliente);
						}
					} catch (Exception e) {
						BotManager.enviarMensagem(cliente.getChatId(), "Valor inv·lido! Tente novamente!");
						return;
					}
				}
			}

			case "informar_meses": {

				if (cliente.getMensagemAtual().trim().equals("")) {
					BotManager.enviarMensagem(cliente.getChatId(), "Informe a quantidade de meses do empr√©stimo");
					return;
				} else {
					try {
						int meses = Integer.valueOf(cliente.getMensagemAtual());

						if (meses > 36) {
							BotManager.enviarMensagem(cliente.getChatId(),
							        "Quantidade de meses do empr√©stimo deve ser menor que 3 anos");
							return;
						} else {
							cliente.getConta().getEmprestimo().setQtdeMeses(meses);
							cliente.setMensagemAtual("");
							cliente.setEstadoAtual("informar_meses");
							SessionManager.addClient(cliente);
						}

					} catch (Exception e) {
						BotManager.enviarMensagem(cliente.getChatId(), "Valor inv√°lido! Tente novamente!");
						return;
					}

				}
			}
		}

		Emprestimo emprestimo = new Emprestimo(cliente.getConta().getEmprestimo().getValorContratado(),
		        cliente.getConta().getEmprestimo().getQtdeMeses(),
		        cliente.getConta());

		cliente.getConta().addEmprestimo(emprestimo);

		String str = "Emprestimo contratado com sucesso! :)"
		        + "\nValor Contratado: R$ "
		        + emprestimo.getValorContratado()
		        + "\nData Contrata√ß√£o: "
		        + emprestimo.getDataContratacaoFormated()
		        + "\nQuantidade de Meses: "
		        + emprestimo.getQtdeMeses()
		        + "\nValor Total do Empr√©stimo: R$ "
		        + emprestimo.getValorEmprestimo()
		        + "\nData Final do Empr√©stimo: R$ "
		        + emprestimo.getDataFinalEmprestimo();

		cliente.setMensagemAtual(null);
		cliente.setEstadoAtual(null);
		cliente.setComandoAtual(null);

		SessionManager.addClient(cliente);

		BotManager.enviarMensagem(cliente.getChatId(), str);
		ComandoStart.mostrarMenu(cliente);

	}
}

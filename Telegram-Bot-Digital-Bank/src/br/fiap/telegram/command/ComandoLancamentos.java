
package br.fiap.telegram.command;

import java.util.List;
import java.util.stream.Collectors;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;
import br.fiap.telegram.model.Lancamento;

public class ComandoLancamentos implements Comando {

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

		lancamentos(cliente);

	}

	private boolean clientePossuiConta(Cliente cliente) {
		return cliente.getConta() != null;
	}

	private void lancamentos(Cliente cliente) throws Exception {

		List<Lancamento> lancamentos = cliente.getConta().getLancamentos();

		if (lancamentos != null && !lancamentos.isEmpty()) {

			double somaDepositos = 0f;
			double somaSaques = 0f;

			int totalDepositos = 0;
			int totalSaques = 0;
			int totalExtratos = 0;

			StringBuilder sb = new StringBuilder();

			sb.append("Resumo de lancamentos");
			sb.append("\n");
			sb.append("\n");
			sb.append("Total de lançamentos: " + lancamentos.size());
			sb.append("\n");

			List<Lancamento> lancamentosSaque =
			        lancamentos.stream().filter(o -> o.getDescricao().startsWith("Saque")).collect(Collectors.toList());

			List<Lancamento> lancamentosDeposito =
			        lancamentos.stream().filter(o -> o.getDescricao().startsWith("Depósito")).collect(
			                Collectors.toList());

			List<Lancamento> lancamentosExtrato =
			        lancamentos.stream().filter(o -> o.getDescricao().startsWith("Extrato")).collect(
			                Collectors.toList());

			for (Lancamento lc : lancamentosSaque) {
				somaSaques += lc.getValor();
				totalSaques++;
			}

			for (Lancamento lc : lancamentosDeposito) {
				somaDepositos += lc.getValor();
				totalDepositos++;
			}

			for (Lancamento lc : lancamentosExtrato) {
				totalExtratos++;
			}

			sb.append("\nValor Depositado: R$ " + somaDepositos);
			sb.append("\nValor Sacado: R$ " + somaSaques);
			sb.append("\n\nTotal de Depósitos: " + totalDepositos);
			sb.append("\nTotal de Saques: " + totalSaques);
			sb.append("\nTotal de Extratos: " + totalExtratos);

			BotManager.enviarMensagem(cliente.getChatId(), sb.toString());

		} else {
			BotManager.enviarMensagem(cliente.getChatId(), "Você não possui lançamentos!");
		}

		cliente.setMensagemAtual(null);
		cliente.setEstadoAtual(null);
		cliente.setComandoAtual(null);

		SessionManager.addClient(cliente);

		ComandoStart.mostrarMenu(cliente);

	}
}

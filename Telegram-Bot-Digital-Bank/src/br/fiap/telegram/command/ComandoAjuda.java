package br.fiap.telegram.command;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;

public class ComandoAjuda implements Comando {


    @Override
    public void processar(Cliente cliente) throws Exception {
        ajuda(cliente);
    }


    private void ajuda(Cliente cliente) throws Exception {

        StringBuilder sb = new StringBuilder();

        for (ComandoEnum comandoEnum : ComandoEnum.values()) {

            sb.append(comandoEnum.codigo+" - "+comandoEnum.descricao);
            sb.append("\n");

        }

        BotManager.enviarMensagem(cliente.getChatId(), sb.toString());

        cliente.setMensagemAtual(null);
        cliente.setEstadoAtual(null);
        cliente.setComandoAtual(null);
        
        SessionManager.addClient(cliente);

        ComandoStart.mostrarMenu(cliente);

    }
}

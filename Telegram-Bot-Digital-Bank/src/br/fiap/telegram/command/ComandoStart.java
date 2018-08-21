package br.fiap.telegram.command;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.model.Cliente;

public class ComandoStart implements Comando{


    @Override
    public void processar(Cliente cliente) {

        String msg = "Bem vindo ao Banco Sheldon.\n" +
                "É um prazer te-lo como nosso cliente.";

        BotManager.enviarMensagem(cliente.getChatId(), msg);
        mostrarMenu(cliente);

    }


    /**
     * Mostra o Menu de opções
     */
    static void mostrarMenu(Cliente cliente) {

        Keyboard keyboard = new ReplyKeyboardMarkup(

                new String[]{ ComandoEnum.ABRIR_CONTA.codigo, ComandoEnum.MODIFICAR_CONTA.codigo },
                new String[]{ ComandoEnum.INCLUIR_DEPENDENTE.codigo, ComandoEnum.EXIBIR_DADOS.codigo },
                new String[]{ ComandoEnum.DEPOSITO.codigo, ComandoEnum.SAQUE.codigo },
                new String[]{ ComandoEnum.EXTRATO.codigo, ComandoEnum.EMPRESTIMO.codigo },
                new String[]{ ComandoEnum.SALDO_DEVEDOR.codigo, ComandoEnum.LANCAMENTOS.codigo },
                new String[]{ ComandoEnum.RETIRADAS.codigo, ComandoEnum.TARIFAS.codigo },
                new String[]{ ComandoEnum.AJUDA.codigo });

        BotManager.enviarMensagem(cliente.getChatId(), "Em que podemos ajudá-lo(a) agora?", keyboard);
    }
}

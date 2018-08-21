package br.fiap.telegram.manager;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.fiap.telegram.constants.BotConstants;

public class BotManager {

    private static TelegramBot bot;

    public static TelegramBot getBotInstance(){

        if(bot == null){
            bot = new TelegramBot(BotConstants.BOT_TOKEN);
        }

        return bot;
    }

    public static void enviarMensagem(long chatId, String mensagem) {
        enviarMensagem(chatId,mensagem,new ForceReply());
    }

    public static void enviarMensagem(long chatId, String mensagem, Keyboard keyboard) {

        SendResponse sr = BotManager.getBotInstance().execute(
                new SendMessage(chatId, mensagem)
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyMarkup(keyboard)
        );

        if (!sr.isOk()) {
            System.out.println(sr.description() + " (" + sr.errorCode() + ")");
            System.out.println("Chat Id: " + chatId);
        }
    }


    /**
     * Deleta Mensagem
     *
     * @param chatId
     * @param mensagemId
     */
    public static void deletarMensagem(int chatId, int mensagemId) {

        BaseResponse br = bot.execute(new DeleteMessage(chatId, mensagemId));

        if (! br.isOk()) {
            System.out.println(br.description() + " (" + br.errorCode() + ")");
            System.out.println("Chat Id: " + chatId + " - Mensagem Id: " + mensagemId);
        }
    }
}

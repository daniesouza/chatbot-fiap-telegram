# chatbot-fiap-telegram
Projeto de Pos Fiap 

Agosto de 2018.
FIAP


# Objetivo

O objetivo desta atividade � criar um bot Telegram que simule um banco virtual, e seja
poss�vel implementar os comportamentos basicos de um banco virtual como depositar,sacar,abrir conta,etc...


# 1. Introdu��o
	
Com o avan�o da tecnologia m�vel no mundo inteiro, explorando, dentre outras formas pelos comunicadores instant�neos aliado aos assistentes virtuais que tem se mostrado cada vez mais inteligentes e ajudando as pessoas a cumprirem suas tarefas do cotidiano estabelecendo uma plataforma de servi�os de autoatendimento, com possibilidades tamb�m abertas as empresas.

Os bancos financeiros, por sua vez, est�o nascendo ou migrando para a realidade digital, onde n�o � obrigat�rio ter ag�ncias f�sicas para obter seus principais servi�os. Com o acesso massificado da internet, � poss�vel realizar desde a abertura da conta e transa��es de qualquer natureza remotamente.

Por outro lado, os bots, abrevia��o de robots, tem sido utilizado nos principais comunicadores de mensagens, como o Telegram e Facebook. Sua facilidade e flexibilidade de integra��o permite utiliz�-los como assistentes pessoais para resolu��o de diversas quest�es, tais como: 
Obter segunda via de contas, solicita��o de servi�os e solicita��o de informa��es. Os bots substituem o atendimento humano pelo virtual, mais efetivo e, podendo ser explorado para ser adaptado a cada tipo de cliente.



# 2. Componentes e Frameworks

# Maven
O Maven � uma ferramenta para gerenciamento das bibliotecas e plug-ins de
um ou mais reposit�rios de forma din�mica utilizando um XML para descrever o
projeto de software sendo constru�do.

#Java API for Telegram
O Java API for Telegram possibilita o desenvolvimento de um bot personalizado conforme a necessidade do projeto.
Website da API:
[https://oss.sonatype.org/content/repositories/releases/com/github/pengrad/java-telegram-bot-api/2.1.2/](https://oss.sonatype.org/content/repositories/releases/com/github/pengrad/java-telegram-bot-api/2.1.2/)
# Prevlayer
Api de persist�ncia usado para persistir os dados do usu�rio/cliente em arquivo conforme documento de requisitos.

[http://prevlayer.org/](http://prevlayer.org/)

[https://mvnrepository.com/artifact/org.prevayler](https://mvnrepository.com/artifact/org.prevayler)

# Java Logging
Ferramenta padr�o de log do java em Console.

[https://docs.oracle.com/javase/7/docs/api/java/util/logging/package-summary.html](https://docs.oracle.com/javase/7/docs/api/java/util/logging/package-summary.html)


# 3. Estrutura de pacotes do Projeto

 ![Pacotes](docs/diagramas/estrutura-pacotes.png)
 
 fiap.telegram
 - Pacote principal com os arquivos do projeto.

 fiap.telegram.bot
 - Pacote com a intelig�ncia do Bot para buscar as mensagens do telegram.

 fiap.telegram.command
 - Pacote com todas as classes de comandos a serem executados.

 fiap.telegram.comparator
 - Pacote com o comparator de cliente.

 fiap.telegram.constants
 - Pacote de constantes

 fiap.telegram.manager
 - Pacote com os gerenciados de envio de mensagens ao telegram e gerenciamento da sess�o do cliente.

 fiap.telegram.model
 - Pacote com a camada model

 fiap.telegram.prevayler
 - Pacote prevlayer de persist�ncia em arquivo.

 fiap.telegram.utils
 - Pacote de Utilidades


# 4. Pr�-requisitos
 - Tecnologias necess�rias.
 
    1- Para executar o projeto � necess�rio ter o JRE 8 ou JDK 8 instalado.
 
    2- Utilizar uma ide para execu��o. (Eclipse � recomendado)
 
 - Dados do Bot

 ![Dados Bot](docs/diagramas/info-bot.png)
 
# 5. Utiliza��o do Bot

- Funcionalidades

 - Para Utiliza��o do bot, enviar o comando /start para o bot com o nome @sheldonfiap_bot.
 - Tela de boas-vindas do banco
 - Cria��o de conta
 - Modifica��o de conta
 - Inclus�o de dependentes (conta-conjunta)
 - Exibi��o dos dados do titular e dependentes
 - Dep�sito
 - Saque (custo do servi�o R$ 2,50)
 - Solicita��o de extrato (custo do servi�o R$ 1,00)
 - Solicita��o de empr�stimo, cujo prazo m�ximo � de 3 anos e valor m�ximo � de 40 vezes o saldo da conta (custo do servi�o R$ 15,00 al�m de juros de 5% a.m.)
 - Exibi��o de saldo devedor do empr�stimo e prazo de pagamento
 - Exibi��o dos lan�amentos detalhada, com somat�ria ao final
 - Exibi��o das retiradas, com somat�ria ao final
 - Exibi��o das tarifas de servi�o, com somat�ria ao final dos servi�os que j� foram utilizados na conta
 - Tela de ajuda
 
 Lista de comandos conforme imagem abaixo.
 
 - ![Funcionalidades](docs/diagramas/bot-funcionalidades.png)




 - Utilizando um bot novo(opcional)
 
 Para o desenvolvimento do Bot, � necess�rio solicitar um token para o 
 @BotFather, um bot do pr�prio Telegram para cria��o e gest�o dos Bots. 

A solicita��o � feita atrav�s do endere�o [Acesse aqui](https://telegram.me/BotFather) mostrado a seguir.

 ![BotFather](docs/diagramas/botfather.png)


# 6. Resumo t�cnico de funcionamento

A partir da classe Bot recuperamos as mensagens represadas da API do Telegram, verificamos se o cliente j� est� salvo atrav�s da classe SessionManager que por sua vez,
 verifica e grava um cliente atrav�s da camada de Prevayler.

Na camada de command, instanciamos um comando atrav�s da CommandFactory, que verifica se o comando 

vindo atrav�s da digita��o do usu�rio � v�lido, ou seja, se est� listado no Enum de comandos ComandoEnum e o inst�ncia para ser executado. 
 	
Cada comando corresponde a uma funcionalidade do sistema, verificando em qual etapa o

 cliente est�, devolve uma informa��o para a Interface Comando e grava o estado a cada intera��o com o usu�rio, no final, 
 retorna as op��es de menu principal para a API do Telegram e devolve o resultado para o usu�rio.



# Diagramas de Classe

 ![Classes](docs/diagramas/classes.png)
 
# Diagrama de Sequ�ncia
 
 ![Sequencia](docs/diagramas/diagrama-sequencia.png)
 
 
# Executando o projeto

No diret�rio **src** no pacote **br.fiap.telegram** existe um arquivo chamado **Main.java** . Abra esse arquivo e execute **Run**

 
# Javadoc

[Acesse clicando aqui](docs/javadoc/index.html)


# Refer�ncias


- Telegram Documenta��o oficial

  [https://core.telegram.org/bots](https://core.telegram.org/bots)
  	
  [https://core.telegram.org/bots/api](https://core.telegram.org/bots/api)

 

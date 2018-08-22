package br.fiap.telegram.command;

public enum ComandoEnum {

    START("/start","Inicio com menu de boas vindas"),
    ABRIR_CONTA("/abrirconta","Abrir uma nova conta"),
    MODIFICAR_CONTA("/modificarconta","Modifica a sua conta atual"),
    INCLUIR_DEPENDENTE("/incluirdependente", "Inclui um dependente a sua conta atual"),
    EXIBIR_DADOS("/exibirdados", "Exibe os dados da sua conta + dependentes"),
    DEPOSITO("/deposito", "Deposita um valor (formato R$ 99.99)"),
    SAQUE("/saque","Saca um valor (formato R$ 99.99)"),
    EXTRATO("/extrato","Extrato da conta atual"),
    EMPRESTIMO("/emprestimo","Emprestimos efetuados"),
    SALDO_DEVEDOR("/saldodevedor","Saldo devedor de emprestimos"),
    LANCAMENTOS("/lancamentos","Resumos dos lançamentos"),
    RETIRADAS("/retiradas","Resumo dos saques/retiradas"),
    TARIFAS("/tarifas", "Resumo de tarifas pagas"),
    AJUDA("/ajuda","Ajuda");


    public String codigo;
    public String descricao;

    ComandoEnum(String codigo,String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static ComandoEnum getByCodigo(String codigo) {
        for (ComandoEnum comandoEnum : ComandoEnum.values()) {
            if (comandoEnum.codigo.equals(codigo)) {
                return comandoEnum;
            }
        }
        return null;
    }
}

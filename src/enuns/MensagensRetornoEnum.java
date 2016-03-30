/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enuns;

/**
 *
 * @author Marcelo
 */
public enum MensagensRetornoEnum {
    PARAMETRO_INVALIDO(0,"A sintaxe dos parametros esta incorreta"),
    ERRO_DESCONHECIDO(1,"Ocorreu um erro desconhecido"),
    INFORMACOES_INVALIDAS(2,"As informacoes inseridas sao invalidas"),
    SUCESSO_DEPOSITO(3,"Deposito foi realizado com sucesso"),
    CONTA_INVALIDA(4,"A conta informada é invalida"),
    SALDO_INSUFICIENTE(5,"Saldo na conta e insuficiente para efetuar esta transacao");
    
    private int codRetorno;
    private String msgRetorno;

    private MensagensRetornoEnum(int codRetorno, String msgRetorno) {
        this.codRetorno = codRetorno;
        this.msgRetorno = msgRetorno;
    }

    public int getCodRetorno() {
        return codRetorno;
    }

    public String getMsgRetorno() {
        return msgRetorno;
    }
}

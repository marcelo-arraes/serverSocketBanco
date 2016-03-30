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
public enum ParametrosEnum {
	SUCESSO("sucesso"),
    DADOS_CONTA("dadosConta"),
    ERRO("erro"),
    LOGAR_CONTA("logarConta"),
    DEPOSITAR_TERCEIRO("depositarTerceiro"),
    DEPOSITAR_PROPRIA("depositarPropria"),
    SAQUE("saque"),
    TRANSFERENCIA("transferencia");
    
    private String nomeParametro;

    private ParametrosEnum(String parametro) {
        this.nomeParametro = parametro;
    }

    public String getNomeParametro() {
        return nomeParametro;
    }
    
    public static ParametrosEnum getEnum(String nome){
        for (ParametrosEnum e : ParametrosEnum.values()) {
            if(e.getNomeParametro().equals(nome)) return e;
        }
        
        return null;
    }
}

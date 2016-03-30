/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import bean.Conta;
import core.SaldoException;
import enuns.MensagensRetornoEnum;
import enuns.ParametrosEnum;

/**
 *
 * @author Marcelo
 */
public class ServerThread extends Thread {

    private SocketMain servidor;
    private Socket cliente;
    public ServerThread(SocketMain servidor, Socket cliente) {
        this.servidor = servidor;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        String retorno;
        try {
            retorno = this.processarRetorno();
            //DataOutputStream retornoSock = new DataOutputStream(this.cliente.getOutputStream());

            //retornoSock.writeBytes(retorno);
            PrintWriter retornoSock = new PrintWriter(this.cliente.getOutputStream());
            retornoSock.println(retorno);
            retornoSock.flush();
            retornoSock.close();

        } catch (IOException | SQLException ex) {
            retorno = getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.ERRO_DESCONHECIDO);
            try {
                //DataOutputStream retornoSock = new DataOutputStream(this.cliente.getOutputStream());
                //retornoSock.writeBytes(retorno);
            	PrintWriter retornoSock = new PrintWriter(this.cliente.getOutputStream());
                retornoSock.println(retorno);
                retornoSock.flush();
                retornoSock.close();
            } catch (IOException ex1) {
                ex.printStackTrace();
            }
            
        }
        
        try {
            this.cliente.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String processarRetorno() throws SQLException, IOException {
        String rec = new BufferedReader(new InputStreamReader(this.cliente.getInputStream())).readLine();
        String dadoRecebido[] = rec.split("\t");

        if (dadoRecebido.length != 2) {
            return this.getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.PARAMETRO_INVALIDO);
        }

        ParametrosEnum parametro = ParametrosEnum.getEnum(dadoRecebido[0]);
        String valor[] = dadoRecebido[1].split("[|]");

        if (parametro == null) {
            return this.getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.PARAMETRO_INVALIDO);
        }

        switch (parametro) {
            case LOGAR_CONTA:
                return this.logarConta(valor);
            case DEPOSITAR_PROPRIA:
            	return this.depositar(ParametrosEnum.DEPOSITAR_PROPRIA, valor);
            case DEPOSITAR_TERCEIRO:
            	return this.depositar(ParametrosEnum.DEPOSITAR_TERCEIRO, valor);
            case SAQUE:
            	return this.saque(valor);
            case TRANSFERENCIA:
            	return this.transferir(valor);
            default:
            	return this.getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.PARAMETRO_INVALIDO);
        }
    }

    private String logarConta(String dadoRecebido[]) throws SQLException {
        if (dadoRecebido.length != 3) {
            return getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.PARAMETRO_INVALIDO);
        }
        Conta conta = this.servidor.getContaCtrl().logarConta(dadoRecebido[0], dadoRecebido[1], dadoRecebido[2]);
        if (conta == null) {
            return this.getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.INFORMACOES_INVALIDAS);
        }

        return this.getDadosConta(conta);
    }
    
    private String depositar(ParametrosEnum tipoDeposito, String valores[]) throws SQLException {
    	 if (valores.length != 3) {
             return getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.PARAMETRO_INVALIDO);
         }
    	 
    	 Conta conta = null;
    	 
    	 try {
    		 conta = this.servidor.getContaCtrl().depositar(valores[0], valores[1], Double.parseDouble(valores[2]));
		} catch (NumberFormatException e) {
			return getMsgRetorno(ParametrosEnum.ERRO, MensagensRetornoEnum.PARAMETRO_INVALIDO);
		}
    	 
    	if(conta == null) return getMsgRetorno(ParametrosEnum.ERRO, MensagensRetornoEnum.CONTA_INVALIDA);
    	
    	if(tipoDeposito == ParametrosEnum.DEPOSITAR_PROPRIA){
    	 return this.getDadosConta(conta);
    	}
    	return getMsgRetorno(ParametrosEnum.SUCESSO, MensagensRetornoEnum.SUCESSO_DEPOSITO);
    }
    
    private String saque(String valores[]) throws SQLException {
    	if (valores.length != 3) {
            return getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.PARAMETRO_INVALIDO);
        }
    	
    	Conta conta = null;
    	try{
    		conta = this.servidor.getContaCtrl().saque(valores[0], valores[1], Double.parseDouble(valores[2]));
    	} catch (NumberFormatException e) {
    		return getMsgRetorno(ParametrosEnum.ERRO, MensagensRetornoEnum.PARAMETRO_INVALIDO);
    	} catch (SaldoException e) {
			return getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.SALDO_INSUFICIENTE);
		}
    	
    	return this.getDadosConta(conta);
    }
    
    private String transferir(String valores[]) throws SQLException{
    	if (valores.length != 5) {
            return getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.PARAMETRO_INVALIDO);
        }
    	Conta conta = null;
    	
    	try{
    		conta = this.servidor.getContaCtrl().transferir(valores[0],valores[1],valores[2],valores[3],Double.parseDouble(valores[4]));
    		if(conta == null) return getMsgRetorno(ParametrosEnum.ERRO, MensagensRetornoEnum.CONTA_INVALIDA);
    	} catch (NumberFormatException e) {
    		return getMsgRetorno(ParametrosEnum.ERRO, MensagensRetornoEnum.PARAMETRO_INVALIDO);
    	} catch (SaldoException e) {
			return getMsgRetorno(ParametrosEnum.ERRO,MensagensRetornoEnum.SALDO_INSUFICIENTE);
		}
    	
    	return this.getDadosConta(conta);
    }

    private String getDadosConta(Conta conta){
    	 String vetRet[] = new String[6];
    	 vetRet[0] = conta.getAgencia().getNumero();
         vetRet[1] = conta.getNumero();
         vetRet[2] = conta.getAgencia().getNome();
         vetRet[3] = conta.getTipoConta();
         vetRet[4] = conta.getCliente().getNome();
         vetRet[5] = String.valueOf(conta.getSaldo());
         
         return this.getInformacoesRetorno(ParametrosEnum.DADOS_CONTA,vetRet);
    }
    
    private String getMsgRetorno(ParametrosEnum param, MensagensRetornoEnum par) {
        MensagensRetornoEnum ret = par;
        return param.getNomeParametro() + "\t" + ret.getCodRetorno() + "|" + ret.getMsgRetorno();
    }

    private String getInformacoesRetorno(ParametrosEnum param ,String vet[]) {
        String ret = "";

        for (String v : vet) {
            ret += v + "|";
        }

        ret = ret.substring(0, ret.length() - 1);
        return param.getNomeParametro() + "\t" + ret;
    }
}

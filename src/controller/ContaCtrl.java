/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;

import bean.Conta;
import controller.generic.GenericController;
import core.SaldoException;
import dao.generic.GenericDao;
import dao.impl.ContaDao;
import enuns.MensagensRetornoEnum;

/**
 *
 * @author Marcelo
 */
public class ContaCtrl extends GenericController<Conta>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4130129929048468087L;
	private ContaDao contaDao;

    public ContaCtrl() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.contaDao = new ContaDao();
    }

    @Override
    protected GenericDao<Conta> getDao() {
        return this.contaDao;
    }
    
    public Conta logarConta(String numAgencia, String numConta, String senha) throws SQLException{
        if(numAgencia.trim().isEmpty() || numConta.trim().isEmpty() || senha.isEmpty()) return null;
        
        Conta conta = this.contaDao.getContaByNumero(numConta);
        
        if(conta != null && conta.getAgencia().getNumero().equals(numAgencia) && conta.getSenha().equals(senha)){
            return conta;
        }
        
        return null;
    }
    
    public Conta depositar(String numAgencia, String numConta, double valor) throws SQLException{
    	Conta conta = this.contaDao.getContaByNumero(numConta);
    	if(conta == null) return null;
    	
    	conta.setSaldo(conta.getSaldo()+valor);
    	return getDao().alterar(conta);
    }
    
    public Conta saque(String numAgencia, String numConta, double valor) throws SQLException, SaldoException {
    	Conta conta = this.contaDao.getContaByNumero(numConta);
    	if(valor>conta.getSaldo()){
    		throw new SaldoException(MensagensRetornoEnum.SALDO_INSUFICIENTE.getMsgRetorno());
    	}
    	conta.setSaldo(conta.getSaldo()-valor);
    	return getDao().alterar(conta);
    }
    
    public Conta transferir(String agOr, String contaOr, String agDest, String contaDest, double valor) throws SQLException, SaldoException {
    	Conta contaOri = this.contaDao.getContaByNumero(contaOr);
    	if(valor>contaOri.getSaldo()){
    		throw new SaldoException(MensagensRetornoEnum.SALDO_INSUFICIENTE.getMsgRetorno());
    	}
    	Conta contaDes = this.contaDao.getContaByNumero(contaDest);
    	
    	if(contaDes == null) return null;
    	
    	contaOri.setSaldo(contaOri.getSaldo()-valor);
    	contaDes.setSaldo(contaDes.getSaldo()+valor);
    	
    	getDao().alterar(contaDes);
    	
    	return getDao().alterar(contaOri);
    }
}

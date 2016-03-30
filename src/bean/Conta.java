/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Marcelo
 */
public class Conta extends CodeBase{
    private String numero;
    private String tipoConta;
    private String senha;
    private double saldo;
    private Cliente cliente;
    private Agencia agencia;

    public Conta() {
    }

    public Conta(String numero, String tipoConta, String senha, double saldo, Cliente cliente, Agencia agencia) {
        this.numero = numero;
        this.tipoConta = tipoConta;
        this.senha = senha;
        this.saldo = saldo;
        this.cliente = cliente;
        this.agencia = agencia;
    }
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }
}

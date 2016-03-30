package controller;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcelo
 */
public class SocketMain {
    private ServerSocket servidor;
    //private InetAddress grupo;
    private ContaCtrl contaCtrl;
    private final String address = "228.5.6.7";
    private final int porta = 5400;

    private SocketMain() throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.contaCtrl = new ContaCtrl();
        this.servidor = new ServerSocket(this.porta);
        //this.grupo = InetAddress.getByName(this.address);
        //this.servidor.joinGroup(grupo);
    }

    public ServerSocket getServidor() {
        return this.servidor;
    }

    public String getAddress() {
        return address;
    }

    public int getPorta() {
        return porta;
    }

    public ContaCtrl getContaCtrl() {
        return contaCtrl;
    }
    
    public static void main(String args[]){
        try {
            SocketMain sock = new SocketMain();
            System.out.println("Servidor operando.");
            while(true){
                Socket cliente = sock.getServidor().accept();
                ServerThread thread = new ServerThread(sock,cliente);
                System.out.println("dado recebido");
                thread.start();
            }
            
        } catch (IllegalAccessException | SQLException | IOException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
           // Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Cliente;
import controller.generic.GenericController;
import dao.generic.GenericDao;
import dao.impl.ClienteDao;
import java.sql.SQLException;

/**
 *
 * @author Marcelo
 */
public class ClienteCtrl extends GenericController<Cliente>{
    private ClienteDao clienteDao;

    public ClienteCtrl() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        this.clienteDao = new ClienteDao();
    }

    @Override
    protected GenericDao<Cliente> getDao() {
        return this.clienteDao;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Agencia;
import controller.generic.GenericController;
import dao.generic.GenericDao;
import dao.impl.AgenciaDao;
import java.sql.SQLException;

/**
 *
 * @author Marcelo
 */
public class AgenciaCtrl extends GenericController<Agencia>{
    AgenciaDao agenciaDao;

    public AgenciaCtrl() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.agenciaDao = new AgenciaDao();
    }

    @Override
    protected GenericDao<Agencia> getDao() {
        return this.agenciaDao;
    }
    
}
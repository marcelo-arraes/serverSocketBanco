/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.generic;

import bean.CodeBase;
import dao.generic.GenericDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public abstract class GenericController<E extends CodeBase> implements Serializable{
    protected abstract GenericDao<E> getDao();
    
    public E adicionar(E bean) throws SQLException{
        return getDao().adicionar(bean);
    }
    
    public E alterar(E bean) throws SQLException{
        return getDao().alterar(bean);
    }
    
    public void remover(E bean) throws SQLException{
        getDao().remover(bean);
    }
    
    public E getBean(int id) throws SQLException{
        return getDao().getBean(id);
    }
    
    public List<E> getListaBean() throws SQLException{
        return getDao().findAll();
    }
}

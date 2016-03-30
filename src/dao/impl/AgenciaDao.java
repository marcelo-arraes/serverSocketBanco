/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Agencia;
import dao.generic.GenericDao;
import dao.vo.ParametroDao;
import dao.vo.TipoParametroDao;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marcelo
 */
public class AgenciaDao extends GenericDao<Agencia> {

    public AgenciaDao() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    }

    @Override
    protected String getSQLAdicionar() {
        return "insert into agencia (numero, nome) values(?,?)";
    }

    @Override
    protected String getSQLAlterar() {
        return "update agencia set numero = ?, nome = ? where id = ?";
    }

    @Override
    protected String getSQLRemover() {
        return "delete from agencia where id = ?";
    }

    @Override
    protected String getSQLToBeanForID() {
        return "select * from agencia where id = ?";
    }

    @Override
    protected String getSQLToBeanAfterAdd() {
        return "select * from agencia a1 where a1.numero = ?";
    }

    @Override
    protected ParametroDao[] getParametrosAfterAdicionar(Agencia bean) {
        ParametroDao[] parametros = new ParametroDao[1];
        parametros[0] = new ParametroDao(1, bean.getNumero(), TipoParametroDao.STRING);

        return parametros;
    }
    
    @Override
    protected String getSQLListAll() {
        return "select * from agencia";
    }

    @Override
    protected ParametroDao[] getParametrosAdicionar(Agencia bean) {
        ParametroDao[] parametros = new ParametroDao[2];
        parametros[0] = new ParametroDao(1, bean.getNumero(), TipoParametroDao.STRING);
        parametros[1] = new ParametroDao(2, bean.getNome(), TipoParametroDao.STRING);

        return parametros;
    }

    @Override
    protected ParametroDao[] getParametrosAlterar(Agencia bean) {
        ParametroDao[] parametros = new ParametroDao[3];
        parametros[0] = new ParametroDao(1, bean.getNumero(), TipoParametroDao.STRING);
        parametros[1] = new ParametroDao(2, bean.getNome(), TipoParametroDao.STRING);
        parametros[2] = new ParametroDao(3, bean.getId(), TipoParametroDao.INTEGER);

        return parametros;
    }

    @Override
    protected ParametroDao[] getParametrosRemover(Agencia bean) {
        ParametroDao[] parametros = new ParametroDao[1];
        parametros[0] = new ParametroDao(1, bean.getId(), TipoParametroDao.INTEGER);

        return parametros;
    }

    @Override
    protected Agencia getBeanFromResultSet(ResultSet rs) throws SQLException {
        Agencia agencia = new Agencia();
        agencia.setId(rs.getInt("id"));
        agencia.setNome(rs.getString("nome"));
        agencia.setNumero(rs.getString("numero"));

        return agencia;
    }

}

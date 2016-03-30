/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Conta;
import dao.generic.GenericDao;
import dao.vo.ParametroDao;
import dao.vo.TipoParametroDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo
 */
public class ContaDao extends GenericDao<Conta> {

    public ContaDao() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    }

    @Override
    protected String getSQLAdicionar() {
        return "insert into conta (numero, tipo_conta, senha, saldo, id_cliente, id_agencia) values(?,?,?,?,?,?)";
    }

    @Override
    protected String getSQLAlterar() {
        return "update conta set numero = ?, tipo_conta = ?, senha = ?, saldo = ?, id_cliente = ?, id_agencia = ? where id = ?";
    }

    @Override
    protected String getSQLRemover() {
        return "delete from conta where id = ?";
    }

    @Override
    protected String getSQLToBeanForID() {
        return "select * from conta where id = ?";
    }

    @Override
    protected String getSQLToBeanAfterAdd() {
        return "select * from conta c1 where c1.numero = ?";
    }

    @Override
    protected String getSQLListAll() {
        return "select * from conta";
    }

    public Conta getContaByNumero(String numero) throws SQLException {
        ParametroDao[] parametros = new ParametroDao[1];
        parametros[0] = new ParametroDao(1, numero, TipoParametroDao.STRING);
        ResultSet rs = getQuery(getSQLToBeanAfterAdd(), parametros);

        Conta result = parseResultSetToBean(rs);

        rs.getStatement().close();

        rs.close();
        return result;
    }

    @Override
    protected ParametroDao[] getParametrosAfterAdicionar(Conta bean) {
        ParametroDao[] parametros = new ParametroDao[1];
        parametros[0] = new ParametroDao(1, bean.getNumero(), TipoParametroDao.STRING);

        return parametros;
    }

    @Override
    protected ParametroDao[] getParametrosAdicionar(Conta bean) {
        ParametroDao[] parametros = new ParametroDao[6];
        parametros[0] = new ParametroDao(1, bean.getNumero(), TipoParametroDao.STRING);
        parametros[1] = new ParametroDao(2, bean.getTipoConta(), TipoParametroDao.STRING);
        parametros[2] = new ParametroDao(3, bean.getSenha(), TipoParametroDao.STRING);
        parametros[3] = new ParametroDao(4, bean.getSaldo(), TipoParametroDao.DOUBLE);
        parametros[4] = new ParametroDao(5, bean.getCliente().getId(), TipoParametroDao.INTEGER);
        parametros[5] = new ParametroDao(6, bean.getAgencia().getId(), TipoParametroDao.INTEGER);

        return parametros;

    }

    @Override
    protected ParametroDao[] getParametrosAlterar(Conta bean) {
        ParametroDao[] parametros = new ParametroDao[7];
        parametros[0] = new ParametroDao(1, bean.getNumero(), TipoParametroDao.STRING);
        parametros[1] = new ParametroDao(2, bean.getTipoConta(), TipoParametroDao.STRING);
        parametros[2] = new ParametroDao(3, bean.getSenha(), TipoParametroDao.STRING);
        parametros[3] = new ParametroDao(4, bean.getSaldo(), TipoParametroDao.DOUBLE);
        parametros[4] = new ParametroDao(5, bean.getCliente().getId(), TipoParametroDao.INTEGER);
        parametros[5] = new ParametroDao(6, bean.getAgencia().getId(), TipoParametroDao.INTEGER);
        parametros[6] = new ParametroDao(7, bean.getId(), TipoParametroDao.INTEGER);

        return parametros;
    }

    @Override
    protected ParametroDao[] getParametrosRemover(Conta bean) {
        ParametroDao[] parametros = new ParametroDao[1];
        parametros[0] = new ParametroDao(1, bean.getId(), TipoParametroDao.INTEGER);

        return parametros;
    }

    @Override
    protected Conta getBeanFromResultSet(ResultSet rs) throws SQLException {
        try {
            ClienteDao clienteDao = new ClienteDao();
            AgenciaDao agenciaDao = new AgenciaDao();

            Conta conta = new Conta();
            conta.setId(rs.getInt("id"));
            conta.setNumero(rs.getString("numero"));
            conta.setTipoConta(rs.getString("tipo_conta"));
            conta.setSenha(rs.getString("senha"));
            conta.setSaldo(rs.getDouble("saldo"));
            conta.setCliente(clienteDao.getBean(rs.getInt("id_cliente")));
            conta.setAgencia(agenciaDao.getBean(rs.getInt("id_cliente")));

            return conta;
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            return null;
        }
    }
}

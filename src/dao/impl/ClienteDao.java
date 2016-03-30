/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Cliente;
import bean.Conta;
import dao.generic.GenericDao;
import dao.vo.ParametroDao;
import dao.vo.TipoParametroDao;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marcelo
 */
public class ClienteDao extends GenericDao<Cliente> {

    public ClienteDao() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    }

    @Override
    protected String getSQLAdicionar() {
        return "insert into cliente (nome, cpf, cnpj, endereco) values(?,?,?,?)";
    }

    @Override
    protected String getSQLAlterar() {
        return "update cliente set nome = ?, cpf = ?, cnpj = ?, endereco = ? where id = ?";
    }

    @Override
    protected String getSQLRemover() {
        return "delete from cliente where id = ?";
    }

    @Override
    protected String getSQLToBeanForID() {
        return "select * from cliente where id = ?";
    }

    @Override
    protected String getSQLToBeanAfterAdd() {
        return "select * from cliente c1 where c1.cpf = ? and c1.cnpj = ?";
    }

    @Override
    protected String getSQLListAll() {
        return "select * from cliente";
    }

    @Override
    protected ParametroDao[] getParametrosAfterAdicionar(Cliente bean) {
        ParametroDao[] parametros = new ParametroDao[2];
        parametros[0] = new ParametroDao(1, bean.getCpf(), TipoParametroDao.STRING);
        parametros[1] = new ParametroDao(2, bean.getCnpj(), TipoParametroDao.STRING);

        return parametros;
    }
    
    @Override
    protected ParametroDao[] getParametrosAdicionar(Cliente bean) {
        ParametroDao[] parametros = new ParametroDao[4];
        parametros[0] = new ParametroDao(1, bean.getNome(), TipoParametroDao.STRING);
        parametros[1] = new ParametroDao(2, bean.getCpf(), TipoParametroDao.STRING);
        parametros[2] = new ParametroDao(3, bean.getCnpj(), TipoParametroDao.STRING);
        parametros[3] = new ParametroDao(4, bean.getEndereco(), TipoParametroDao.STRING);

        return parametros;
    }

    @Override
    protected ParametroDao[] getParametrosAlterar(Cliente bean) {
        ParametroDao[] parametros = new ParametroDao[5];
        parametros[0] = new ParametroDao(1, bean.getNome(), TipoParametroDao.STRING);
        parametros[1] = new ParametroDao(2, bean.getCpf(), TipoParametroDao.STRING);
        parametros[2] = new ParametroDao(3, bean.getCnpj(), TipoParametroDao.STRING);
        parametros[3] = new ParametroDao(4, bean.getEndereco(), TipoParametroDao.STRING);
        parametros[4] = new ParametroDao(5, bean.getId(), TipoParametroDao.INTEGER);

        return parametros;
    }

    @Override
    protected ParametroDao[] getParametrosRemover(Cliente bean) {
        ParametroDao[] parametros = new ParametroDao[1];
        parametros[0] = new ParametroDao(1, bean.getId(), TipoParametroDao.INTEGER);

        return parametros;
    }

    @Override
    protected Cliente getBeanFromResultSet(ResultSet rs) throws SQLException {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setCnpj(rs.getString("cnpj"));
            cliente.setEndereco(rs.getString("endereco"));

            return cliente;
    }

}

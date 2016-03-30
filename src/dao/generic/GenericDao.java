package dao.generic;

import bean.CodeBase;
import dao.vo.TipoParametroDao;
import core.ConexaoBanco;
import dao.vo.ParametroDao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <E>
 *
 *<p>Classe gen�rica para a persist�ncia de dados</p>
 */
public abstract class GenericDao<E extends CodeBase> implements Serializable {

	public GenericDao() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		super();
		this.conn = ConexaoBanco.getConnection();
	}

	private static final long serialVersionUID = -329987815494808089L;

	protected Connection conn;

	
	/**
	 * @param bean
	 * @return bean
	 * 
	 * <p>M�todo da super-classe respons�vel pela inser��o de novo registro no banco de dados</p>
	 */
	public E adicionar(E bean) throws SQLException {

		if (execSQL(getSQLAdicionar(), getParametrosAdicionar(bean))) {
			return getBeanFromAdicionar(bean);
		}
		
		return null;
	}
	
	/**
	 * @param bean
	 * @return bean
	 * 
	 * <p>M�todo da super-classe respons�vel pela altera��o de dados de um registro no banco de dados</p>
	 */
	public E alterar(E bean) throws SQLException {
		if(execSQL(getSQLAlterar(), getParametrosAlterar(bean))){
			return getBean(bean.getId());
		}

		return null;
	}
	
	/**
	 * @param bean
	 * @return boolean
	 * 
	 * <p>M�todo da super-classe respons�vel pela exclus�o de registro do banco de dados</p>
	 */
	public boolean remover(E bean) throws SQLException {
		return execSQL(getSQLRemover(), getParametrosRemover(bean));
	}
	
	/**
	 * @param id
	 * @return bean
	 * 
	 * <p>M�todo da super-classe que recupera do banco de dados um registro atrav�s do id </p>
	 */
	public E getBean(int id) throws SQLException {
		ParametroDao[] parametros = new ParametroDao[1];
		parametros[0] = new ParametroDao(1, id, TipoParametroDao.INTEGER);
			ResultSet rs = getQuery(getSQLToBeanForID(), parametros);
			
			E result = parseResultSetToBean(rs);
			
			rs.getStatement().close();
			
			rs.close();
			return result;
			
	}
	
	/**
	 * @return List<<bean>>
	 * 
	 * <p>M�todo da super-classe que recupera do banco de dados uma lista de registro</p>
	 */
	public List<E> findAll() throws SQLException {
			ResultSet rs = getQuery(getSQLListAll(), null);
			
			List<E> result = parseResultSetToList(rs);
			
			rs.getStatement().close();
			
			rs.close();
			return result;
			
	}
	
	/**
	 * @param id
	 * @return bean
	 * 
	 * <p>M�todo privado que recupera do banco de dados um registro ap�s ter sido inserido. 
	 * Este metodo foi necess�rio para recuperar o registro com o seu id que foi definido pelo banco de dados.</p>
	 */
	private E getBeanFromAdicionar(E bean) throws SQLException {
			ResultSet rs = getQuery(getSQLToBeanAfterAdd(), getParametrosAfterAdicionar(bean));

			E result = parseResultSetToBean(rs);

			rs.getStatement().close();

			rs.close();

			return result;
	}

	/**
	 * @param sql
	 * @param parametros
	 * @return boolean
	 * @throws SQLException
	 * 
	 * <p>M�todo privado que executa comando SQL no banco de dados. 
	 * Inicialmente executa os comandos INSERT, UPDATE e DELETE.</p>
	 */
	protected boolean execSQL(String sql, ParametroDao[] parametros) throws SQLException {
		
			PreparedStatement stmt = conn.prepareStatement(sql);

			setParametrosStmt(stmt, parametros);

			stmt.execute();

			stmt.close();

			return true;
	}

	/**
	 * @param stmt (PreparedStatement)
	 * @param parametros (ParametroDao[])
	 * 
	 * <p>M�todo respons�vel por definir os valores dos curingas nos comandos SQL</p>
	 */
	private void setParametrosStmt(PreparedStatement stmt,
			ParametroDao[] parametros) throws SQLException {
		if (parametros != null && parametros.length > 0) {
			for (ParametroDao item : parametros) {
				if (item.getTipoParametro().getIndice() == TipoParametroDao.STRING.getIndice()) {
					stmt.setString(item.getIndice(), (String) item.getValor());
				} else if (item.getTipoParametro().getIndice() == TipoParametroDao.INTEGER.getIndice()) {
					stmt.setInt(item.getIndice(), ((Integer) item.getValor()).intValue());
				} else if (item.getTipoParametro().getIndice() == TipoParametroDao.LONG.getIndice()) {
					stmt.setLong(item.getIndice(), ((Long) item.getValor()).intValue());
				} else if (item.getTipoParametro().getIndice() == TipoParametroDao.DATE.getIndice()) {
					stmt.setDate(item.getIndice(), new Date(((java.util.Date) item.getValor()).getTime()));
				} else if (item.getTipoParametro().getIndice() == TipoParametroDao.DOUBLE.getIndice()) {
					stmt.setDouble(item.getIndice(), (Double) item.getValor());
				} else if (item.getTipoParametro().getIndice() == TipoParametroDao.FLOAT.getIndice()) {
					stmt.setFloat(item.getIndice(), (Float) item.getValor());
				} else if (item.getTipoParametro().getIndice() == TipoParametroDao.BOOLEAN.getIndice()) {
					stmt.setBoolean(item.getIndice(), (Boolean) item.getValor());
				}
			}
		}
	}

	/**
	 * @param sql (String)
	 * @param parametros (ParametroDao[])
	 * @return ResultSet
	 * @throws SQLException
	 * 
	 * <p>M�todo respons�vel por executar uma consulta no banco de dados</p>
	 */
	protected ResultSet getQuery(String sql, ParametroDao[] parametros) throws SQLException {
			PreparedStatement stmt = conn.prepareStatement(sql);

			setParametrosStmt(stmt, parametros);

			ResultSet rs = stmt.executeQuery();

			return rs;

	}

	
	/**
	 * @param rs (ResultSet)
	 * @return bean
	 * 
	 * <p>M�todo respons�vel por carregar um bean atrav�s de um ResultSet</p>
	 */
	protected E parseResultSetToBean(ResultSet rs) throws SQLException {
		if(rs != null){
				if(rs.next()){
					return getBeanFromResultSet(rs);
				}
		}
		return null;
	}
	
	/**
	 * @param rs
	 * @return bean
	 * 
	 * <p>M�todo respons�vel por carregar uma lista de bean atrav�s de um ResultSet</p>
	 */
	protected  List<E> parseResultSetToList(ResultSet rs) throws SQLException {
		if(rs != null){
			List<E> list = new ArrayList();
				while(rs.next()){
					list.add(getBeanFromResultSet(rs));
				}
				
				return list;
		}
		return null;
	}
	
	/**
	 * @return String
	 * 
	 * <p>M�todo respons�vel por fornecer o c�digo SQL para a inser��o de um novo registro no banco de dados</p>
	 */
	protected abstract String getSQLAdicionar();

	/**
	 * @return String
	 * 
	 * <p>M�todo respons�vel por fornecer o c�digo SQL para a altera��o de um registro no banco de dados</p>
	 */
	protected abstract String getSQLAlterar();

	/**
	 * @return String
	 * 
	 * <p>M�todo respons�vel por fornecer o c�digo SQL para a remo��o de um registro no banco de dados</p>
	 */
	protected abstract String getSQLRemover();

	/**
	 * @return String
	 * 
	 * <p>M�todo respons�vel por fornecer o c�digo SQL para a consulta de um registro no banco de dados atrav�s do seu id</p>
	 */
	protected abstract String getSQLToBeanForID();
	
	/**
	 * @return String
	 * 
	 * <p>M�todo respons�vel por fornecer o c�digo SQL para a consulta de um registro no banco de dados logo ap�s ele ser inserido</p>
	 */
	protected abstract String getSQLToBeanAfterAdd();
	
	/**
	 * @return List<E>
	 * 
	 * <p>M�todo respons�vel por fornecer o c�digo SQL para a consulta de uma lista de registro do banco de dados</p>
	 */
	protected abstract String getSQLListAll();
	
	/**
	 * @return ParametroDao[]
	 * 
	 * <p>M�todo respons�vel por fornecer os valores dos curingas para o c�digo SQL na inser��o de um registro no banco de dados</p>
	 */
	protected abstract ParametroDao[] getParametrosAdicionar(E bean);
        
        protected abstract ParametroDao[] getParametrosAfterAdicionar(E bean);

	/**
	 * @return ParametroDao[]
	 * 
	 * <p>M�todo respons�vel por fornecer os valores dos curingas para o c�digo SQL na altera��o de um registro no banco de dados</p>
	 */
	protected abstract ParametroDao[] getParametrosAlterar(E bean);

	/**
	 * @return ParametroDao[]
	 * 
	 * <p>M�todo respons�vel por fornecer os valores dos curingas para o c�digo SQL na exclus�o de um registro no banco de dados</p>
	 */
	protected abstract ParametroDao[] getParametrosRemover(E bean);
	
	/**
	 * @return bean
	 * 
	 * <p>M�todo respons�vel por carregar e fornecer um bean com base no ponteiro do cursos do ResultSet</p>
	 * @throws SQLException 
	 */
	protected abstract E getBeanFromResultSet(ResultSet rs) throws SQLException;
}

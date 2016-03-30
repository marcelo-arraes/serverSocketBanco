package dao.vo;

public class ParametroDao {

	private int indice;
	private Object valor;
	private TipoParametroDao tipoParametro;
	
	public ParametroDao(int indice, Object valor, TipoParametroDao tipoParametro) {
		super();
		this.indice = indice;
		this.valor = valor;
		this.tipoParametro = tipoParametro;
	}

	/**
	 * @return indice (int)
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * @return valor (Object)
	 */
	public Object getValor() {
		return valor;
	}

	/**
	 * @return tipoParametro (TipoParametroDao)
	 */
	public TipoParametroDao getTipoParametro() {
		return tipoParametro;
	}

	
}

package dao.vo;

import java.util.Date;

public enum TipoParametroDao {
	
	INTEGER(1,Integer.class),
	LONG(2,Long.class),
	STRING(3,String.class),
	DATE(4,Date.class),
	FLOAT(5,Float.class),
	DOUBLE(6,Double.class),
	BOOLEAN(7,Boolean.class);
	
	private int indice;
	private Class classe;
	
	
	private TipoParametroDao(int indice, Class valor) {
		this.indice = indice;
		this.classe = valor;
	}
	
	public Class getClasse() {
		return classe;
	}

	public int getIndice() {
		return indice;
	}
	
	/**
	 * <p> Retorna o Enum de um indice passado ou nulo</p>
	 * @param indice (int)
	 * @return e ({@link br.com.supera.model.dao.vo.TipoParametroDao})
	 */
	public static TipoParametroDao getEnum(int indice){
		for(TipoParametroDao e : TipoParametroDao.values()){
			if(e.getIndice() == indice){
				return e;
			}
		}
		
		return null;
	}
	
	/**
	 * <p> Retorna o Enum de uma classe passada ou nulo</p>
	 * @param classe (Class)
	 * @return e ({@link br.com.supera.model.dao.vo.TipoParametroDao})
	 */
	public static TipoParametroDao getEnum(Class classe){
		for(TipoParametroDao e : TipoParametroDao.values()){
			if(e.getClasse().getName().equals(classe.getName())){
				return e;
			}
		}
		
		return null;
	}
	
	/**
	 * <p> Retorna a classe de um �ndice passado ou nulo</p>
	 * @param indice (int)
	 * @return e.getClasse (Class)
	 */
	public static Class getClasse(int indice){
		TipoParametroDao e = getEnum(indice);
		
		if(e!=null){
			return e.getClasse();
		}
		
		return null;
	}
	
	/**
	 * <p> Retorna o �ndice de uma classe passada ou -1</p>
	 * @param classe (Class)
	 * @return e.getIndice() (int)
	 */
	public static int getIndice(Class classe){
		TipoParametroDao e = getEnum(classe);
		
		if(e!=null){
			return e.getIndice();
		}
		
		return -1;
	}
	
}

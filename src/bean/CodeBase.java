package bean;

import java.io.Serializable;

/**
 * 
 *
 *
 *<p>Esta classe ter por finalidade de fornecer, por heran�a, o identificar das entidades do sistema.</p>
 */
public class CodeBase implements Serializable{

	public CodeBase() {
		super();
		this.id = 0;
	}

	private static final long serialVersionUID = -462766652561974613L;
	
	private int id;

	/**
	 * @return id - C�digo identificador de entidades 
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}

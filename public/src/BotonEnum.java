

public enum BotonEnum {
	// Botones de la botonera
	GENERAR("Generar"),
	REINICIAR("Reiniciar"),
	INFORMACION("Informacion"),
	ADD("Añadir");
	
	private String valor;
	
	/**
	 * Constructor para el string de cada enumerado
	 * @param valor Define el valor del enumerado
	 */
	private BotonEnum (String valor) {
		this.valor = valor;
	}
	
	/**
	 * Getter
	 * @return Devuelve el nombre del boton
	 */
	public String getTexto ()	{
		return valor;
	}
	
	
}

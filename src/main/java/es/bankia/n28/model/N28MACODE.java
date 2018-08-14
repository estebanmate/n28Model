package es.bankia.n28.model;

/**
 * API model for the MACODE.
 *
 */
public class N28MACODE {

	private String titularCuenta;
	private String idUnico;
	private String importeIngreso;
	private String cifContribuyente;
	private String nombreContribuyente;
	private String apellido1Contribuyente;
	private String apellido2Contribuyente;
	private String oficinaLiquidadora;
	private String hechoImponible;

	public N28MACODE() {

	}

	public String getTitularCuenta() {
		return titularCuenta;
	}

	public void setTitularCuenta(String titularCuenta) {
		this.titularCuenta = titularCuenta;
	}

	public String getIdUnico() {
		return idUnico;
	}

	public void setIdUnico(String idUnico) {
		this.idUnico = idUnico;
	}

	public String getImporteIngreso() {
		return importeIngreso;
	}

	public void setImporteIngreso(String importeIngreso) {
		this.importeIngreso = importeIngreso;
	}

	public String getCifContribuyente() {
		return cifContribuyente;
	}

	public void setCifContribuyente(String cifContribuyente) {
		this.cifContribuyente = cifContribuyente;
	}

	public String getNombreContribuyente() {
		return nombreContribuyente;
	}

	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}

	public String getApellido1Contribuyente() {
		return apellido1Contribuyente;
	}

	public void setApellido1Contribuyente(String apellido1Contribuyente) {
		this.apellido1Contribuyente = apellido1Contribuyente;
	}

	public String getApellido2Contribuyente() {
		return apellido2Contribuyente;
	}

	public void setApellido2Contribuyente(String apellido2Contribuyente) {
		this.apellido2Contribuyente = apellido2Contribuyente;
	}

	public String getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public String getHechoImponible() {
		return hechoImponible;
	}

	public void setHechoImponible(String hechoImponible) {
		this.hechoImponible = hechoImponible;
	}

	@Override
	public String toString() {
		return titularCuenta +  idUnico +  importeIngreso +  cifContribuyente + nombreContribuyente + apellido1Contribuyente
				+ apellido2Contribuyente + oficinaLiquidadora + hechoImponible;
	}
}
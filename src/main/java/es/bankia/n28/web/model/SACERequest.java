package es.bankia.n28.web.model;

/**
 * API model for the SACE Request.
 *
 */
public class SACERequest {

	private String titularCuenta;
	private String idUnico;
	private String cuentaCorriente;
	private String importeIngreso;
	private String cifContribuyente;
	private String nombreContribuyente;
	private String apellido1Contribuyente;
	private String apellido2Contribuyente;
	private String oficinaLiquidadora;
	private String hechoImponible;

	public SACERequest() {

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

	public String getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(String cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido1Contribuyente == null) ? 0 : apellido1Contribuyente.hashCode());
		result = prime * result + ((apellido2Contribuyente == null) ? 0 : apellido2Contribuyente.hashCode());
		result = prime * result + ((cifContribuyente == null) ? 0 : cifContribuyente.hashCode());
		result = prime * result + ((cuentaCorriente == null) ? 0 : cuentaCorriente.hashCode());
		result = prime * result + ((hechoImponible == null) ? 0 : hechoImponible.hashCode());
		result = prime * result + ((idUnico == null) ? 0 : idUnico.hashCode());
		result = prime * result + ((importeIngreso == null) ? 0 : importeIngreso.hashCode());
		result = prime * result + ((nombreContribuyente == null) ? 0 : nombreContribuyente.hashCode());
		result = prime * result + ((oficinaLiquidadora == null) ? 0 : oficinaLiquidadora.hashCode());
		result = prime * result + ((titularCuenta == null) ? 0 : titularCuenta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SACERequest other = (SACERequest) obj;
		if (apellido1Contribuyente == null) {
			if (other.apellido1Contribuyente != null)
				return false;
		} else if (!apellido1Contribuyente.equals(other.apellido1Contribuyente))
			return false;
		if (apellido2Contribuyente == null) {
			if (other.apellido2Contribuyente != null)
				return false;
		} else if (!apellido2Contribuyente.equals(other.apellido2Contribuyente))
			return false;
		if (cifContribuyente == null) {
			if (other.cifContribuyente != null)
				return false;
		} else if (!cifContribuyente.equals(other.cifContribuyente))
			return false;
		if (cuentaCorriente == null) {
			if (other.cuentaCorriente != null)
				return false;
		} else if (!cuentaCorriente.equals(other.cuentaCorriente))
			return false;
		if (hechoImponible == null) {
			if (other.hechoImponible != null)
				return false;
		} else if (!hechoImponible.equals(other.hechoImponible))
			return false;
		if (idUnico == null) {
			if (other.idUnico != null)
				return false;
		} else if (!idUnico.equals(other.idUnico))
			return false;
		if (importeIngreso == null) {
			if (other.importeIngreso != null)
				return false;
		} else if (!importeIngreso.equals(other.importeIngreso))
			return false;
		if (nombreContribuyente == null) {
			if (other.nombreContribuyente != null)
				return false;
		} else if (!nombreContribuyente.equals(other.nombreContribuyente))
			return false;
		if (oficinaLiquidadora == null) {
			if (other.oficinaLiquidadora != null)
				return false;
		} else if (!oficinaLiquidadora.equals(other.oficinaLiquidadora))
			return false;
		if (titularCuenta == null) {
			if (other.titularCuenta != null)
				return false;
		} else if (!titularCuenta.equals(other.titularCuenta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SACERequest [titularCuenta=" + titularCuenta + ", idUnico=" + idUnico + ", cuentaCorriente="
				+ cuentaCorriente + ", importeIngreso=" + importeIngreso + ", cifContribuyente=" + cifContribuyente
				+ ", nombreContribuyente=" + nombreContribuyente + ", apellido1Contribuyente=" + apellido1Contribuyente
				+ ", apellido2Contribuyente=" + apellido2Contribuyente + ", oficinaLiquidadora=" + oficinaLiquidadora
				+ ", hechoImponible=" + hechoImponible + "]";
	}

}
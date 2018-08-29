package es.bankia.n28.web.model;

/**
 * API model for the N28 Account.
 *
 */
public class N28Account {

	private String IBAN;
	private String entidad;
	private String oficina;
	private String dc;
	private String cuenta;

	public N28Account() {

	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IBAN == null) ? 0 : IBAN.hashCode());
		result = prime * result + ((cuenta == null) ? 0 : cuenta.hashCode());
		result = prime * result + ((dc == null) ? 0 : dc.hashCode());
		result = prime * result + ((entidad == null) ? 0 : entidad.hashCode());
		result = prime * result + ((oficina == null) ? 0 : oficina.hashCode());
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
		N28Account other = (N28Account) obj;
		if (IBAN == null) {
			if (other.IBAN != null)
				return false;
		} else if (!IBAN.equals(other.IBAN))
			return false;
		if (cuenta == null) {
			if (other.cuenta != null)
				return false;
		} else if (!cuenta.equals(other.cuenta))
			return false;
		if (dc == null) {
			if (other.dc != null)
				return false;
		} else if (!dc.equals(other.dc))
			return false;
		if (entidad == null) {
			if (other.entidad != null)
				return false;
		} else if (!entidad.equals(other.entidad))
			return false;
		if (oficina == null) {
			if (other.oficina != null)
				return false;
		} else if (!oficina.equals(other.oficina))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "N28Account [IBAN=" + IBAN + ", entidad=" + entidad + ", oficina=" + oficina + ", dc=" + dc + ", cuenta="
				+ cuenta + "]";
	}

}
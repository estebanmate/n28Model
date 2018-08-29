package es.bankia.n28.web.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class N28Proof {

	private String cuentaCargo;
	private String fechaOperacion;
	private String horaOperacion;
	private String importe;
	private String numeroSerie;
	private String MACODE;
	private String nif;
	private String nombre;
	private String apellidos;

	public String getCuentaCargo() {
		return cuentaCargo;
	}

	public void setCuentaCargo(String cuentaCargo) {
		this.cuentaCargo = cuentaCargo;
	}

	public String getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getHoraOperacion() {
		return horaOperacion;
	}

	public void setHoraOperacion(String horaOperacion) {
		this.horaOperacion = horaOperacion;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getMACODE() {
		return MACODE;
	}

	public void setMACODE(String mACODE) {
		MACODE = mACODE;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MACODE == null) ? 0 : MACODE.hashCode());
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((cuentaCargo == null) ? 0 : cuentaCargo.hashCode());
		result = prime * result + ((fechaOperacion == null) ? 0 : fechaOperacion.hashCode());
		result = prime * result + ((horaOperacion == null) ? 0 : horaOperacion.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroSerie == null) ? 0 : numeroSerie.hashCode());
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
		N28Proof other = (N28Proof) obj;
		if (MACODE == null) {
			if (other.MACODE != null)
				return false;
		} else if (!MACODE.equals(other.MACODE))
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (cuentaCargo == null) {
			if (other.cuentaCargo != null)
				return false;
		} else if (!cuentaCargo.equals(other.cuentaCargo))
			return false;
		if (fechaOperacion == null) {
			if (other.fechaOperacion != null)
				return false;
		} else if (!fechaOperacion.equals(other.fechaOperacion))
			return false;
		if (horaOperacion == null) {
			if (other.horaOperacion != null)
				return false;
		} else if (!horaOperacion.equals(other.horaOperacion))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroSerie == null) {
			if (other.numeroSerie != null)
				return false;
		} else if (!numeroSerie.equals(other.numeroSerie))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "N28Proof [cuentaCargo=" + cuentaCargo + ", fechaOperacion=" + fechaOperacion + ", horaOperacion="
				+ horaOperacion + ", importe=" + importe + ", numeroSerie=" + numeroSerie + ", MACODE=" + MACODE
				+ ", nif=" + nif + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
	}

}

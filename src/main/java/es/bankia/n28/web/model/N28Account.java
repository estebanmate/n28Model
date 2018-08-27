package es.bankia.n28.web.model;

/**
 * API model for the CCT MACODE.
 *
 */
public class N28Account {

	private String n28;
	private String importeIngreso;
	private String fechaIngreso;
	private String entidad;

	public N28Account() {

	}

	public String getN28() {
		return n28;
	}

	public void setN28(String n28) {
		this.n28 = n28;
	}

	public String getImporteIngreso() {
		return importeIngreso;
	}

	public void setImporteIngreso(String importeIngreso) {
		this.importeIngreso = importeIngreso;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	@Override
	public String toString() {
		return n28 + importeIngreso + fechaIngreso + entidad;
	}
}
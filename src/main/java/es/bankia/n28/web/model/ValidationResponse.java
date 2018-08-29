package es.bankia.n28.web.model;

/**
 * API model for the Account validation response.
 *
 */
public class ValidationResponse {

	private String codResultado;
	private String msgResultado;

	public ValidationResponse() {

	}

	public String getCodResultado() {
		return codResultado;
	}

	public void setCodResultado(String codResultado) {
		this.codResultado = codResultado;
	}

	public String getMsgResultado() {
		return msgResultado;
	}

	public void setMsgResultado(String msgResultado) {
		this.msgResultado = msgResultado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codResultado == null) ? 0 : codResultado.hashCode());
		result = prime * result + ((msgResultado == null) ? 0 : msgResultado.hashCode());
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
		ValidationResponse other = (ValidationResponse) obj;
		if (codResultado == null) {
			if (other.codResultado != null)
				return false;
		} else if (!codResultado.equals(other.codResultado))
			return false;
		if (msgResultado == null) {
			if (other.msgResultado != null)
				return false;
		} else if (!msgResultado.equals(other.msgResultado))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ValidationResponse [codResultado=" + codResultado + ", msgResultado=" + msgResultado + "]";
	}

}
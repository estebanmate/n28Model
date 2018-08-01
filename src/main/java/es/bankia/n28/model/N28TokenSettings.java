package es.bankia.n28.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class N28TokenSettings {

	@Value("${n28.token.algorithm}")
	private String tokenAlgorithm;

	@Value("${n28.token.encode.transformation}")
	private String tokenEncodeTransformation;

	@Value("${n28.token.decode.transformation}")
	private String tokenDecodeTransformation;

	@Value("${n28.token.charcode}")
	private String tokenCharcode;

	@Value("${n28.token.key}")
	private String tokenKey;

	@Value("${n28.mac.algorithm}")
	private String macAlgorithm;

	@Value("${n28.mac.key}")
	private String macKey;

	public String getTokenAlgorithm() {
		return tokenAlgorithm;
	}

	public void setTokenAlgorithm(String tokenAlgorithm) {
		this.tokenAlgorithm = tokenAlgorithm;
	}

	public String getTokenEncodeTransformation() {
		return tokenEncodeTransformation;
	}

	public void setTokenEncodeTransformation(String tokenEncodeTransformation) {
		this.tokenEncodeTransformation = tokenEncodeTransformation;
	}

	public String getTokenDecodeTransformation() {
		return tokenDecodeTransformation;
	}

	public void setTokenDecodeTransformation(String tokenDecodeTransformation) {
		this.tokenDecodeTransformation = tokenDecodeTransformation;
	}

	public String getTokenCharcode() {
		return tokenCharcode;
	}

	public void setTokenCharcode(String tokenCharcode) {
		this.tokenCharcode = tokenCharcode;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getMacAlgorithm() {
		return macAlgorithm;
	}

	public void setMacAlgorithm(String macAlgorithm) {
		this.macAlgorithm = macAlgorithm;
	}

	public String getMacKey() {
		return macKey;
	}

	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

}

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

	@Value("${n28.maccct.algorithm}")
	private String maccctAlgorithm;

	@Value("${n28.maccct.encode.transformation}")
	private String macctEncodeTransformation;

	@Value("${n28.maccct.decode.transformation}")
	private String macctDecodeTransformation;

	@Value("${n28.maccct.charcode}")
	private String macctCharcode;

	@Value("${n28.maccct.key}")
	private String maccctKey;

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

	public String getMaccctAlgorithm() {
		return maccctAlgorithm;
	}

	public void setMaccctAlgorithm(String maccctAlgorithm) {
		this.maccctAlgorithm = maccctAlgorithm;
	}

	public String getMacctEncodeTransformation() {
		return macctEncodeTransformation;
	}

	public void setMacctEncodeTransformation(String macctEncodeTransformation) {
		this.macctEncodeTransformation = macctEncodeTransformation;
	}

	public String getMacctDecodeTransformation() {
		return macctDecodeTransformation;
	}

	public void setMacctDecodeTransformation(String macctDecodeTransformation) {
		this.macctDecodeTransformation = macctDecodeTransformation;
	}

	public String getMacctCharcode() {
		return macctCharcode;
	}

	public void setMacctCharcode(String macctCharcode) {
		this.macctCharcode = macctCharcode;
	}

	public String getMaccctKey() {
		return maccctKey;
	}

	public void setMaccctKey(String maccctKey) {
		this.maccctKey = maccctKey;
	}

}

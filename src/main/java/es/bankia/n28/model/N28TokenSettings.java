package es.bankia.n28.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class N28TokenSettings {

 	@Value("${n28.token.algorithm}")
	private String algorithm;
	   
	@Value("${n28.token.encode.transformation}")
	private String encode_transformation;

	@Value("${n28.token.decode.transformation}")
	private String decode_transformation;
	
	@Value("${n28.token.charcode}")
	private String charcode;
	
	@Value("${n28.token.key}")
    private String key;

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getEncode_transformation() {
		return encode_transformation;
	}

	public void setEncode_transformation(String encode_transformation) {
		this.encode_transformation = encode_transformation;
	}

	public String getDecode_transformation() {
		return decode_transformation;
	}

	public void setDecode_transformation(String decode_transformation) {
		this.decode_transformation = decode_transformation;
	}

	public String getCharcode() {
		return charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
}

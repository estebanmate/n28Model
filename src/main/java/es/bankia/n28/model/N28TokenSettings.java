package es.bankia.n28.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class N28TokenSettings {

 	@Value("${n28.token.algorithm}")
	private String algorithm;
	   
	@Value("${n28.token.transformation}")
	private String transformation;
	   
	@Value("${n28.token.key}")
    private String key;

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getTransformation() {
		return transformation;
	}

	public void setTransformation(String transformation) {
		this.transformation = transformation;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}

package es.bankia.n28.web.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import es.bankia.n28.cifrado.model.N28CCTMACODE;
import es.bankia.n28.cifrado.model.N28MACODE;
import es.bankia.n28.cifrado.model.N28ModelSettings;
import es.bankia.n28.web.model.N28Account;
import es.bankia.n28.web.model.N28Proof;
import es.bankia.n28.web.model.SACERequest;
import es.bankia.n28.web.model.SACEResponse;
import es.bankia.n28.web.model.ValidationResponse;

import java.util.ArrayList;
import java.util.Base64;

@Service
public class N28WebService {

	public ValidationResponse validate_Account(N28Account n28Account) {

        //TODO: Validación de cuenta
		return new ValidationResponse();

	}

	public SACEResponse call_SACE(SACERequest sACERequest) {

        //TODO: Llamadas a SACE
		return new SACEResponse();
		
	}

	public String generate_Proof(N28Proof n28Proof) {

		//TODO: Generar PDF
		return new String();
		
	}

}
package es.bankia.n28.web.service;


import org.springframework.stereotype.Service;

import es.bankia.n28.web.model.N28Account;
import es.bankia.n28.web.model.N28Proof;
import es.bankia.n28.web.model.SACERequest;
import es.bankia.n28.web.model.SACEResponse;
import es.bankia.n28.web.model.ValidationResponse;


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
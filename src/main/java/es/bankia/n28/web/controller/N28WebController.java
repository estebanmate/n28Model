package es.bankia.n28.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.bankia.n28.web.model.N28Account;
import es.bankia.n28.web.model.N28Proof;
import es.bankia.n28.web.model.SACERequest;
import es.bankia.n28.web.model.SACEResponse;
import es.bankia.n28.web.model.ValidationResponse;
import es.bankia.n28.web.service.N28WebService;

@RestController
@RequestMapping("n28Web")
public class N28WebController {
	@Autowired
	private N28WebService n28WebService;


	/**
	 * Valida los datos de cuenta en pantalla (titularidad y modo de cuenta).
	 *
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validate_account", method = POST)
	public ResponseEntity<ValidationResponse> validate_Account(@RequestBody N28Account n28Account) throws Exception {

		ValidationResponse validationResponse = n28WebService.validate_Account(n28Account);

		if (null != validationResponse) {
			return new ResponseEntity<>(validationResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(validationResponse, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * Llama a SACE para la gestión del cobro
	 *
	 * @param sACERequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "call_sace", method = POST)
	public ResponseEntity<SACEResponse> call_SACE(@RequestBody SACERequest sACERequest) throws Exception {

		SACEResponse sACEResponse = n28WebService.call_SACE(sACERequest);

		if (null != sACEResponse) {
			return new ResponseEntity<>(sACEResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(sACEResponse, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * Genera el justificante de pago para el usuario
	 *
	 * @param Proof
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "generate_proof", method = POST)
	public ResponseEntity<String> generate_Proof(@RequestBody N28Proof n28Proof) throws Exception {

		String proof = n28WebService.generate_Proof(n28Proof);

		if (null != proof) {
			return new ResponseEntity<>(proof, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(proof, HttpStatus.NO_CONTENT);
		}

	}
	
}
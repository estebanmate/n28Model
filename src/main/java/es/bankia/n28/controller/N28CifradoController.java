package es.bankia.n28.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.bankia.n28.cifrado.model.N28MACODE;
import es.bankia.n28.cifrado.model.N28TOKEN_REQUEST;
import es.bankia.n28.cifrado.model.N28TOKEN_REPLY;
import es.bankia.n28.cifrado.service.N28MacService;
import es.bankia.n28.cifrado.service.N28TokenService;

@RestController
@RequestMapping("n28")
public class N28CifradoController {

	@Autowired
	private N28TokenService n28TokenService;

	@Autowired
	private N28MacService n28MacService;

	/**
	 * Encripta un token para ser enviado a la CARM.
	 *
	 * @param XMLFormat
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "get_token", method = POST)
	public ResponseEntity<N28TOKEN_REPLY> get_Token(@RequestBody String TOKEN_REPLY) throws Exception {

		String token = n28TokenService.get_Token(TOKEN_REPLY);
		N28TOKEN_REPLY n28Token = new N28TOKEN_REPLY();
		n28Token.setToken(token);

		if (null != n28Token && null != n28Token.getToken()) {
			return new ResponseEntity<>(n28Token, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(n28Token, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * Obtiene un MACODE a partir de los datos de TOKEN_REQUEST para su validación
	 *
	 * @param XMLFormat
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "get_mac", method = POST)
	public ResponseEntity<String> get_MAC(@RequestBody N28MACODE n28Macode) throws Exception {

		String mac = n28MacService.get_MAC(n28Macode);

		if (null != mac && !"".equals(mac)) {
			return new ResponseEntity<>(mac, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(mac, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * Desencripta un token enviado por CARM en la TOKEN_REQUEST.
	 *
	 * @param n28TokenRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validate_token", method = POST)
	public ResponseEntity<String> validate_Toke(@RequestBody N28TOKEN_REQUEST n28TokenRequest) throws Exception {

		String xmlRequest = n28TokenService.validate_Token(n28TokenRequest.getToken());

		if (null != xmlRequest && !"".equals(xmlRequest)) {
			return new ResponseEntity<>(xmlRequest, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(xmlRequest, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * Desencripta un MAC
	 *
	 * @param n28TokenRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validate_mac", method = POST)
	public ResponseEntity<String> validate_MAC(@RequestBody String MAC) throws Exception {

		String xmlRequest = n28MacService.validate_MAC(MAC);

		if (null != xmlRequest && !"".equals(xmlRequest)) {
			return new ResponseEntity<>(xmlRequest, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(xmlRequest, HttpStatus.NO_CONTENT);
		}

	}

}

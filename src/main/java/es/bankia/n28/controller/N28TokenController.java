package es.bankia.n28.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.bankia.n28.model.N28TokenRequest;
import es.bankia.n28.model.N28TokenResponse;
import es.bankia.n28.service.N28TokenService;

@RestController
@RequestMapping("n28")
public class N28TokenController {

    @Autowired
    private N28TokenService n28TokenService;
    
    /**
     * Encripta/Desencripta un token enviado por CARM.
     *
     * @param credentials
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "validate_token", method = POST)
    public ResponseEntity<N28TokenResponse> getXML(@RequestBody N28TokenRequest n28TokenRequest) throws Exception {

        String token = n28TokenService.decrypt(n28TokenRequest.getToken().getBytes());
        N28TokenResponse n28Token = new N28TokenResponse();
        n28Token.setToken(token);
        
		if(null != n28Token && null != n28Token.getToken()) {
			return new ResponseEntity<>(n28Token, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(n28Token, HttpStatus.NO_CONTENT);
		}
        
    }

}

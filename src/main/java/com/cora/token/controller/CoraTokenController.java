package com.cora.token.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cora.token.model.CoraToken;
import com.cora.token.model.CoraTokenDetails;
import com.cora.token.model.UserCredentials;
import com.cora.token.service.CoraTokenService;
import com.cora.token.service.SendChannelService;

@RestController
@RequestMapping("cora")
public class CoraTokenController {


    @Autowired
    private SendChannelService sendChannelService;

    @Autowired
    private CoraTokenService authenticationTokenService;

    /**
     * Valida las credenciales del usuario y genera un token.
     *
     * @param credentials
     * @return
     */
    @RequestMapping(value = "get_token", method = POST)
    public ResponseEntity<CoraToken> getToken(@RequestBody UserCredentials credentials) {

        sendChannelService.validateSendChannel(credentials.getEmail(), credentials.getSmsNumber());
        String token = authenticationTokenService.getToken(credentials.getUsername(), credentials.getPlattform());
        CoraToken authenticationToken = new CoraToken();
        authenticationToken.setToken(token);
        
		if(null != authenticationToken && null != authenticationToken.getToken()) {
			return new ResponseEntity<>(authenticationToken, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(authenticationToken, HttpStatus.NO_CONTENT);
		}
        
    }

    /**
     * Valida el token recibido
     *
     * @param authenticationToken
     * @return
     */
    @RequestMapping(value = "validate_token", method = POST)
    public ResponseEntity<CoraTokenDetails> validateToken(@RequestBody CoraToken authenticationToken) {

        CoraTokenDetails authenticationTokenDetails = authenticationTokenService.tokenValidation(authenticationToken.getToken());

		if(null != authenticationTokenDetails && null != authenticationTokenDetails.getId()) {
			return new ResponseEntity<>(authenticationTokenDetails, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(authenticationTokenDetails, HttpStatus.NO_CONTENT);
		}
    }

}

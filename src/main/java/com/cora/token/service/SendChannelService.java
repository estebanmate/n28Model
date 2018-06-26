package com.cora.token.service;

import org.springframework.stereotype.Service;

import com.cora.token.exception.SendChannelException;
import com.cora.token.model.UserCredentials;

@Service
public class SendChannelService {

    /**
     * Validate email and SMS Number
     *
     * @param email
     * @param smsNumber
     * @return
     */
    public void validateSendChannel(UserCredentials credentials) {

        if ((null == credentials.getEmail() || ("").equals(credentials.getEmail())) && (null == credentials.getSmsNumber() || ("").equals(credentials.getSmsNumber()))) {
            throw new SendChannelException("Error en los canales de envío. Es necesario al menos uno.");
        }

        if (null == credentials.getSmsNumber() || ("").equals(credentials.getSmsNumber())) {
        	System.out.println("Envío a mail: "+credentials.getEmail());
        	//TODO: Marcar email para envío
        } else {
        	//TODO: Marcar móvil para envío
        	System.out.println("Envío a tfno: "+credentials.getSmsNumber());
        }
    }
}
package com.cora.token.service;


import org.springframework.stereotype.Service;

import com.cora.token.exception.SendChannelException;

@Service
public class SendChannelService {

    /**
     * Validate email and SMS Number
     *
     * @param email
     * @param smsNumber
     * @return
     */
    public void validateSendChannel(String email, String smsNumber) {

        if (null == email && null == smsNumber) {
            throw new SendChannelException("Error en los canales de envío. Es necesario al menos uno.");
        }
//
//        if (!user.isActive()) {
//            // User is not active
//            throw new SendChannelException("The user is inactive.");
//        }
//
//        if (!passwordEncoder.checkPassword(password, user.getPassword())) {
//            // Invalid password
//            throw new AuthenticationException("Bad credentials.");
//        }
    }
}
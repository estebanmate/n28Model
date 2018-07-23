package es.bankia.n28.service;

import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import es.bankia.n28.exception.N28TokenException;
import es.bankia.n28.model.N28TokenSettings;

/**
 * Service which provides operations for encrypt/decrypt tokens.
 *
 */
@Service
public class N28TokenService {

	@Autowired
    private N28TokenSettings settings;
	
	 public KeyGenerator keygenerator;
	 public SecretKeySpec bankiaKey;
	 public Cipher cipher;
     public byte[] ivBytes = new byte[]{0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};
     public IvParameterSpec ivectorSpecv;

 	 public byte[] encrypt(String s) {

		try {
			this.setParameters();
			
			cipher.init(Cipher.ENCRYPT_MODE, bankiaKey, ivectorSpecv);
	
			return(cipher.doFinal(s.getBytes()));
			 
	     } catch (Exception e) {
	        throw new N28TokenException("Error al encriptar Token", e);
	     }	     
	       
	 }
	 
	 public String decrypt(byte[] s) {
	 
		try {
			
			this.setParameters();
			
			cipher.init(Cipher.DECRYPT_MODE, bankiaKey, ivectorSpecv);
		   
		    return(new String(cipher.doFinal(s), settings.getCharcode()));
		     
	     } catch (Exception e) {
	         throw new N28TokenException("Error al desencriptar Token", e);
	     }	     
	 }
	 
	 private void setParameters() throws NoSuchAlgorithmException, NoSuchPaddingException {
 		 bankiaKey = new SecretKeySpec(settings.getKey().getBytes(), settings.getAlgorithm());
		 cipher = Cipher.getInstance(settings.getTransformation());
		 ivectorSpecv = new IvParameterSpec(ivBytes);
	 }
}
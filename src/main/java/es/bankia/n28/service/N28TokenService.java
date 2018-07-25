package es.bankia.n28.service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.bankia.n28.beans.LIQUIDACIONAUTOLIQUIDACION;
import es.bankia.n28.model.N28TokenSettings;

import java.util.Base64;

@Service
public class N28TokenService {
	
	@Autowired
	public N28TokenSettings settings;

    private static byte[] keyiv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00 };

     public String encode(String args) {

        byte[] encoding;
        
        try {
	        encoding = Base64.getEncoder().encode(args.getBytes(settings.getCharcode()));
	
	        byte[] str5 = des3EncodeCBC(settings.getKey().getBytes(), keyiv, encoding);
	
	        byte[] encoding1 = Base64.getEncoder().encode(str5);
	        
	        System.out.println("Texto a encriptar ==>  " + args);
	
	        System.out.println("Token texto encriptado ==> " + new String(encoding1));
	
	        return new String(encoding1);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String decode(String args) {
        try {
        
	        System.out.println("Token a desencriptar ==> " + new String(args.getBytes(settings.getCharcode())));
	
	        byte[] decode = Base64.getDecoder().decode(args.getBytes(settings.getCharcode()));
	 
	        byte[] str6 = des3DecodeCBC(settings.getKey().getBytes(), keyiv, decode);
	 
	        String data=new String(str6);
	        
	        byte[] decode1 = Base64.getDecoder().decode(data.trim().getBytes(settings.getCharcode()));
	        
	        System.out.println("Texto desencriptado ==>  " + new String(decode1));
	        
	        System.out.println("Datos Desencriptados ==>  ");
	        
	        generaXML(new String(decode1));
	        
	        return new String(decode1);
        } catch (JAXBException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        
        return null;

    }

    private byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(settings.getAlgorithm());
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance(settings.getEncode_transformation());
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] bout = cipher.doFinal(data);
            return bout;

        } catch (Exception e) {
            System.out.println("Error en generación de TOKEN_REPLY" + e);
        }
        
        return null;

    }

    private byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(settings.getAlgorithm());
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance(settings.getEncode_transformation());
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            byte[] bout = cipher.doFinal(data);

            return bout;

        } catch (Exception e) {
            System.out.println("Error en validación de TOKEN_REQUEST" + e);
        }
        return null;

    }
    
    private void generaXML(String cadenaXML) throws JAXBException {
    	JAXBContext jaxbContext = JAXBContext.newInstance(LIQUIDACIONAUTOLIQUIDACION.class);

    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    	LIQUIDACIONAUTOLIQUIDACION xmlClass = 
    		(LIQUIDACIONAUTOLIQUIDACION)unmarshaller.unmarshal(
    			new ByteArrayInputStream(cadenaXML.getBytes()
    			));

       	System.out.println("ID Comunicación: "+xmlClass.getREQUEST().getCABECERA().getIDCOMUNICACION());        
       	System.out.println("APLICACION/EMISOR: "+xmlClass.getREQUEST().getCABECERA().getAPLICACION()+"/"+xmlClass.getREQUEST().getCABECERA().getEMISOR());        
    	
       	System.out.println("URL vuelta: "+xmlClass.getREQUEST().getURLCOMUNICACION().getURLVUELTA());        
       	System.out.println("URL Notificación: "+xmlClass.getREQUEST().getURLCOMUNICACION().getURLNOTIFICACION());        
       	
       	System.out.println("Titular de la Cuenta: "+xmlClass.getREQUEST().getLOTE().getCARGO().getTITULARCUENTA());        
    	System.out.println("Importe: "+xmlClass.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getIMPORTEINGRESO());  
    	System.out.println("MACODE: "+xmlClass.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getMACODE());   
    	
    }

}
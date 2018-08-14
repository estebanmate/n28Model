package es.bankia.n28.service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.bankia.n28.beans.XmlBody;
import es.bankia.n28.model.N28MACODE;
import es.bankia.n28.model.N28TokenSettings;

import java.util.ArrayList;
import java.util.Base64;

@Service
public class N28TokenService {

	@Autowired
	public N28TokenSettings settings;

	private static byte[] keyiv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	static IvParameterSpec iv;

	public String get_Token(String args) {

		byte[] encoding;

		try {
			encoding = Base64.getEncoder().encode(args.getBytes(settings.getTokenCharcode()));

			byte[] str5 = encode3DES_CBC(settings.getTokenKey().getBytes(), keyiv, encoding);

			byte[] encoding1 = Base64.getEncoder().encode(str5);

			System.out.println("Texto a encriptar ==>  " + args);

			System.out.println("Token texto encriptado ==> " + new String(encoding1));

			return new String(encoding1);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String validate_Token(String args) {
		try {

			System.out.println("Token a desencriptar ==> " + new String(args.getBytes(settings.getTokenCharcode())));

			byte[] decode = Base64.getDecoder().decode(args.getBytes(settings.getTokenCharcode()));

			byte[] str6 = decode3DES_CBC(settings.getTokenKey().getBytes(), keyiv, decode);

			String data = new String(str6);

			byte[] decode1 = Base64.getDecoder().decode(data.trim().getBytes(settings.getTokenCharcode()));

			System.out.println("Texto desencriptado ==>  " + new String(decode1));

//			System.out.println("Datos Desencriptados ==>  ");

			XmlBody tokenRequestXmlBody = generaTOKEN_REQUEST_XML(new String(decode1));

			// Generamos los datos mockeados de la respuesta
			String MACCCT = generaMACCCT(tokenRequestXmlBody);

			// TODO: Generar todo el flujo de llamadas a los SNG
			// TODO: Generar el TOKEN_REPLY

			return new String(decode1);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		return null;

	}

	public String get_MAC(N28MACODE n28Macode) {

		byte[] encoding;

		try {

			encoding = Base64.getEncoder().encode(n28Macode.toString().getBytes(settings.getMacCharcode()));

			byte[] macodeStr = encodeDES_CBC(settings.getMacKey().getBytes(), encoding);

			byte[] mac = Base64.getEncoder().encode(macodeStr);

			System.out.println("Texto a encriptar ==>  " + n28Macode.toString());

			System.out.println("MAC ==> " + new String(mac));

			return new String(mac);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public String validate_MAC(String MAC) {

		try {
			return new String(verificaMACODE(MAC));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private byte[] encode3DES_CBC(byte[] key, byte[] keyiv, byte[] data) {
		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(settings.getTokenAlgorithm());
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance(settings.getTokenEncodeTransformation());
			IvParameterSpec ips = new IvParameterSpec(keyiv);
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] bout = cipher.doFinal(data);
			return bout;

		} catch (Exception e) {
			System.out.println("Error en generación de TOKEN_REPLY" + e);
		}

		return null;

	}

	private byte[] decode3DES_CBC(byte[] key, byte[] keyiv, byte[] data) {
		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(settings.getTokenAlgorithm());
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance(settings.getTokenDecodeTransformation());
			IvParameterSpec ips = new IvParameterSpec(keyiv);
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] bout = cipher.doFinal(data);

			return bout;

		} catch (Exception e) {
			System.out.println("Error en validación de TOKEN_REQUEST" + e);
		}
		return null;

	}

	private XmlBody generaTOKEN_REQUEST_XML(String cadenaXML) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(XmlBody.class);

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		XmlBody xmlBody = (XmlBody) unmarshaller.unmarshal(new ByteArrayInputStream(cadenaXML.getBytes()));

//		System.out.println("ID Comunicación: " + xmlBody.getREQUEST().getCABECERA().getIDCOMUNICACION());
//		System.out.println("APLICACION/EMISOR: " + xmlBody.getREQUEST().getCABECERA().getAPLICACION() + "/"
//				+ xmlBody.getREQUEST().getCABECERA().getEMISOR());
//
//		System.out.println("URL vuelta: " + xmlBody.getREQUEST().getURLCOMUNICACION().getURLVUELTA());
//		System.out.println("URL Notificación: " + xmlBody.getREQUEST().getURLCOMUNICACION().getURLNOTIFICACION());
//
//		System.out.println("Titular de la Cuenta: " + xmlBody.getREQUEST().getLOTE().getCARGO().getTITULARCUENTA());
//		System.out.println("Importe: " + xmlBody.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getIMPORTEINGRESO());
//		System.out.println("MACODE: " + xmlBody.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getMACODE());

		return xmlBody;
	}

	private String generaMACCCT(XmlBody xmlBody) {
		StringBuilder CADENA_ORIGEN = new StringBuilder()
				.append(xmlBody.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getIDUNICO())
				.append(String.format("%013d",
						Integer.parseInt(
								xmlBody.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getIMPORTEINGRESO())))
				.append(xmlBody.getREQUEST().getCABECERA().getFECHA())
				.append(xmlBody.getREQUEST().getLOTE().getCARGO().getCUENTACARGO().getENTIDAD());

		ArrayList<String> grupos = new ArrayList<String>(7);

		// Generamos los grupos de bytes
		for (int grupo = 0; grupo < 7; grupo++) {
			int inicio = grupo * 8;
			if (grupo == 6) {
				String cadenaEntrada = CADENA_ORIGEN.substring(inicio).trim();
				grupos.add(grupo, String.format("%1$-8s", cadenaEntrada).replace(" ", "0"));
			} else {
				String cadenaEntrada = CADENA_ORIGEN.substring(inicio, inicio + 8).trim();
				grupos.add(grupo, cadenaEntrada);
			}
		}

		// TODO: Tratar los grupos de bytes

		return "1F54393D7E5F4527";
	}


	private byte[] verificaMACODE(String MAC) throws Exception {

		return decodeDES_CBC(settings.getMacKey().getBytes(), MAC.getBytes());
	}

	private byte[] encodeDES_CBC(byte[] key, byte[] data) {
		try {
			// create a binary key from the argument key
			SecureRandom sr = new SecureRandom(key);
			KeyGenerator kg = KeyGenerator.getInstance(settings.getMacAlgorithm());
			kg.init(sr);
			SecretKey sk = kg.generateKey();

			// create an instance of cipher
			Cipher cipher = Cipher.getInstance(settings.getMacEncodeTransformation());

			// generate an initialization vector (IV)
			SecureRandom secureRandom = new SecureRandom();
			byte[] ivspec = new byte[cipher.getBlockSize()];
			secureRandom.nextBytes(ivspec);
			iv = new IvParameterSpec(ivspec);

			cipher.init(Cipher.ENCRYPT_MODE, sk, iv);

			byte[] bout = cipher.doFinal(data);

			return bout;

		} catch (Exception e) {
			System.out.println("Error en generación de MAC" + e);
		}

		return null;

	}

	private byte[] decodeDES_CBC(byte[] key, byte[] data) throws Exception {
		// create a binary key from the argument key (seed)
		SecureRandom sr = new SecureRandom(key);
		KeyGenerator kg = KeyGenerator.getInstance(settings.getMacAlgorithm());
		kg.init(sr);
		SecretKey sk = kg.generateKey();

		// do the decryption with that key
		Cipher cipher = Cipher.getInstance(settings.getMacDecodeTransformation());

		cipher.init(Cipher.DECRYPT_MODE, sk, iv);

		byte[] decrypted = cipher.doFinal(data);

		return decrypted;
	}

	private XmlBody generaXmlReply(XmlBody xmlBody) {

		// TODO: Comprobar el MACODE de la REQUEST
		// TODO: Generar las llamadas para rellenar el REPLY
		// TODO: Generar el Token del REPLY
		return generaMockRespuesta(xmlBody);
	}

	private XmlBody generaMockRespuesta(XmlBody xmlBody) {
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setIDUNICO("1234567890");
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setFECHAING("20180504");
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setENTIDADING("0049");
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setIMPORTEING("000000045654");
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setMACCCT(generaMACCCT(xmlBody));

		return xmlBody;
	}
}
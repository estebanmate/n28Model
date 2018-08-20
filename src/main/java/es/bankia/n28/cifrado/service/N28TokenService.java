package es.bankia.n28.cifrado.service;

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

import es.bankia.n28.cifrado.beans.XmlBody;
import es.bankia.n28.cifrado.model.N28ModelSettings;

import java.util.Base64;

@Service
public class N28TokenService {

	@Autowired
	public N28ModelSettings settings;

	@Autowired
	public N28MacService n28MacService;

	private static byte[] keyiv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public String get_Token(String args) {

		try {
			byte[] encoding = Base64.getEncoder().encode(args.getBytes(settings.getTokenCharcode()));

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

			XmlBody tokenRequestXmlBody = generaTOKEN_REQUEST_XML(new String(decode1));

			// Generamos los datos mockeados de la respuesta
			String MACCCT = n28MacService.get_CCTMAC(tokenRequestXmlBody);

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
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setMACCCT(n28MacService.get_CCTMAC(xmlBody));

		return xmlBody;
	}
}
package es.bankia.n28.service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.bankia.n28.beans.XmlBody;
import es.bankia.n28.model.N28TokenSettings;
import es.bankia.n28.constants.N28Constants;

import java.util.ArrayList;
import java.util.Base64;

@Service
public class N28TokenService {

	@Autowired
	public N28TokenSettings settings;

	private static byte[] keyiv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public String encode3DES(String args) {

		byte[] encoding;

		try {
			encoding = Base64.getEncoder().encode(args.getBytes(settings.getTokenCharcode()));

			byte[] str5 = des3EncodeCBC(settings.getTokenKey().getBytes(), keyiv, encoding);

			byte[] encoding1 = Base64.getEncoder().encode(str5);

			System.out.println("Texto a encriptar ==>  " + args);

			System.out.println("Token texto encriptado ==> " + new String(encoding1));

			return new String(encoding1);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String decode3DES(String args) {
		try {

			System.out.println("Token a desencriptar ==> " + new String(args.getBytes(settings.getTokenCharcode())));

			byte[] decode = Base64.getDecoder().decode(args.getBytes(settings.getTokenCharcode()));

			byte[] str6 = des3DecodeCBC(settings.getTokenKey().getBytes(), keyiv, decode);

			String data = new String(str6);

			byte[] decode1 = Base64.getDecoder().decode(data.trim().getBytes(settings.getTokenCharcode()));

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

	private byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) {
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

	private void generaXML(String cadenaXML) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(XmlBody.class);

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		XmlBody xmlBody = (XmlBody) unmarshaller.unmarshal(new ByteArrayInputStream(cadenaXML.getBytes()));

		System.out.println("ID Comunicación: " + xmlBody.getREQUEST().getCABECERA().getIDCOMUNICACION());
		System.out.println("APLICACION/EMISOR: " + xmlBody.getREQUEST().getCABECERA().getAPLICACION() + "/"
				+ xmlBody.getREQUEST().getCABECERA().getEMISOR());

		System.out.println("URL vuelta: " + xmlBody.getREQUEST().getURLCOMUNICACION().getURLVUELTA());
		System.out.println("URL Notificación: " + xmlBody.getREQUEST().getURLCOMUNICACION().getURLNOTIFICACION());

		System.out.println("Titular de la Cuenta: " + xmlBody.getREQUEST().getLOTE().getCARGO().getTITULARCUENTA());
		System.out.println("Importe: " + xmlBody.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getIMPORTEINGRESO());
		System.out.println("MACODE: " + xmlBody.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getMACODE());

		// Generamos los datos mockeados de la respuesta
		String MACCCT = generarMACCCT(xmlBody);
//		XmlBody xmlReply = generaXmlReply(xmlBody);
//
//		System.out.println("MAC_CCT: " + xmlReply.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).getMACCCT());

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
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setMACCCT(generarMACCCT(xmlBody));

		return xmlBody;
	}

	private String generarMACCCT(XmlBody xmlBody) {
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

		// Tratamos los grupos de bytes

		return "1F54393D7E5F4527";
	}

	private byte[] desEncodeCBC(byte[] key, byte[] data) {
		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(settings.getMaccctAlgorithm());
			deskey = keyfactory.generateSecret(spec);

			Cipher cifrador = Cipher.getInstance(settings.getMacctEncodeTransformation());

			cifrador.init(Cipher.ENCRYPT_MODE, deskey);

			byte[] bout = cifrador.doFinal(data);

			return bout;

		} catch (Exception e) {
			System.out.println("Error en generación de MACCCT" + e);
		}

		return null;

	}
}
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

import es.bankia.n28.beans.XmlBody;
import es.bankia.n28.model.N28TokenSettings;
import es.bankia.n28.constants.N28Constants;

import java.util.Base64;

@Service
public class N28TokenService {

	@Autowired
	public N28TokenSettings settings;

	private static byte[] keyiv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

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

			String data = new String(str6);

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
		XmlBody xmlReply = generaXmlReply(xmlBody);

		System.out.println("MAC_CCT: " + xmlReply.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).getMACCCT());

	}

	private XmlBody generaXmlReply(XmlBody xmlBody) {

		XmlBody xmlReply = generaMockRespuesta(xmlBody);

		if (!N28Constants.N28_RESULTADO_KO_88
				.equals(xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).getRESULTADO())) {
			StringBuilder MAC_CCT = new StringBuilder().append(xmlReply.getREPLY().getCABECERA().getIDCOMUNICACION())
					.append(xmlReply.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).getIMPORTEING())
					.append(xmlReply.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).getFECHAING())
					.append(xmlReply.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).getENTIDADING())
					.append(xmlReply.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getMACODE());
			xmlReply.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setMACCCT(MAC_CCT.toString());
		} else
			xmlReply.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0)
					.setMACCCT(N28Constants.N28_RESULTADO_KO_88_MSG);

		return xmlReply;
	}

	private XmlBody generaMockRespuesta(XmlBody xmlBody) {
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setIDUNICO("1234567890");
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setFECHAING("20180504");
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setENTIDADING("0049");
		xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setIMPORTEING("000000045654");
		if (generarMACODE(xmlBody).equals(xmlBody.getREQUEST().getLOTE().getDETALLEINGRESO().get(0).getMACODE())) {
			xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0)
					.setVALIDACIONCCT(N28Constants.N28_VALIDACION_CCT_OK);
			xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0).setRESULTADO(N28Constants.N28_RESULTADO_OK);
		} else {
			xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0)
					.setVALIDACIONCCT(N28Constants.N28_VALIDACION_CCT_KO);
			xmlBody.getREPLY().getRESPUESTALOTE().getDETALLECARGO().get(0)
					.setRESULTADO(N28Constants.N28_RESULTADO_KO_88);
		}
		return xmlBody;
	}

	private String generarMACODE(XmlBody xmlBody) {
		// TODO generación del MACODE con algoritmo DES
		return "1F54393D7E5F4527";
	}
}
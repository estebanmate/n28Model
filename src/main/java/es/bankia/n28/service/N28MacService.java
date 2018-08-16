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
public class N28MacService {

	@Autowired
	public N28TokenSettings settings;

	public String get_MAC(N28MACODE n28Macode) {

		try {

			System.out.println("Texto a encriptar ==>  " + n28Macode.toString());

			byte[] encoding = Base64.getEncoder().encode(n28Macode.toString().getBytes(settings.getMacCharcode()));

			byte[] macodeStr = encodeDES_CBC(settings.getMacKey().getBytes(), encoding);

			byte[] mac = Base64.getEncoder().encode(macodeStr);

			System.out.println("MAC ==> " + new String(mac));

			return new String(mac);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public String validate_MAC(String args) {
		try {

			System.out.println("MAC a desencriptar ==> " + new String(args.getBytes(settings.getMacCharcode())));

			byte[] decode = Base64.getDecoder().decode(args.getBytes(settings.getMacCharcode()));

			byte[] str6 = decodeDES_CBC(settings.getMacKey().getBytes(), decode);

			String data = new String(str6);

			byte[] decode1 = Base64.getDecoder().decode(data.trim().getBytes(settings.getMacCharcode()));

			System.out.println("Texto desencriptado ==>  " + new String(decode1));

			return new String(decode1);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public String get_CCTMAC(XmlBody xmlBody) {
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
			byte[] ivspec = new byte[cipher.getBlockSize()];
			sr.nextBytes(ivspec);
			IvParameterSpec iv = new IvParameterSpec(ivspec);

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

		// generate an initialization vector (IV)
		byte[] ivspec = new byte[cipher.getBlockSize()];
		sr.nextBytes(ivspec);
		IvParameterSpec iv = new IvParameterSpec(ivspec);

		cipher.init(Cipher.DECRYPT_MODE, sk, iv);

		byte[] decrypted = cipher.doFinal(data);

		return decrypted;
	}

}
package es.bankia.n28.cifrado.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.bankia.n28.cifrado.beans.XmlBody;
import es.bankia.n28.cifrado.model.N28CCTMACODE;
import es.bankia.n28.cifrado.model.N28MACODE;
import es.bankia.n28.cifrado.model.N28ModelSettings;

import java.util.ArrayList;
import java.util.Base64;

@Service
public class N28MacService {

	@Autowired
	public N28ModelSettings settings;

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

	public String get_ByteMAC(String args) {

		try {

			//System.out.println("Byte a encriptar ==>  " + args);

			byte[] encoding = Base64.getEncoder().encode(args.getBytes(settings.getMacCharcode()));

			byte[] macodeStr = encodeDES_CBC(settings.getMacKey().getBytes(), encoding);

			byte[] mac = Base64.getEncoder().encode(macodeStr);

			//System.out.println("MAC Byte ==> " + new String(mac));

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

	public String get_CCTMAC(N28CCTMACODE n28CCTMacode) {
		StringBuilder CADENA_ORIGEN = new StringBuilder().append(n28CCTMacode.getN28())
				.append(String.format("%013d", Integer.parseInt(n28CCTMacode.getImporteIngreso())))
				.append(n28CCTMacode.getFechaIngreso()).append(n28CCTMacode.getEntidad());

		ArrayList<byte[]> grupos = new ArrayList<byte[]>(7);

		// Generamos los grupos de bytes
		for (int grupo = 0; grupo < 7; grupo++) {
			int inicio = grupo * 8;
			if (grupo == 6) {
				String cadenaEntrada = CADENA_ORIGEN.substring(inicio).trim();
				grupos.add(grupo, String.format("%1$-8s", cadenaEntrada).replace(" ", "0").getBytes());
			} else {
				String cadenaEntrada = CADENA_ORIGEN.substring(inicio, inicio + 8).trim();
				grupos.add(grupo, cadenaEntrada.getBytes());
			}
		}

		// Tratamos los grupos de bytes con el XOR
		byte[] E1 = grupos.get(0);
		byte[] S1 = get_ByteMAC(new String(E1)).getBytes();
		byte[] S1XORD2 = xor(S1, grupos.get(1));

		byte[] E2 = S1XORD2;
		byte[] S2 = get_ByteMAC(new String(E2)).getBytes();
		byte[] S2XORD3 = xor(S2, grupos.get(2));

		byte[] E3 = S2XORD3;
		byte[] S3 = get_ByteMAC(new String(E3)).getBytes();
		byte[] S3XORD4 = xor(S3, grupos.get(3));

		byte[] E4 = S3XORD4;
		byte[] S4 = get_ByteMAC(new String(E4)).getBytes();
		byte[] S4XORD5 = xor(S4, grupos.get(4));

		byte[] E5 = S4XORD5;
		byte[] S5 = get_ByteMAC(new String(E5)).getBytes();
		byte[] S5XORD6 = xor(S5, grupos.get(5));

		byte[] E6 = S5XORD6;
		byte[] S6 = get_ByteMAC(new String(E6)).getBytes();
		byte[] S6XORD7 = xor(S6, grupos.get(6));

		byte[] E7 = S6XORD7;
		String CCTMACODE = get_ByteMAC(new String(E7));

		return CCTMACODE;
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

	private static byte[] xor(final byte[] input, final byte[] secret) {
		final byte[] output = new byte[input.length];
		if (secret.length == 0) {
			throw new IllegalArgumentException("empty security key");
		}
		int spos = 0;
		for (int pos = 0; pos < input.length; ++pos) {
			output[pos] = (byte) (input[pos] ^ secret[spos]);
			++spos;
			if (spos >= secret.length) {
				spos = 0;
			}
		}
		return output;
	}

}
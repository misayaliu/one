package jieyi.app.util;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 结合所有的经验，随机生成密钥
 *
 * @author hsxqwfxc
 */
public class RSAUtil {

    public static RSAPrivateKey privateKey;
    public static RSAPublicKey publicKey;

    //创建密钥：这个方法需要对象调用，构造函数调用无用。
    public void generateKeyPair() throws Exception {
        //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
        //初始化密钥对生成器，密钥大小为1024位
        keyPairGen.initialize(1024, new SecureRandom());
        //生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //得到私钥
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //得到公钥
        publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    public static byte[] encrypt(RSAPublicKey publicKey, byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (publicKey != null) {
            //Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            //根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (privateKey != null) {
            //Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            //根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    /**
	 * rsa解密页面传过来的密码
	 * @param rsapassword
	 * @return
	 */
	public static String getDecryptPassword(String rsapassword) {
		String newpass ="";
		try {
			System.out.println(rsapassword);
			byte[] en_result = HexToBytes.HexString2Bytes(rsapassword);
			byte[] de_result = RSAUtil.decrypt(RSAUtil.privateKey, en_result);
			StringBuilder sb = new StringBuilder();
			sb.append(new String(de_result));
			String str = sb.reverse().toString();
			newpass=str;
		} catch (Exception e){
			e.printStackTrace();
		}
		return newpass;
	}
	
}

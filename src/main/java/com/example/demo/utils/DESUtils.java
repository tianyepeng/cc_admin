package com.example.demo.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtils {

    /**
     * 加密  
     * 
     * @author yepeng @param  data : json 格式 @param  key : 密钥 @param  @return
     * @exception  
     */
    public static String encrypt(String data, String key) {
        return doWork(data, key, 0);
    }

    /**
     * 解密  
     * 
     * @author yepeng @param  data : json 格式 @param  key : 密钥
     * @exception  
     */
    public static String decode(String data, String key) {
        return doWork(data, key, 1);
    }

    /**
     * 加密解密接口
     * 
     * @param data
     *            数据
     * @param password
     *            加密解密密码 必须8位字节
     * @param flag
     *            加密解密标志 0：加密 ，1：解密
     * @return
     */
    private static String doWork(String data, String key, int flag) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            //
            if (flag == 0) {
                BASE64Encoder base64encoder = new BASE64Encoder();
                cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
                return base64encoder.encode(cipher.doFinal(data.getBytes("UTF-8")));
            } else {
                BASE64Decoder base64decoder = new BASE64Decoder();
                byte[] encodeByte = base64decoder.decodeBuffer(data);
                cipher.init(Cipher.DECRYPT_MODE, securekey, random);
                byte[] decoder = cipher.doFinal(encodeByte);
                return new String(decoder, "UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * test
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 明文
            String str = "{\"user_name\": \"tianyp588\",\"inner_id\": \"145877\",\"real_name\": \"admin\", \"mobile_phone\": \"17710253698\", \"dept_code\": \"\"}";
            String password = "+HQNBemL8QlyhV4YkU67VA==";
            // 密码
            String desc = DESUtils.encrypt(str, password);
            System.out.println("密文：" + desc);
            // 解密
            str = DESUtils.decode(
                            "mN4LVjOjdnXPTTqLt5kRz9knPDzrahN0KCzDnFhQyVTqz3HxRVjc50DxMpUNKFsUcKEsxvump1Nbdcz5xgDjEaOirts5CVlOBuMGU77HOdmEY7lbXVilQkh5jvDqASBd74Oxoy6aTSIsrqy+hUM9jA==",
                            password);
            System.out.println("明文：" + str);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
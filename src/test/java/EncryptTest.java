import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;

import java.security.Key;

/**
 * @author w_huangruixian
 * @date 2018/7/13 15:13
 **/
public class EncryptTest {

    @Test
    public void testBase64Shiro() {
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testBase64Java() {
        String str = "hello";
        String base64Encoded = Hex.encodeToString(str.getBytes());
        String str2 = new String(Hex.decode(base64Encoded));
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testMd5AndSalt() {
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt, 2).toString();//还可以转换为 toBase64()/toHex()
        System.out.println(md5);
    }

    @Test
    public void testSHA1() {
        String str = "hello";
        String salt = "123";
//内部使用MessageDigest
        String s = new SimpleHash("SHA-1", str, salt).toString();
        System.out.println(s);
    }


    @Test
    public void testHashService() {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成Hash值的迭代次数

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
    }


    @Test
    public void testSecureRandomNumberGenerator() {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
//        randomNumberGenerator.setSeed("123".getBytes());
        String hex = randomNumberGenerator.nextBytes().toHex();
        System.out.println(hex);
    }

    @Test
    public void testAes() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);

        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        //加密
        ByteSource byteSource = aesCipherService.encrypt(text.getBytes(), key.getEncoded());
        String encrptText = byteSource.toHex();
        System.out.println(encrptText);
        String text2 = new String(aesCipherService.decrypt(byteSource.getBytes(), key.getEncoded()).getBytes());
//        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        Assert.assertEquals(text, text2);
    }

    @Test
    public void test2Md5And2Salt(){
        String algorithmName = "md5";
        String username = "liu";
        String password = "123";
        String salt1 = username;
//        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        String salt2 = "24520ee264eab73ec09451d0e9ea6aac";
        int hashIterations = 2;

        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);
    }

}

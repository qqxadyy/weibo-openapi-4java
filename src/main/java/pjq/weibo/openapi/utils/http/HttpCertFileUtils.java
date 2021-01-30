package pjq.weibo.openapi.utils.http;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

import javax.net.ssl.KeyManagerFactory;

import lombok.extern.slf4j.Slf4j;
import pjq.weibo.openapi.utils.DateTimeUtils;

@Slf4j
public class HttpCertFileUtils {
    private HttpCertFileUtils() {}

    public static final String KEY_STORE_TYPE_PKCS12 = "PKCS12";
    public static final String KEY_FACTORY_ALGORITHM_SUNX509 = "SunX509";

    public static KeyManagerFactory getKeyManagerFactory(String cafilepath, String capwd, String keyStoreType,
        String keyManagerFactoryAlgorithm) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        try (FileInputStream instream = new FileInputStream(new File(cafilepath))) {
            // 加载本地的证书进行https加密传输
            keyStore.load(instream, capwd.toCharArray()); // 设置证书密码
            KeyManagerFactory km = KeyManagerFactory.getInstance(keyManagerFactoryAlgorithm);
            km.init(keyStore, capwd.toCharArray());
            return km;
        }
    }

    public static KeyStore getPKCS12KeyStore(String cafilepath, String capwd) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_PKCS12);
        try (FileInputStream instream = new FileInputStream(new File(cafilepath))) {
            // 加载本地的证书进行https加密传输
            keyStore.load(instream, capwd.toCharArray()); // 设置证书密码
            return keyStore;
        }
    }

    /**
     * -获取PKCS12(.pfx文件)证书的alias
     * 
     * @param cafilepath
     * @param capwd
     * @return
     * @throws Exception
     */
    public static String getPKCS12Alias(String cafilepath, String capwd) throws Exception {
        return getPKCS12Alias(getPKCS12KeyStore(cafilepath, capwd));
    }

    /**
     * -获取PKCS12(.pfx文件)证书的alias
     * 
     * @param keyStore
     * @return
     * @throws Exception
     */
    public static String getPKCS12Alias(KeyStore keyStore) throws Exception {
        String alias = null;
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            alias = aliases.nextElement();
        }
        return alias;
    }

    /**
     * -获取PKCS12(.pfx文件)证书信息的对象
     * 
     * @param cafilepath
     * @param capwd
     * @return
     * @throws Exception
     */
    public static X509Certificate getCAWithPKCS12(String cafilepath, String capwd) throws Exception {
        KeyStore keyStore = getPKCS12KeyStore(cafilepath, capwd);
        String alias = getPKCS12Alias(keyStore);
        return getCAWithPKCS12(keyStore, alias);
    }

    /**
     * -获取PKCS12(.pfx文件)证书信息的对象
     * 
     * @param keyStore
     * @param alias
     * @return
     * @throws Exception
     */
    public static X509Certificate getCAWithPKCS12(KeyStore keyStore, String alias) throws Exception {
        X509Certificate cert = (X509Certificate)keyStore.getCertificate(alias);
        Date validDate = cert.getNotAfter();
        if (null != validDate && DateTimeUtils.currentDateObj().after(validDate)) {
            log.warn("warning:ther cert is out of date");
        }
        return cert;
    }

    /**
     * -获取PKCS12(.pfx文件)中的私钥信息
     * 
     * @param cafilepath
     * @param capwd
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String cafilepath, String capwd) throws Exception {
        KeyStore keyStore = getPKCS12KeyStore(cafilepath, capwd);
        String alias = getPKCS12Alias(keyStore);
        return (PrivateKey)keyStore.getKey(alias, capwd.toCharArray());
    }

    /**
     * -获取PKCS12(.pfx文件)中的公钥信息
     * 
     * @param cafilepath
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String cafilepath, String capwd) throws Exception {
        return getCAWithPKCS12(cafilepath, capwd).getPublicKey();
    }

    /**
     * -获取公钥cer文件中的公钥信息
     * 
     * @param cafilepath
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String cafilepath) throws Exception {
        try (FileInputStream instream = new FileInputStream(cafilepath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(instream);
            PublicKey publicKey = cert.getPublicKey();
            return publicKey;
        }
    }
}
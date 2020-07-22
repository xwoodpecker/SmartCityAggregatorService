package htw.smartcity.aggregator.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collections;

public class Utils {

    public static String getMacAddress() {

        String result = "";
        try {
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                byte[] hardwareAddress = ni.getHardwareAddress();

                if (hardwareAddress != null) {
                    for (int i = 0; i < hardwareAddress.length; i++)
                        result += String.format((i == 0 ? "" : "-") + "%02X", hardwareAddress[i]);

                    return result;
                }
            }

        } catch (SocketException e) {
            System.out.println("Error retrieving MAC Address. Exiting Application ");
            System.exit(1);
        }
        return result;
    }

    public static SSLSocketFactory getSocketFactoryForCaCertificate(final String caCertificate)
            throws Exception {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream is = new FileInputStream(caCertificate);
        InputStream caInput = new BufferedInputStream(is);
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
        } finally {
            caInput.close();
        }

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("caCertificate", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        return context.getSocketFactory();
    }
}

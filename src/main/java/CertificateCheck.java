import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CertificateCheck {

    String httpsUrl = "https://www.google.com";
    URL url;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z");
    int certificateNumber;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public HostResult  testIt(String subdomain){
        HostResult result = new HostResult();
        try {
            System.out.println("\n\nPulling the folling URL's certifications: '" + ANSI_GREEN + subdomain.trim() + ANSI_RESET + "'.");

            url = new URL("https://www." + subdomain.trim());

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.connect();

            result.setData("\n\nPulling the folling URL's certifications: '" + subdomain.trim() + "'.");

            printHttpsCertification(connection, result);

            connection.disconnect();

        } catch (Exception e) {
            result = new HostResult();
            result.setHostKnown(false);
            result.setDateAttempted(new Date());
            result.setData("This domain is NO LONGER REACHABLE despite being listed by Sublist3r: " + url.toString());
            return result;
        }

        return result;
    }

    private HostResult printHttpsCertification(HttpsURLConnection connection, HostResult result) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (null != connection){

            result.setHostKnown(true);
            result.setDateAttempted(new Date());



            sb.append("\rResponse Code: " + connection.getResponseCode());
            sb.append("\rCipher Suite: " + connection.getCipherSuite());

            setCertificateNumber(0);
            System.out.println("\n\n------------------------------------------------\n");
            System.out.println("Response Code: " + connection.getResponseCode());
            System.out.println("Cipher Suite: " + connection.getCipherSuite());
            System.out.println("\n------------------------------------------------\n\n");


            List<Certificate> certificates = Arrays.asList(connection.getServerCertificates());
            certificates.stream().forEach(certificate -> {
                setCertificateNumber(getCertificateNumber() + 1);
                System.out.println("Certificate Number: " + getCertificateNumber() + " of " + certificates.size());
                sb.append("\rCertificate Number: " + getCertificateNumber() + " of " + certificates.size());
                System.out.println();
                System.out.println("Cert Type: " + certificate.getType());
                sb.append("\rCert Type: " + certificate.getType());
                System.out.println();
                System.out.println("Cert Public Key: " + certificate.getPublicKey());
                sb.append("\rCert Public Key: " + certificate.getPublicKey());
                System.out.println();
                if(certificate instanceof X509Certificate){
                    try {
                        ((X509Certificate) certificate).checkValidity();
                        System.out.println();
                        System.out.println("Algorithm: " + ((X509Certificate) certificate).getSigAlgName());
                        sb.append("\rAlgorithm: " + ((X509Certificate) certificate).getSigAlgName());
                        System.out.println();
                        System.out.println("Certificate is" + ANSI_GREEN + " ACTIVE " + ANSI_RESET+ "for current date.");
                        sb.append("\rCertificate is ACTIVE for current date.");
                        System.out.println("Not Before: " + simpleDateFormat.format(((X509Certificate) certificate).getNotBefore()));
                        sb.append("\rNot Before: " + simpleDateFormat.format(((X509Certificate) certificate).getNotBefore()));
                        System.out.println("Not After: " + simpleDateFormat.format(((X509Certificate) certificate).getNotAfter()));
                        sb.append("\rNot After: " + simpleDateFormat.format(((X509Certificate) certificate).getNotAfter()));
                    } catch (CertificateExpiredException e) {
                        System.out.println("Certificate is" + ANSI_RED + " NOT ACTIVE " + ANSI_RESET+ " for current date!");
                        sb.append("\rCertificate is NOT ACTIVE for current date!");
                        result.setData(result.getData() + sb);
                    } catch (CertificateNotYetValidException e) {
                        System.out.println("Certificate is not yet valid");
                        sb.append("\rCertificate is not yet valid");
                        result.setData(result.getData() + sb);
                    }
                }

                System.out.println("\n\n------------------------------------------------\n\n");
                sb.append("\n\n------------------------------------------------\n\n");
            });
        }

        result.setData(result.getData() + sb);
        return result;
    }

    private int getCertificateNumber(){
        return this.certificateNumber;
    }

    private void setCertificateNumber(int i){
        this.certificateNumber = i;
    }

}

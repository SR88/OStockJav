import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        PythonLauncher launcher = new PythonLauncher();
        CertificateCheck cc = new CertificateCheck();
        DocumentWriter documentWriter = new DocumentWriter();


        List<String> subDomains = launcher.runScript("overstock.com");

        List<HostResult> hostResultList = new ArrayList<>();
        if (subDomains != null) {
            for (String s: subDomains) {
                HostResult result = cc.testIt(s);
                hostResultList.add(result);
            }
        }

        List<HostResult> hostResultsKnown = hostResultList.stream().filter(HostResult::isHostKnown).collect(Collectors.toList());
        List<HostResult> hostResultsUnknown = hostResultList.stream().filter(Predicate.not(HostResult::isHostKnown)).collect(Collectors.toList());

        List<String> provenDomains = new ArrayList<>();
        provenDomains.add("overstock.com");
        provenDomains.add("google.com");
        provenDomains.add("ebay.com");

        for (String s : provenDomains) {
            hostResultsKnown.add(cc.testIt(s));
        }

        documentWriter.createUnknownHostFile(hostResultsUnknown);
        documentWriter.createKnownHostFile(hostResultsKnown);



    }

}

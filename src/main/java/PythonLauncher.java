import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PythonLauncher {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public List<String> runScript(String url) {
        String eline = "";
        String stringSublister = "";

        ProcessBuilder pb = new ProcessBuilder("/Library/Frameworks/Python.framework/Versions/3.9/bin/python3", getPythonScript().getFile(), "-d", url);
        try {
            Process process = pb.start();

            System.out.println(ANSI_CYAN + "====== STARTING SUBLIST3R ======" + ANSI_RESET);

            stringSublister = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().collect(Collectors.joining("\n"));

            BufferedReader ereader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((eline = ereader.readLine()) != null) {
                System.out.println(eline);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(stringSublister);
        if (stringSublister.length() > 0){
            int indexOf = stringSublister.indexOf("www." + url);
            int lengthOfUrl = 4 + url.length();
            int startOfSubdomains = indexOf + lengthOfUrl;
            String subDomains = stringSublister.substring(startOfSubdomains);
            String replacePrefix = subDomains.replace("[92m", "");
            String replaceSuffix = replacePrefix.replace("[0m", "");
            List<String> subdomainList = new LinkedList(Arrays.asList(replaceSuffix.split("\n")));
            subdomainList.remove(0);
            subdomainList.stream().forEach(s -> s.trim());
            return subdomainList;
        }

        return null;
    }

        public URL getPythonScript(){
        return getClass().getResource("/Sublist3r/sublist3r.py");
    }

}

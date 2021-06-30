import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DocumentWriter {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z");

    public void createUnknownHostFile(List<HostResult> unknownHosts){
        try {
            FileWriter writer = new FileWriter("UnknownHosts.txt");
            writer.write("The following hosts were not reachable and may not exist in public DNS tables:\r\r");
            unknownHosts.stream().forEach(unknownHost -> {
                try {
                    writer.write("\r\r");
                    writer.write(simpleDateFormat.format(unknownHost.getDateAttempted()) + "\r");
                    writer.write(unknownHost.getData());
                } catch (Exception e){
                }
            });
            writer.write("\r\rEnd of Records");
            writer.close();

        } catch (IOException e) {
            System.out.println("Error occured in writing Unknown Host Document." + e.getMessage());
        }

    }

    public void createKnownHostFile(List<HostResult> knownHosts){
        try {
            FileWriter writer = new FileWriter("KnownHostsCertificates.txt");
            writer.write("The following hosts are reachable and their certificates are as follows:\r\r");
            knownHosts.stream().forEach(hosts -> {
                try {
                    writer.write("\r\r");
                    writer.write(simpleDateFormat.format(hosts.getDateAttempted()) + "\r");
                    writer.write(hosts.getData());
                } catch (Exception e){
                }
            });
            writer.write("\r\rEnd of Records");
            writer.close();

        } catch (IOException e) {
            System.out.println("Error occured in writing Known Host Document." + e.getMessage());
        }

    }





}

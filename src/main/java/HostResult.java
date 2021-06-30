import java.util.Date;

public class HostResult {

    private Date dateAttempted;
    private boolean isHostKnown;
    private String data;

    public HostResult() {
    }

    public Date getDateAttempted() {
        return dateAttempted;
    }

    public void setDateAttempted(Date dateAttempted) {
        this.dateAttempted = dateAttempted;
    }

    public boolean isHostKnown() {
        return isHostKnown;
    }

    public void setHostKnown(boolean hostKnown) {
        isHostKnown = hostKnown;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HostResult{" +
                "\ndateAttempted=" + dateAttempted +
                ", \nisHostKnown=" + isHostKnown +
                ", \ndata='" + data + '\'' +
                "\n}";
    }
}

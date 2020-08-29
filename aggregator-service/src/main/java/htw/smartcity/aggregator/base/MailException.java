package htw.smartcity.aggregator.base;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class MailException {
    private LocalDateTime time;
    private LogException exception;
    private List<String> additionalInfos = new ArrayList<>();

    public MailException(LogException exception) {
        this.time = LocalDateTime.now();
        this.exception = exception;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LogException getException() {
        return exception;
    }

    public void setException(LogException exception) {
        this.exception = exception;
    }

    public void addAdditionalInfos(String infos) {
        this.additionalInfos.add(infos);
    }

    @Override
    public String toString(){
        DateTimeFormatter dtf = ofPattern("yyyy/MM/dd HH:mm:ss");
        String datetimeFormatted = dtf.format(time);
        StringBuilder sb = new StringBuilder(datetimeFormatted).append(" - ").append(exception);
        if(additionalInfos.size() > 0){
            sb.append(":\n");
        }
        for(String info : additionalInfos){
            sb.append("\t").append(info).append("\n");
        }
        return sb.toString();
    }
}

package htw.smartcity.aggregator.base;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * The type Mail exception.
 */
public class MailException {
    private LocalDateTime time;
    private LogException exception;
    private List<String> additionalInfos = new ArrayList<>();

    /**
     * Instantiates a new Mail exception.
     *
     * @param exception the exception
     */
    public MailException(LogException exception) {
        this.time = LocalDateTime.now();
        this.exception = exception;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Gets exception.
     *
     * @return the exception
     */
    public LogException getException() {
        return exception;
    }

    /**
     * Sets exception.
     *
     * @param exception the exception
     */
    public void setException(LogException exception) {
        this.exception = exception;
    }

    /**
     * Add additional infos.
     *
     * @param infos the infos
     */
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

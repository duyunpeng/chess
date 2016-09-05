package chess.application.user.command;


/**
 * Created by jm on 16-5-23.
 */
public class FlushLocationCommand {

    private String user;

    private Double longitude;       //经度
    private Double latitude;        //维度

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}

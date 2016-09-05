package chess.core.util.calculation;

/**
 * Created by YJH on 2016/5/23.
 */
public class Degree {

    public Degree(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private Double longitude;       //经度
    private Double latitude;        //维度

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

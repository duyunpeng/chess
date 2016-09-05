package chess.core.util.calculation;

/**
 * 经纬度计算
 * Created by YJH on 2016/5/23.
 */
public class CoreCalculationUtils {

    private static final double EARTH_RADIUS = 6378137;//赤道半径(单位m)

    /**
     * 角度数转换为弧度公式
     *
     * @param d
     * @return
     */
    private static double radians(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 弧度转换为角度数公式
     *
     * @param d
     * @return
     */
    private static double degrees(double d) {
        return d * (180 / Math.PI);
    }

    /**
     * 计算两个经纬度之间的直接距离
     *
     * @param Degree1
     * @param Degree2
     * @return
     */
    public static double GetDistance(Degree Degree1, Degree Degree2) {
        double radLat1 = radians(Degree1.getLatitude());
        double radLat2 = radians(Degree2.getLatitude());
        double a = radLat1 - radLat2;
        double b = radians(Degree1.getLongitude()) - radians(Degree2.getLongitude());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 计算两个经纬度之间的直接距离(google 算法)
     *
     * @param Degree1
     * @param Degree2
     * @return
     */
    public static double GetDistanceGoogle(Degree Degree1, Degree Degree2) {
        double radLat1 = radians(Degree1.getLatitude());
        double radLng1 = radians(Degree1.getLongitude());
        double radLat2 = radians(Degree2.getLatitude());
        double radLng2 = radians(Degree2.getLongitude());
        double s = Math.acos(Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radLng1 - radLng2) + Math.sin(radLat1) * Math.sin(radLat2));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 以一个经纬度为中心计算出四个顶点
     *
     * @param Degree1  需要计算的经纬
     * @param distance 半径(米)
     * @return
     */
    public static Degree[] GetDegreeCoordinates(Degree Degree1, double distance) {
        double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(Degree1.getLatitude()));
        dlng = degrees(dlng);//一定转换成角度数
        double dlat = distance / EARTH_RADIUS;
        dlat = degrees(dlat);//一定转换成角度数
        return new Degree[]{new Degree(Degree1.getLongitude() - dlng, Degree1.getLatitude() + dlat),//left-top
                new Degree(Degree1.getLongitude() - dlng, Degree1.getLatitude() - dlat),//left-bottom
                new Degree(Degree1.getLongitude() + dlng, Degree1.getLatitude() + dlat),//right-top
                new Degree(Degree1.getLongitude() + dlng, Degree1.getLatitude() - dlat) //right-bottom
        };
    }


    public static void main(String[] args) {
        double a = CoreCalculationUtils.GetDistance(new Degree(116.412007, 39.947545), new Degree(116.412924, 39.947918));//116.416984,39.944959
        double b = CoreCalculationUtils.GetDistanceGoogle(new Degree(116.412007, 39.947545), new Degree(116.412924, 39.947918));
        Degree[] dd = CoreCalculationUtils.GetDegreeCoordinates(new Degree(116.412007, 39.947545), 2500);
        System.out.println(a + " " + b);
        System.out.println(dd[0].getLatitude() + "," + dd[0].getLongitude());
        System.out.println(dd[3].getLatitude() + "," + dd[3].getLongitude());
    }
}

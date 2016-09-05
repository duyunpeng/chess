package chess.application.user.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.EnableStatus;

import java.util.List;

/**
 * Created by YJH on 2016/4/19.
 */
public class ListUserCommand extends BasicPaginationCommand {

    private String userName;        //用户名

    private EnableStatus status;    //状态

    private String user;            //用户

    private Integer distance;        //距离

    private String area;            //区域

    private Double longitude;       //经度

    private Double latitude;        //维度

    private List<String> contactsUserList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public List<String> getContactsUserList() {
        return contactsUserList;
    }

    public void setContactsUserList(List<String> contactsUserList) {
        this.contactsUserList = contactsUserList;
    }
}

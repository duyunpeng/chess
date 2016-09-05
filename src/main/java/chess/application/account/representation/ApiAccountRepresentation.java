package chess.application.account.representation;

import chess.application.picture.representation.PictureRepresentation;
import chess.core.enums.EnableStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by YJH on 2016/5/18.
 */
public class ApiAccountRepresentation {

    private String id;
    private Integer version;
    private Date createDate;

    private String userName;        //用户名
    private EnableStatus status;     //状态
    private PictureRepresentation headPic;  //头像
    private String email;   //邮箱
    private String areaString;          //地区

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

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

    public PictureRepresentation getHeadPic() {
        return headPic;
    }

    public void setHeadPic(PictureRepresentation headPic) {
        this.headPic = headPic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAreaString() {
        return areaString;
    }

    public void setAreaString(String areaString) {
        this.areaString = areaString;
    }
}

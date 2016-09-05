package chess.application.withdraw.command;

/**
 * Created by pengyi on 2016/5/6.
 */
public class EditWithdrawCommand {

    private String id;
    private Integer version;

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

}

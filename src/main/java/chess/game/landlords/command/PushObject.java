package chess.game.landlords.command;

/**
 * Created by yjh on 16-6-13.
 */
public class PushObject {

    private int type;
    private Object data;
    private String code;

    public PushObject() {
    }

    public PushObject(int type, Object data, String code) {
        this.type = type;
        this.data = data;
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

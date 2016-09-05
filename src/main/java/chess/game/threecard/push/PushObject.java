package chess.game.threecard.push;

/**
 * Author pengyi
 * Create date 16-6-13
 */
public class PushObject {

    private int type;
    private Object data;
    private boolean code;
    private String message;

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

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PushObject() {
    }

    public PushObject(int type, Object data, boolean code, String message) {
        this.type = type;
        this.data = data;
        this.code = code;
        this.message = message;
    }
}

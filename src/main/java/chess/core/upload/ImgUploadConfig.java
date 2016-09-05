package chess.core.upload;

/**
 * 图片上传配置
 * Created by YJH on 2016/4/11.
 */
public class ImgUploadConfig extends FileUploadConfig {

    private int miniThumbnailWidth; //迷你图片
    private int miniThumbnailHeight;

    public int getMiniThumbnailWidth() {
        return miniThumbnailWidth;
    }

    public void setMiniThumbnailWidth(int miniThumbnailWidth) {
        this.miniThumbnailWidth = miniThumbnailWidth;
    }

    public int getMiniThumbnailHeight() {
        return miniThumbnailHeight;
    }

    public void setMiniThumbnailHeight(int miniThumbnailHeight) {
        this.miniThumbnailHeight = miniThumbnailHeight;
    }

}

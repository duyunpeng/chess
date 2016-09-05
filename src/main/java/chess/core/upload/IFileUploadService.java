package chess.core.upload;

import org.springframework.web.multipart.MultipartFile;
import chess.application.picture.command.CreatePictureCommand;

import java.io.File;
import java.io.IOException;

/**
 * Created by YJH on 2016/4/11.
 */
public interface IFileUploadService {
    String getDoMainName();

    UploadResult imgUpload(MultipartFile[] files) throws IOException;

    CreatePictureCommand moveToImg(String picPath);

    String moveToChatImg(String picPath);

    void deleteImg(String picPath);

    boolean deleteFile(File file);

    String getHttpPath(String type);
}

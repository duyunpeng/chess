package chess.domain.service.picture;


import chess.application.picture.command.CreatePictureCommand;
import chess.domain.model.picture.Picture;

/**
 * Created by YJH on 2016/4/12.
 */
public interface IPictureService {
    Picture create(CreatePictureCommand command);

    Picture searchByID(String id);

    void delete(String id);

    Picture searchByDescribes(String describes);
}

package chess.application.bullfightrecord.command;

import chess.domain.model.user.User;

/**
 * Created by dyp on 2016/7/20.
 */
public class CreateBullfightRecordCommand {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package chess.application.message.command;

import chess.core.common.BasicPaginationCommand;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
public class ListMessageCommand extends BasicPaginationCommand {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

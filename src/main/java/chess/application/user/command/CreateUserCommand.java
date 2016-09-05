package chess.application.user.command;

import chess.application.account.command.CreateAccountCommand;

/**
 * Created by YJH on 2016/4/19.
 */
public class CreateUserCommand extends CreateAccountCommand {

    private String name;                //网名
    private String area;                  //地区

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

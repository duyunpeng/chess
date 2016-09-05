package chess.application.user.command;

import chess.application.account.command.EditAccountCommand;

/**
 * Created by YJH on 2016/4/19.
 */
public class EditUserCommand extends EditAccountCommand {

    private String name;                //网名
    private String area;                  //地区
    private String headPic;             //头像
    private String jobDescription;      //工作介绍

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

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}

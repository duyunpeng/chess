package chess.application.permission.command;

import org.hibernate.validator.constraints.NotBlank;
import chess.application.shared.command.SharedCommand;

/**
 * Created by YJH on 2016/3/30.
 */
public class EditPermissionCommand extends SharedCommand {

    @NotBlank(message = "{permission.name.NotBlank.messages}")
    private String name;            //权限名称
    @NotBlank(message = "{permission.description.NotBlank.messages}")
    private String description;        //权限描述
    @NotBlank(message = "{permission.value.NotBlank.messages}")
    private String value;           //权限默认值

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

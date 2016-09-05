package chess.application.role.command;

import org.hibernate.validator.constraints.NotBlank;
import chess.core.enums.EnableStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public class CreateRoleCommand {

    @NotBlank(message = "{role.name.NotBlank.messages}")
    private String name;                    //角色名称
    @NotBlank(message = "{role.description.NotBlank.messages}")
    private String description;                //角色描述

    private List<String> permissions;   //角色包含的权限集合
    @NotNull(message = "{role.status.NotNull.messages}")
    private EnableStatus status;            //状态
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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }
}

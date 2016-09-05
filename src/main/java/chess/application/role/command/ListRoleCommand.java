package chess.application.role.command;


import chess.core.common.BasicPaginationCommand;
import chess.core.enums.EnableStatus;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public class ListRoleCommand extends BasicPaginationCommand {

    private String name;
    private EnableStatus status;
    private List<String> ids;

    private String roleName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}

package chess.domain.service.procedures;

import chess.application.procedures.command.CreateProceduresCommand;

/**
 * Created by yjh
 * Date : 2016/8/14.
 */
public interface IProceduresService {
    void create(CreateProceduresCommand command);
}

package chess.domain.service.procedures;

import chess.application.procedures.command.CreateProceduresCommand;
import chess.domain.model.procedures.IProceduresRepository;
import chess.domain.model.procedures.Procedures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yjh
 * Date : 2016/8/14.
 */
@Service("proceduresService")
public class ProceduresService implements IProceduresService {

    @Autowired
    private IProceduresRepository<Procedures, String> proceduresRepository;

    @Override
    public void create(CreateProceduresCommand command){
        Procedures procedures = new Procedures(command.getProcedures(),command.getGameType(),command.getUser());
        proceduresRepository.save(procedures);
    }
}

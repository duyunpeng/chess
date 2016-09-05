package chess.domain.service.withhold;

import chess.domain.model.withhold.IWithholdRepository;
import chess.domain.model.withhold.Withhold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yjh on 16-7-9.
 */
@Service("withholdService")
public class WithholdService implements IWithholdService {

    @Autowired
    private IWithholdRepository<Withhold, String> withholdRepository;

}

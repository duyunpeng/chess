package chess.application.withhold;

import chess.core.mapping.IMappingService;
import chess.domain.service.withhold.IWithholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yjh on 16-7-11.
 */
@Service("withholdAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class WithholdAppService implements IWithholdAppService {

    @Autowired
    private IWithholdService withholdService;

    @Autowired
    private IMappingService mappingService;

}

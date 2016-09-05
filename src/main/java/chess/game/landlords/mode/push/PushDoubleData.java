package chess.game.landlords.mode.push;

import chess.game.landlords.mode.Action;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

/**
 *
 * 加倍数据
 * Created by yjh on 16-7-4.
 */
public class PushDoubleData {

    private Action result;

    private BigDecimal multiple;

    public PushDoubleData() {
    }

    public PushDoubleData(Action result, BigDecimal multiple) {
        this.result = result;
        this.multiple = multiple;
    }

    public Action getResult() {
        return result;
    }

    public void setResult(Action result) {
        this.result = result;
    }

    public BigDecimal getMultiple() {
        return multiple;
    }

    public void setMultiple(BigDecimal multiple) {
        this.multiple = multiple;
    }
}

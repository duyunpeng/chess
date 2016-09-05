package chess.game.threecard.push;

import java.math.BigDecimal;

/**
 * Author pengyi
 * Create date 16-6-23
 */
public class ScoreResult {
    private String username;
    private BigDecimal score;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}

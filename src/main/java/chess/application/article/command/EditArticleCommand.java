package chess.application.article.command;

import chess.application.shared.command.SharedCommand;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by YJH on 2016/4/19.
 */
public class EditArticleCommand extends SharedCommand {

    @NotBlank(message = "{article.title.NotBlank.message}")
    private String title;           //文章标题
    @NotBlank(message = "{article.content.NotBlank.message}")
    private String content;         //文章内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

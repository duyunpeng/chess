package chess.domain.service.article;

import chess.application.article.command.CreateArticleCommand;
import chess.application.article.command.EditArticleCommand;
import chess.application.article.command.ListArticleCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.article.Article;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH on 2016/4/19.
 */
public interface IArticleService {
    Pagination<Article> pagination(ListArticleCommand command);

    Article searchByID(String id);

    Article create(CreateArticleCommand command);

    Article edit(EditArticleCommand command);

    void updateStatus(SharedCommand command);

    Pagination<Article> apiArticleList(ListArticleCommand command);
}

package chess.application.article;

import chess.application.article.command.CreateArticleCommand;
import chess.application.article.command.EditArticleCommand;
import chess.application.article.command.ListArticleCommand;
import chess.application.article.representation.ArticleRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH on 2016/4/19.
 */
public interface IArticleAppService {
    Pagination<ArticleRepresentation> pagination(ListArticleCommand command);

    ArticleRepresentation searchByID(String id);

    ArticleRepresentation create(CreateArticleCommand command);

    ArticleRepresentation edit(EditArticleCommand command);

    void updateStatus(SharedCommand command);
}

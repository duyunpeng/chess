package chess.application.article;

import chess.application.article.command.ListArticleCommand;
import chess.core.api.ApiResponse;

/**
 * Created by dyp on 2016/6/12.
 */
public interface IApiArticleAppService {

    ApiResponse articleList(ListArticleCommand command);
}

package chess.application.article;

import chess.application.article.command.ListArticleCommand;
import chess.application.article.representation.ArticleRepresentation;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.mapping.IMappingService;
import chess.domain.model.article.Article;
import chess.domain.service.article.IArticleService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dyp on 2016/6/12.
 */
@Service("apiArticleAppService")
public class ApiArticleAppService implements IApiArticleAppService {

    @Autowired
    private IMappingService mappingService;

    @Autowired
    private IArticleService articleService;

    @Override
    @Transactional(readOnly = true)
    public ApiResponse articleList(ListArticleCommand command) {
        command.verifyPage();
        command.verifyPageSize(15);
        Pagination<Article> pagination = articleService.apiArticleList(command);
        List<ArticleRepresentation> data = mappingService.mapAsList(pagination.getData(), ArticleRepresentation.class);
        return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(),
                new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize()));
    }
}

package chess.application.article;

import chess.application.article.command.CreateArticleCommand;
import chess.application.article.command.EditArticleCommand;
import chess.application.article.command.ListArticleCommand;
import chess.application.article.representation.ArticleRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.core.mapping.IMappingService;
import chess.domain.model.article.Article;
import chess.domain.service.article.IArticleService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by YJH on 2016/4/19.
 */
@Service("articleAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ArticleAppService implements IArticleAppService {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IMappingService mappingService;

    @Override
    @Transactional(readOnly = true)
    public Pagination<ArticleRepresentation> pagination(ListArticleCommand command) {
        command.verifyPage();
        command.verifyPageSize(15);
        Pagination<Article> pagination = articleService.pagination(command);
        List<ArticleRepresentation> date = mappingService.mapAsList(pagination.getData(), ArticleRepresentation.class);
        return new Pagination<ArticleRepresentation>(date, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleRepresentation searchByID(String id) {
        return mappingService.map(articleService.searchByID(id), ArticleRepresentation.class, false);
    }

    @Override
    public ArticleRepresentation create(CreateArticleCommand command) {
        return mappingService.map(articleService.create(command), ArticleRepresentation.class, false);
    }

    @Override
    public ArticleRepresentation edit(EditArticleCommand command) {
        return mappingService.map(articleService.edit(command), ArticleRepresentation.class, false);
    }

    @Override
    public void updateStatus(SharedCommand command) {
        articleService.updateStatus(command);
    }
}

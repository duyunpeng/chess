package chess.application.article.representation.mapping;

import chess.application.account.representation.AccountRepresentation;
import chess.application.article.representation.ArticleRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.article.Article;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YJH on 2016/4/19.
 */
@Component
public class ArticleRepresentationMapper extends CustomMapper<Article, ArticleRepresentation> {

    @Autowired
    private IMappingService mappingService;

    public void mapAtoB(Article article, ArticleRepresentation representation, MappingContext context) {
        if (null != article.getAccount()) {
            AccountRepresentation data = mappingService.map(article.getAccount(), AccountRepresentation.class, false);
            representation.setAccount(data);
        }
    }

}

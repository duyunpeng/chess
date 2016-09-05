package chess.application.article.representation.mapping;

import chess.application.article.representation.ApiArticleRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.article.Article;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YJH on 2016/5/18.
 */
@Component
public class ApiArticleRepresentationMapper extends CustomMapper<Article, ApiArticleRepresentation> {

    @Autowired
    private IMappingService mappingService;

    public void mapAtoB(Article article, ApiArticleRepresentation representation, MappingContext context) {
        if (null != article.getAccount()) {
            representation.setAccount(article.getAccount().getId());
            representation.setUserName(article.getAccount().getUserName());
        }
    }
}

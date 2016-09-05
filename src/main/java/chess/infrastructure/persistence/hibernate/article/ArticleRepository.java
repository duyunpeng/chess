package chess.infrastructure.persistence.hibernate.article;

import chess.domain.model.article.Article;
import chess.domain.model.article.IArticleRepository;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by YJH on 2016/4/15.
 */
@Repository("articleRepository")
public class ArticleRepository extends AbstractHibernateGenericRepository<Article, String>
        implements IArticleRepository<Article, String> {
}

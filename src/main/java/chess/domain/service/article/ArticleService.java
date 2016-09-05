package chess.domain.service.article;

import chess.application.article.command.CreateArticleCommand;
import chess.application.article.command.EditArticleCommand;
import chess.application.article.command.ListArticleCommand;
import chess.application.shared.command.SharedCommand;
import chess.core.enums.EnableStatus;
import chess.core.exception.NoFoundException;
import chess.domain.model.account.Account;
import chess.domain.model.article.Article;
import chess.domain.model.article.IArticleRepository;
import chess.domain.service.account.IAccountService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YJH on 2016/4/19.
 */
@Service("articleService")
public class ArticleService implements IArticleService {

    @Autowired
    private IArticleRepository<Article, String> articleRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public Pagination<Article> pagination(ListArticleCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();

        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.desc("createDate"));
        return articleRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }

    @Override
    public Article searchByID(String id) {
        Article article = articleRepository.getById(id);
        if (null == article) {
            throw new NoFoundException("没有找到ID[" + id + "]的Article数据");
        }
        return article;
    }

    @Override
    public Article create(CreateArticleCommand command) {
        Account account = accountService.searchByID(command.getAccount());
        Article article = new Article(command.getTitle(), account, command.getContent(), command.getStatus());
        articleRepository.save(article);
        return article;
    }

    @Override
    public Article edit(EditArticleCommand command) {

        Article article = this.searchByID(command.getId());
        article.fainWhenConcurrencyViolation(command.getVersion());

        article.changeTitle(command.getTitle());
        article.changeContent(command.getContent());
        articleRepository.update(article);
        return article;
    }

    @Override
    public void updateStatus(SharedCommand command) {
        Article article = this.searchByID(command.getId());
        article.fainWhenConcurrencyViolation(command.getVersion());

        if (article.getStatus() == EnableStatus.DISABLE) {
            article.changeStatus(EnableStatus.ENABLE);
        } else {
            article.changeStatus(EnableStatus.DISABLE);
        }
        articleRepository.update(article);
    }

    @Override
    public Pagination<Article> apiArticleList(ListArticleCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();

        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.desc("createDate"));
        return articleRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }
}

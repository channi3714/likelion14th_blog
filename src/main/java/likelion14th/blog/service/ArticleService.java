package likelion14th.blog.service;

import jakarta.transaction.Transactional;
import likelion14th.blog.domain.Article;
import likelion14th.blog.dto.response.ArticleResponse;
import likelion14th.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse addArticle(String title, String content, String author, String password) {
        Article article = new Article(title, content, author, password);

        articleRepository.save(article);

        return ArticleResponse.from(article);
    }
}

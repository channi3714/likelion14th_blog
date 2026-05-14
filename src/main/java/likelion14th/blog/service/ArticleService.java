package likelion14th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import likelion14th.blog.domain.Article;
import likelion14th.blog.dto.response.ArticleDetailResponse;
import likelion14th.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleDetailResponse addArticle(String title, String content, String author, String password) {
        Article article = new Article(title, content, author, password);

        articleRepository.save(article);

        return ArticleDetailResponse.from(article);
    }

    @Transactional(readOnly = true)
    public ArticleDetailResponse getOneArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        return ArticleDetailResponse.from(article);
    }

    @Transactional
    public ArticleDetailResponse updateArticle(Long id, String title, String content) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        article.update(title, content);

        articleRepository.save(article); // 안해줘도 된다. post는 영속성 컨텍스트에 의해서 한번도 사용한 적이 없기 때문에 해줘야 하지만 patch는 자동으로 해준다.
        return ArticleDetailResponse.from(article);
    }
}

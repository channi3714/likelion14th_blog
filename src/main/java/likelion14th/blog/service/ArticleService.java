package likelion14th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import likelion14th.blog.domain.Article;
import likelion14th.blog.domain.Comment;
import likelion14th.blog.dto.request.CommentRequest;
import likelion14th.blog.dto.response.ArticleDetailResponse;
import likelion14th.blog.dto.response.ArticleSummaryResponse;
import likelion14th.blog.exception.ArticleNotFoundException;
import likelion14th.blog.exception.PermissionNotFoundException;
import likelion14th.blog.repository.ArticleRepository;
import likelion14th.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ArticleDetailResponse addArticle(String title, String content, String author, String password) {
        Article article = new Article(title, content, author, password);

        articleRepository.save(article);

        return ArticleDetailResponse.from(article);
    }

    @Transactional(readOnly = true)
    public ArticleDetailResponse getOneArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        return ArticleDetailResponse.from(article);
    }

    @Transactional
    public ArticleDetailResponse updateArticle(Long id, String title, String content, String password) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        if(!article.getPassword().equals(password)) {
            throw new PermissionNotFoundException("해당 게시글에 대한 수정 권한이 없습니다.");
        }
        article.update(title, content);

        articleRepository.save(article); // 안해줘도 된다. post는 영속성 컨텍스트에 의해서 한번도 사용한 적이 없기 때문에 해줘야 하지만 patch는 자동으로 해준다.
        return ArticleDetailResponse.from(article);
    }

    @Transactional
    public Void deleteArticle(Long id, String password) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("해당 게시글을 조회할 수 없습니다."));

        if (!article.getPassword().equals(password)) {
            throw new PermissionNotFoundException("해당 게시글에 대한 삭제 권한이 없습니다.");
        }

        List<Comment> comments = commentRepository.findByArticle(article);
        comments.forEach(comment -> commentRepository.delete(comment));

        articleRepository.deleteById(id);
        return null;
    }

    @Transactional
    public List<ArticleSummaryResponse> getArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleSummaryResponse> articleResponses = articles.stream()
                .map(ArticleSummaryResponse::from)
                .toList();
        return articleResponses;
    }
}

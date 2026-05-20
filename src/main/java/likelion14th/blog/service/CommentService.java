package likelion14th.blog.service;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import likelion14th.blog.domain.Article;
import likelion14th.blog.domain.Comment;
import likelion14th.blog.dto.response.CommentResponse;
import likelion14th.blog.exception.ArticleNotFoundException;
import likelion14th.blog.exception.PermissionNotFoundException;
import likelion14th.blog.repository.ArticleRepository;
import likelion14th.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.TenantId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public CommentResponse addComment(String content, String author, Long articleId, String password) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(author, content, article, password);

        commentRepository.save(comment);
        return CommentResponse.of(articleId, comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(""));
        List<Comment> comments = commentRepository.findByArticle(article);
        List<CommentResponse> commentResponses = comments.stream()
                .map(comment -> CommentResponse.of(articleId, comment))
                .toList();
        return commentResponses;
    }
    
    @Transactional
    public CommentResponse updateComment(Long articleId, Long commentId, String author, String content, String password) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다."));

        if (!comment.getArticle().getId().equals(articleId)) {
            throw new IllegalArgumentException("잘못된 접근"); //RunTimeException을 상속한것임 더 세부적인것 이거 써도됌
        }
        if (!comment.getPassword().equals(password)) {
            throw new PermissionNotFoundException("해당 댓글에 대한 수정 권한이 없습니다.");
        }
        comment.update(author, content);
        commentRepository.save(comment);
        return CommentResponse.of(articleId, comment);
    }

    @Transactional
    public Void deleteComment(Long articleId, Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다."));

        if (!comment.getArticle().getId().equals(articleId)) {
            throw new IllegalArgumentException("잘못된 접근");
        }
        if (!comment.getPassword().equals(password)) {
            throw new PermissionNotFoundException("해당 댓글에 대한 삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
        return null;
    }

}

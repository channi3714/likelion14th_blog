package likelion14th.blog.repository;

import likelion14th.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByAuthor(String author);
    Optional<Comment> findByContent(String content);
}

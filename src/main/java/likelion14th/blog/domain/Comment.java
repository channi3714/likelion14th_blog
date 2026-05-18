package likelion14th.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;  // 만약에 이거를 commentId로 해놓고 위 컬럼 어노테이션에서 name 부분 생략하면 자동으로 컬럼명을 comment_id같은 스네이크 케이스로 바꿔줌

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    public Comment(String author, String content, Article article) {
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.article = article;
    }
}

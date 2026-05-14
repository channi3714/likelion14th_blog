package likelion14th.blog.dto.response;

import likelion14th.blog.domain.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ArticleDetailResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    //일종의 생성자
    public static ArticleDetailResponse from(Article article) { //메서드를 항상 인스턴스를 생성하고 메서드 사용해야 하기 때문에 static을 사용해 클래스 인스턴스가 생성되기 전에 사용할 수 있게함.
        return ArticleDetailResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .createdAt(article.getCreatedAt())
                .build();
    }
}

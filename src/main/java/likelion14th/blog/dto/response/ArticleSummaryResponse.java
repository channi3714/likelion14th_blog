package likelion14th.blog.dto.response;

import likelion14th.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ArticleSummaryResponse {
    private String title;
    private String content;
    private String author;

    public static ArticleSummaryResponse from(Article article) {
        return ArticleSummaryResponse.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .build();
    }
}

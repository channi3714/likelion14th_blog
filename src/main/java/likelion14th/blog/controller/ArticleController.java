package likelion14th.blog.controller;

import likelion14th.blog.domain.Article;
import likelion14th.blog.dto.request.ArticleRequest;
import likelion14th.blog.dto.response.ApiResponse;
import likelion14th.blog.dto.response.ArticleResponse;
import likelion14th.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // controller어노테이션 대신 RestController사용 (response body + @Controller)
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping()
    public ResponseEntity<ApiResponse<ArticleResponse>> addArticle(@RequestBody ArticleRequest request) {
        ArticleResponse articleResponse =
                articleService.addArticle(request.getTitle(), request.getContent(), request.getAuthor(), request.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(201, "게시글 생성에 성공하였습니다.", articleResponse));
//        return ResponseEntity.ok(ApiResponse.created(201, "게시글 생성에 성공하였습니다.", articleResponse)); // ApiResponse에 created메소드 만들고 해도됨
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> getOneArticle(@PathVariable("article-id") Long id) { // @PathVariabla Long articleId랑 같음 그냥 id 쓰면 달라서 인식 못함
        ArticleResponse articleResponse = articleService.getOneArticle(id);

        return ResponseEntity.ok(ApiResponse.success(200, "게시글 개별 조회에 성공하였습니다.", articleResponse));
    }
}

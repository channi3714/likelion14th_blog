package likelion14th.blog.controller;

import likelion14th.blog.dto.request.ArticleRequest;
import likelion14th.blog.dto.request.UpdateArticleRequest;
import likelion14th.blog.dto.response.ApiResponse;
import likelion14th.blog.dto.response.ArticleDetailResponse;
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
    public ResponseEntity<ApiResponse<ArticleDetailResponse>> addArticle(@RequestBody ArticleRequest request) {
        ArticleDetailResponse articleDetailResponse =
                articleService.addArticle(request.getTitle(), request.getContent(), request.getAuthor(), request.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(201, "게시글 생성에 성공하였습니다.", articleDetailResponse));
//        return ResponseEntity.ok(ApiResponse.created(201, "게시글 생성에 성공하였습니다.", articleResponse)); // ApiResponse에 created메소드 만들고 해도됨
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ApiResponse<ArticleDetailResponse>> getOneArticle(@PathVariable("article-id") Long id) { // @PathVariabla Long articleId랑 같음 그냥 id 쓰면 달라서 인식 못함
        ArticleDetailResponse articleDetailResponse = articleService.getOneArticle(id);

        return ResponseEntity.ok(ApiResponse.success(200, "게시글 개별 조회에 성공하였습니다.", articleDetailResponse));
    }

//    @GetMapping()
//    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getArticles

    @PatchMapping("/{article-id}")
    public ResponseEntity<ApiResponse<ArticleDetailResponse>> updateArticle(@PathVariable("article-id") Long id, @RequestBody UpdateArticleRequest request) {
        ArticleDetailResponse articleDetailResponse = articleService.updateArticle(id, request.getTitle(), request.getContent());

        return ResponseEntity.ok(ApiResponse.success(200, "게시글을 업데이트 하였습니다.",articleDetailResponse));
    }
}

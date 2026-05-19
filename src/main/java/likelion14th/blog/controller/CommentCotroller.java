package likelion14th.blog.controller;

import likelion14th.blog.domain.Comment;
import likelion14th.blog.dto.request.CommentRequest;
import likelion14th.blog.dto.request.CommentUpdateRequest;
import likelion14th.blog.dto.request.DeleteCommentRequest;
import likelion14th.blog.dto.response.ApiResponse;
import likelion14th.blog.dto.response.CommentResponse;
import likelion14th.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class CommentCotroller {

    private final CommentService commentService;

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(@RequestBody CommentRequest request, @PathVariable Long articleId) {
        CommentResponse commentResponse = commentService.addComment(request.getContent(), request.getAuthor(), articleId, request.getPassword());

        return ResponseEntity.ok(ApiResponse.success(201, "댓글 생성에 성공하였습니다.", commentResponse));
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long articleId) {
        List<CommentResponse> commentResponses = commentService.getComments(articleId);

        return ResponseEntity.ok(ApiResponse.success(200, "댓글 전체 조회에 성공하였습니다.", commentResponses));
    }

    @PatchMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @RequestBody CommentUpdateRequest request,
            @PathVariable Long articleId,
            @PathVariable Long commentId) {
        CommentResponse commentResponse = commentService.updateComment(articleId, commentId, request.getAuthor(), request.getContent(), request.getPassword());
        return ResponseEntity.ok(ApiResponse.success(200, "댓글 수정에 성공하였습니다.", commentResponse));
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @RequestBody DeleteCommentRequest request,
            @PathVariable Long articleId,
            @PathVariable Long commentId) {
        commentService.deleteComment(articleId, commentId, request.getPassword());
        return ResponseEntity.ok(ApiResponse.success(204, "해당 게시글을 삭제하였습니다."));
    }
}

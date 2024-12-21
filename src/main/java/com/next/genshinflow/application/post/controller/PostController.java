package com.next.genshinflow.application.post.controller;

import java.util.List;

import com.next.genshinflow.application.BasePageResponse;
import com.next.genshinflow.application.post.dto.GuestPostActionRequest;
import com.next.genshinflow.application.post.dto.PostModifyRequest;
import com.next.genshinflow.application.post.dto.PostResponse;
import com.next.genshinflow.application.post.dto.PostCreateRequest;
import com.next.genshinflow.application.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/postings")
@RestController
@Tag(name = "Posting API", description = "포스팅 API")
public class PostController {
    private final PostService postService;

    @Operation(summary = "회원 게시물 작성")
    @PostMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostResponse> createUserPost(@Valid @RequestBody PostCreateRequest request) {
        PostResponse postResponse = postService.createUserPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @Operation(summary = "비회원 게시물 작성")
    @PostMapping("/guest")
    public ResponseEntity<PostResponse> createGuestPost(@Valid @RequestBody PostCreateRequest request) {
        PostResponse postResponse = postService.createGuestPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @Operation(summary = "포스팅 목록", description = "size = 20")
    @GetMapping
    public ResponseEntity<BasePageResponse<PostResponse>> getPosts(
        @Positive @RequestParam(value = "page", defaultValue = "1") int page,
        @Positive @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        BasePageResponse<PostResponse> pageResponse = postService.getPosts(page-1, size);
        return ResponseEntity.ok(pageResponse);
    }

    @Operation(summary = "회원 게시물 완료 처리")
    @PatchMapping("/user/{postId}/complete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostResponse> completeUserPost(@PathVariable("postId") @NotNull long postId) {
        PostResponse postResponse = postService.completeUserPost(postId);
        return ResponseEntity.ok(postResponse);
    }

    @Operation(summary = "비회원 게시물 완료 처리")
    @PatchMapping("/guest/complete")
    public ResponseEntity<PostResponse> completeGuestPost(@Valid @RequestBody GuestPostActionRequest request) {
        PostResponse postResponse = postService.completeGuestPost(request);
        return ResponseEntity.ok(postResponse);
    }

    @Operation(summary = "회원 게시물 수정")
    @PatchMapping("/user/modify")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostResponse> modifyUserPost(@Valid @RequestBody PostModifyRequest request) {
        PostResponse postResponse = postService.modifyUserPost(request);
        return ResponseEntity.ok(postResponse);
    }

    @Operation(summary = "비회원 게시물 수정")
    @PatchMapping("/guest/modify")
    public ResponseEntity<PostResponse> modifyGuestPost(@Valid @RequestBody PostModifyRequest request) {
        PostResponse postResponse = postService.modifyGuestPost(request);
        return ResponseEntity.ok(postResponse);
    }

    @Operation(summary = "회원 리퀘스트 끌어올리기")
    @PatchMapping("/user/{postId}/pull-up")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostResponse> pullUpUserPost(@PathVariable("postId") @NotNull long postId) {
        PostResponse postResponse = postService.pullUpUserPost(postId);
        return ResponseEntity.ok(postResponse);
    }

    @Operation(summary = "비회원 리퀘스트 끌어올리기")
    @PatchMapping("/guest/pull-up")
    public ResponseEntity<PostResponse> pullUpGuestPost(@Valid @RequestBody GuestPostActionRequest request) {
        PostResponse postResponse = postService.pullUpGuestPost(request);
        return ResponseEntity.ok(postResponse);
    }

    @Operation(summary = "회원 게시물 삭제")
    @DeleteMapping("/user/{postId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> deleteUserPost(@PathVariable("postId") @NotNull long postId) {
        postService.deleteUserPost(postId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비회원 게시물 삭제")
    @DeleteMapping("/guest")
    public ResponseEntity<Void> deleteGuestPost(@Valid @RequestBody GuestPostActionRequest request) {
        postService.deleteGuestPost(request);
        return ResponseEntity.ok().build();
    }
}

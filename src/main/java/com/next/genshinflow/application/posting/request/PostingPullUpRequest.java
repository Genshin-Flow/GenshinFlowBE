package com.next.genshinflow.application.posting.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostingPullUpRequest(
    @Schema(description = "끌올할 posting id", type = "long", example = "1234")
    long postingId,
    @Schema(description = "비밀번호(비회원일 경우만)", type = "String", example = "1234")
    Integer password
) {

}

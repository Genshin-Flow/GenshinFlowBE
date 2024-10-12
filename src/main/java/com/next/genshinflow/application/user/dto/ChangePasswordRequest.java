package com.next.genshinflow.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @Schema(description = "사용자 비밀번호", type = "String", example = "example1234!")
    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String password;
}

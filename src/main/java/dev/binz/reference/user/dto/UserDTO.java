package dev.binz.reference.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "이름을 입력해야 합니다.")  // 공백, null 허용 안 함
    private String name;

    @NotBlank
    @Size(min = 5, max = 20, message = "비밀번호는 5~20자여야 합니다.")  // 길이 제한
    private String password;

    // 생성자, Getter, Setter
    public UserDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

package dev.binz.reference.page;

import dev.binz.reference.page.dto.UserPageDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // @PathVariable import 추가
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional; // Optional import 추가

@Controller
@RequestMapping("/page")
public class PageController {

    // 임시 사용자 데이터 (실제 DB 대신 사용)
    private List<UserPageDto> allUsers = Arrays.asList(
            new UserPageDto("Alice", 30, "user001", "Class A"),
            new UserPageDto("Bob", 24, "user002", "Class B"),
            new UserPageDto("Charlie", 35, "user003", "Class A"),
            new UserPageDto("David", 28, "user004", "Class B"),
            new UserPageDto("Eve", 32, "user005", "Class C"),
            new UserPageDto("Frank", 29, "user006", "Class A")
    );

    @GetMapping("/test")
    public String testPage(Model model) {
        // 초기 로드 시 age <= 30인 사용자만 필터링하여 전달
        List<UserPageDto> initialUsers = allUsers.stream()
                .filter(user -> user.getAge() <= 30)
                .toList();
        model.addAttribute("users", initialUsers);
        return "test";
    }

    @GetMapping("/addMoreUsers")
    @ResponseBody
    public List<UserPageDto> addMoreUsers() {
        // 모든 사용자 데이터를 반환 (클라이언트에서 필터링)
        return allUsers;
    }

    // AJAX 요청을 처리하여 특정 사용자 상세 정보를 반환하는 엔드포인트
    @GetMapping("/userDetails/{id}")
    @ResponseBody
    public UserPageDto getUserDetails(@PathVariable String id) {
        // 임시 데이터에서 ID에 해당하는 사용자 찾기
        Optional<UserPageDto> user = allUsers.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        
        // 찾으면 반환, 없으면 null 또는 적절한 예외 처리 (여기서는 간단히 null 반환)
        return user.orElse(null);
    }
}

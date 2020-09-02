package pl.ligatenisa.tenis.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    @GetMapping("/")
    public String welcome(){
        return "Witam w API ligi tenisowej! Lista wszystkich endpointów znajduje się w linku swagger-ui.html";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Witam w panelu administratora. Skorzystaj z API. Poniżej znajduje się lista endpointów.";
    }

    @GetMapping("/user")
    public String user(){
        return "Cześć User! Ty również możesz korzystać z ale nie możesz dodawać i usuwać.";
    }
}

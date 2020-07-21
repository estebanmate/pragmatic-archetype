#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/api-doc")
    public String ApiDocumentation() {
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping("/swagger")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
}

package ss.pku.attacktraceproject.honeytoken.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss.pku.attacktraceproject.honeytoken.honeytokenserver.TraceProcessor;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/home")
public class WebController {
    @RequestMapping(value = "/sub/{id}")
    public void startParse(HttpServletRequest request) {
        TraceProcessor processor = new TraceProcessor();

        processor.startTrace(request);
    }

}

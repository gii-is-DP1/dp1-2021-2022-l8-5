package org.springframework.dwarf.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
	    // get error status
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    if (status != null) {
			int statusCode = Integer.parseInt(status.toString());
			// display specific error page
		    if (statusCode == HttpStatus.NOT_FOUND.value()) {
			    return "errors/error404";
		    } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			    return "errors/error500";
		    } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
		    	String view = "errors/error403";
			    return view;
		    }
	    }
	    // display generic error
	    return "error";
    }
   
}*/

package kz.bdl.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // 403 Forbidden
                model.addAttribute("errorCode", "403");
                model.addAttribute("errorMessage", "Доступ запрещен");
                model.addAttribute("errorDescription", 
                    "У вас нет прав для доступа к этой странице. Обратитесь к администратору системы для получения необходимых прав доступа.");
                return "error/403";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // 404 Not Found
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorMessage", "Страница не найдена");
                model.addAttribute("errorDescription", 
                    "Запрашиваемая страница не существует или была перемещена.");
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // 500 Internal Server Error
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorMessage", "Внутренняя ошибка сервера");
                model.addAttribute("errorDescription", 
                    "Произошла внутренняя ошибка сервера. Попробуйте позже или обратитесь к администратору.");
                return "error/500";
            }
        }
        
        // Default error page
        model.addAttribute("errorCode", "Ошибка");
        model.addAttribute("errorMessage", "Произошла ошибка");
        model.addAttribute("errorDescription", 
            "Произошла непредвиденная ошибка. Попробуйте позже или обратитесь к администратору.");
        return "error/general";
    }
} 
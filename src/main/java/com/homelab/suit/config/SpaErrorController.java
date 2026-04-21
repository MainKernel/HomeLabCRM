package com.homelab.suit.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Якщо Spring не знайшов сторінку (404)
            if (statusCode == HttpStatus.NOT_FOUND.value()) {

                // Отримуємо оригінальний шлях запиту
                String requestUri = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
                if (requestUri == null) {
                    requestUri = request.getRequestURI();
                }

                // Якщо це НЕ запит до нашого бекенд API, віддаємо фронтенд Vue.js
                if (requestUri != null && !requestUri.startsWith("/api/") && !requestUri.startsWith("/uploads/")) {
                    return "forward:/index.html";
                }
            }
        }

        // Для всіх інших помилок (500, 403 тощо) або неіснуючих API ендпоінтів повертаємо стандартну поведінку
        return "error";
    }
}
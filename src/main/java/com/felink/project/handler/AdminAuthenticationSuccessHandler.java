package com.felink.project.handler;

import com.felink.project.model.ResponseJSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdminAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        JSONObject data = new JSONObject();
        data.put("hasLogin", true);
        ResponseJSON responseJSON = new ResponseJSON(200, "success", data);
        response.getWriter().write(responseJSON.getJSON().toString());
    }
 }
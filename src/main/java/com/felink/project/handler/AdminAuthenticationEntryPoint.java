package com.felink.project.handler;

import com.felink.project.model.ResponseJSON;
import net.sf.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        JSONObject data = new JSONObject();
        data.put("hasLogin", false);
        ResponseJSON responseJSON = new ResponseJSON(0, "No login", data);
        httpServletResponse.getWriter().write(responseJSON.getJSON().toString());
    }
}

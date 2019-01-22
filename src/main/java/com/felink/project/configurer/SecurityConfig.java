package com.felink.project.configurer;

import com.felink.project.handler.AdminAuthenticationFailureHandler;
import com.felink.project.handler.AdminAuthenticationLogoutSuccessHandler;
import com.felink.project.handler.AdminAuthenticationSuccessHandler;
import com.felink.project.handler.AdminAuthenticationEntryPoint;
import com.felink.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AdminAuthenticationEntryPoint entryPoint;

    @Autowired
    AdminAuthenticationFailureHandler failureHandler;

    @Autowired
    AdminAuthenticationSuccessHandler successHandler;

    @Autowired
    AdminAuthenticationLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/*")
                .antMatchers("/login_success")
                .antMatchers("/css/*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                .and()
                .formLogin()
                .loginPage("/login.html")
                .permitAll()

                .and()
                .logout()
                .logoutSuccessUrl("/login.html")
                .permitAll();

//        http.authorizeRequests()
//                .antMatchers("/", "/home")
//                .permitAll() //其他地址的访问均需验证权限
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin() //指定登录页是"/login"
//                .loginPage("/login.html") //登录成功后默认跳转到
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/index.html")
//                .failureForwardUrl("/login.html")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout.html") //退出登录后的默认url是"/login"
//                .logoutSuccessUrl("/login.html")
//                .permitAll(); //解决非thymeleaf的form表单提交被拦截问题

        http.csrf().disable(); //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UserService()).passwordEncoder(passwordEncoder());
        // 也可以将用户名密码写在内存，不推荐
//        auth.inMemoryAuthentication().withUser("admin").password("111111").roles("USER");
    }

    /**
     * 设置用户密码的加密方式为MD5加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     *从数据库中读取用户信息
     */
    @Bean
    @Primary
    public UserDetailsService UserService() {
        return new UserServiceImpl();
    }
}

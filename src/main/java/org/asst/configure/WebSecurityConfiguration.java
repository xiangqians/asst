package org.asst.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author xiangqian
 * @date 21:57 2022/09/05
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源放行
        web.ignoring().antMatchers("/lib/**",
                "/js/**",
                "/css/**",
                "/img/**",
                "/json/**",
                "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Refused to display 'http://localhost:8080/' in a frame because it set 'X-Frame-Options' to 'deny'.
        http.headers().frameOptions().sameOrigin();

        http.csrf().disable();
        
        super.configure(http);
    }
}

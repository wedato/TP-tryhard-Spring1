package fr.orleans.info.wsi.cc.tpnote.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CryptoConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetails();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/quizz/utilisateur").permitAll()
                .antMatchers(HttpMethod.GET,"/api/quizz/utilisateur/*").authenticated()
                .antMatchers(HttpMethod.POST,"/api/quizz/question").hasRole("PROFESSEUR")
                .antMatchers(HttpMethod.GET, "/api/quizz/question/*").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/quizz/question/*/vote").hasRole("ETUDIANT")
                .antMatchers(HttpMethod.GET,"/api/quizz/question/*/vote").hasRole("PROFESSEUR")
                .and().httpBasic()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}

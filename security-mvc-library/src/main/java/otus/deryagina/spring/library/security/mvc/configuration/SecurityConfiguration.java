package otus.deryagina.spring.library.security.mvc.configuration;

import lombok.RequiredArgsConstructor;
import org.omg.DynamicAny.DynArray;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        auth.jdbcAuthentication().dataSource(dataSource);
        //auth.inMemoryAuthentication().withUser(userBuilder.username("admin").password("password").roles("ADMIN"));
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/show-login-form")
                    .loginProcessingUrl("/authenticateTheUser")
                    .permitAll()
                .and()
                    .logout().permitAll();

    }
}

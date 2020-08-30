package htw.smartcity.aggregator.security;

import htw.smartcity.aggregator.util.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final String REST_ADMIN_PASSWORD = "Ebp4Uw5UfajpHJvv";

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        /*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser("user")
                    .password(passwordEncoder.encode(ConfigProperties.REST_USER_PASSWORD))
                    .roles("USER")
                .and()
                .withUser("admin")
                    .password(passwordEncoder.encode(REST_ADMIN_PASSWORD))
                    .roles("USER", "ADMIN");
         */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
        http.httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers("/temperatures/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable();
    }
}
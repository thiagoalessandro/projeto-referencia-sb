package br.com.packagebase.projetoreferenciasb.config;

import br.com.packagebase.projetoreferenciasb.component.PropertiesApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ActuatorSecurity extends WebSecurityConfigurerAdapter {

    private final PropertiesApp propertiesApp;

    public ActuatorSecurity(PropertiesApp propertiesApp) {
        this.propertiesApp = propertiesApp;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(propertiesApp.getAppCredentialsUser())
                .password(passwordEncoder().encode(propertiesApp.getAppCredentialsPassword()))
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/**").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic();
    }

}

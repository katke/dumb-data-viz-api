package kpatke.me.dumbdatavizapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class EndpointSecurity extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // TODO strengthen/remove
    http.authorizeRequests().antMatchers("/line-graph").permitAll();
  }
}
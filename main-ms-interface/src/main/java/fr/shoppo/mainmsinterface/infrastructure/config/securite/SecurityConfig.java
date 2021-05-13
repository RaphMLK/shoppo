package fr.shoppo.mainmsinterface.infrastructure.config.securite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static fr.shoppo.mainmsinterface.infrastructure.config.securite.PrexifeRoleUrl.*;
import static fr.shoppo.mainmsinterface.infrastructure.config.securite.Role.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Classe de configuration de la sécurité du back
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationProvider provider;

    static String URL_ROLE_CLIENT;
    static String URL_ROLE_COMMERCANT;
    static String URL_ROLE_ADMIN;
    static String URL_CREATE_COMMERCE_ADMIN1;
    static String URL_CREATE_COMMERCE_ADMIN2;
    static String URL_ADD_COMMERCANT_COMMERCE1;
    static String URL_ADD_COMMERCANT_COMMERCE2;

    static RequestMatcher PROTECTED_URLS;

    public SecurityConfig(final AuthenticationProvider authenticationProvider){
        super();
        this.provider = authenticationProvider;
        URL_ROLE_CLIENT = CLIENT_URL.libelleRoute();
        URL_ROLE_COMMERCANT = COMMERCANT_URL.libelleRoute();
        URL_ROLE_ADMIN = ADMIN_URL.libelleRoute();
        URL_CREATE_COMMERCE_ADMIN1="/admin/commerce";
        URL_CREATE_COMMERCE_ADMIN2="/admin/commerce/";
        URL_ADD_COMMERCANT_COMMERCE1="/admin/commerce/addCommercant";
        URL_ADD_COMMERCANT_COMMERCE2="/admin/commerce/addCommercant/";
        PROTECTED_URLS =new OrRequestMatcher(
                new AntPathRequestMatcher(URL_ROLE_CLIENT)
                ,new AntPathRequestMatcher(URL_ROLE_COMMERCANT)
                ,new AntPathRequestMatcher(URL_ROLE_ADMIN)
                ,new AntPathRequestMatcher(URL_CREATE_COMMERCE_ADMIN1, GET.name())
                ,new AntPathRequestMatcher(URL_CREATE_COMMERCE_ADMIN2, GET.name())
                ,new AntPathRequestMatcher(URL_ADD_COMMERCANT_COMMERCE1, POST.name())
                ,new AntPathRequestMatcher(URL_ADD_COMMERCANT_COMMERCE2, POST.name())
        );
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling()
                .and()
                    .authenticationProvider(provider)
                    .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(URL_ROLE_CLIENT).hasRole(CLIENT.libelle())
                    .antMatchers(URL_ROLE_COMMERCANT).hasRole(COMMERCANT.libelle())
                    .antMatchers(URL_ROLE_ADMIN).hasRole(ADMIN.libelle())
                    .antMatchers(GET,URL_CREATE_COMMERCE_ADMIN1,URL_CREATE_COMMERCE_ADMIN2)
                            .hasRole(ADMIN.libelle())
                    .antMatchers(POST,URL_ADD_COMMERCANT_COMMERCE1,URL_ADD_COMMERCANT_COMMERCE2)
                            .hasRole(COMMERCANT.libelle())
                    .anyRequest().permitAll()
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .logout().disable();
    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

}


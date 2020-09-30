package io.baselogic.springsecurity.configuration;

import io.baselogic.springsecurity.service.DefaultEventService;
import io.baselogic.springsecurity.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Security Configuration  Class
 * @see WebSecurityConfigurerAdapter
 * @since chapter02.01 created
 * @since chapter02.02 Added formLogin and logout configuration
 * @since chapter02.03 Added basic role-based authorization
 * @since chapter02.04 converted antMatchers to SPeL expressions
 * @since chapter02.05 Added .defaultSuccessUrl("/default")
 * @since chapter03.01 Converted configure(HttpSecurity) to use DSL
 * @since chapter03.01 Added PasswordEncoder passwordEncoder()
 * @since chapter03.02 Created userDetailsService() to return {@link UserDetailsManager}
 * @since chapter03.03 Removed userDetailsService() and configure(HttpSecurity) methods
 * @since chapter03.05 Added auth.authenticationProvider(EventUserAuthenticationProvider)
 * @since chapter03.06 Added .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
 * @since chapter04.00 removed .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
 * @since chapter04.01 Exposed 'JdbcUserDetailsManager' as 'UserDetailsManager' named 'userDetailsService'
 * @since chapter04.03 Added custom SQL Queries
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_ANONYMOUS = "ANONYMOUS";
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Autowired
    private DataSource dataSource;

    @Autowired @Qualifier("customCreateUserSql")
    private String customCreateUserSql;

    @Autowired @Qualifier("customCreateUserAuthoritiesSql")
    private String customCreateUserAuthoritiesSql;

    @Autowired @Qualifier("customUserByUsernameQuery")
    private String customUserByUsernameQuery;

    @Autowired @Qualifier("customUserByUsernameAuthoritiesQuery")
    private String customUserByUsernameAuthoritiesQuery;


    /**
     * Configure AuthenticationManager with inMemory credentials.
     *
     * NOTE:
     * Due to a known limitation with JavaConfig:
     * <a href="https://jira.spring.io/browse/SPR-13779">
     *     https://jira.spring.io/browse/SPR-13779</a>
     *
     * We cannot use the following to expose a {@link UserDetailsManager}
     * <pre>
     *     http.authorizeRequests()
     * </pre>
     *
     * In order to expose {@link UserDetailsManager} as a bean, we must create  @Bean
     *
     * @see UserDetailsManager {@link SecurityConfig#userDetailsService()}}
     * @see EventService  {@link DefaultEventService}
     *
     * @param auth       AuthenticationManagerBuilder
     * @throws Exception Authentication exception
     *
     * @since chapter04.03 Added custom SQL queries
     */
    @Description("Configure AuthenticationManager with inMemory credentials")
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(customUserByUsernameQuery)
                .authoritiesByUsernameQuery(customUserByUsernameAuthoritiesQuery)
                .passwordEncoder(passwordEncoder())
        ;
    }

    /**
     * The parent method from {@link WebSecurityConfigurerAdapter} (public UserDetailsService userDetailsService())
     * originally returns a {@link org.springframework.security.core.userdetails.UserDetailsService},
     * but this needs to be a {@link UserDetailsManager}
     * UserDetailsManager vs UserDetailsService
     *
     * @since chapter04.03 Added custom SQL queries
     */
    @Bean
    @Description("Expose 'JdbcUserDetailsManager' as 'UserDetailsManager' named 'userDetailsService'")
    @Override
    public UserDetailsManager userDetailsService() {
        JdbcUserDetailsManager judm = new JdbcUserDetailsManager();
        judm.setDataSource(dataSource);

        // Override default SQL for JdbcUserDetailsManager
        judm.setUsersByUsernameQuery(customUserByUsernameQuery);
        judm.setAuthoritiesByUsernameQuery(customUserByUsernameAuthoritiesQuery);

        // NOTE: This is not available through AuthenticationManagerBuilder
        judm.setCreateUserSql(customCreateUserSql);
        judm.setCreateAuthoritySql(customCreateUserAuthoritiesSql);

        return judm;
    }


    /**
     * HTTP Security configuration
     *
     * <http auto-config="true"> is equivalent to:
     * <pre>
     *  <http>
     *      <form-login />
     *      <http-basic />
     *      <logout />
     *  </http>
     * </pre>
     *
     * Which is equivalent to the following JavaConfig:
     *
     * <pre>
     *     http.formLogin()
     *          .and().httpBasic()
     *          .and().logout();
     * </pre>
     *
     * @see  org.springframework.security.access.expression.SecurityExpressionRoot
     * @param http HttpSecurity configuration.
     * @throws Exception Authentication configuration exception
     *
     */
    @Description("Configure HTTP Security")
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests

                // Allow anyone to use H2 (NOTE: NOT FOR PRODUCTION USE EVER !!! )
                .antMatchers("/admin/h2/**").permitAll()
                .antMatchers("/actuator/**").permitAll()

                .antMatchers("/").hasAnyRole(ROLE_ANONYMOUS, ROLE_USER)
                .antMatchers("/registration/*").permitAll()
                .antMatchers("/login/*").permitAll()
                .antMatchers("/logout/*").hasAnyRole(ROLE_ANONYMOUS, ROLE_USER)
                .antMatchers("/admin/*").hasRole(ROLE_ADMIN)
                .antMatchers("/events/").hasRole(ROLE_ADMIN)
                .antMatchers("/**").hasRole(ROLE_USER)
        );

        // The default AccessDeniedException
        http.exceptionHandling(handler -> handler
                .accessDeniedPage("/errors/403")
        );

        // Login Configuration
        http.formLogin(form -> form
                .loginPage("/login/form")
                .loginProcessingUrl("/login")
                .failureUrl("/login/form?error")
                .usernameParameter("username") // redundant
                .passwordParameter("password") // redundant
                .defaultSuccessUrl("/default", true)
                .permitAll()
        );

        // Logout Configuration
        http.logout(form -> form
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/form?logout")
                .permitAll()
        );


        // Allow anonymous users
        http.anonymous();

        // CSRF is enabled by default, with Java Config
        http.csrf().disable();

        // Cross Origin Resource Sharing
        http.cors().disable();

        // HTTP Security Headers
        http.headers().disable();
        http.headers().xssProtection().disable();
        http.headers().contentTypeOptions().disable();

        // Enable <frameset> in order to use H2 web console
        http.headers().frameOptions().disable();

    } // end configure


    /**
     * This is the equivalent to:
     * <pre><http pattern="/css/**" security="none"/></pre>
     *
     *
     * @param web {@link WebSecurity} is created by {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration}
     * <p>
     * The {@link WebSecurity} is created by {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration} to create the
     * {@link org.springframework.security.web.FilterChainProxy} known as the Spring Security Filter Chain
     * (springSecurityFilterChain). The springSecurityFilterChain is the {@link javax.servlet.Filter} that
     * the {@link org.springframework.web.filter.DelegatingFilterProxy} delegates to.
     * </p>
     */
    @Description("Configure Web Security")
    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/img/**")
                .antMatchers("/webjars/**")
        ;
    }


    /**
     * Create a DelegatingPasswordEncoder
     *  see https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
     *
     *  Standard use, see {@link PasswordEncoderFactories}:
     *  <code>return PasswordEncoderFactories.createDelegatingPasswordEncoder();</code>
     *
     * Older implementations, such as SHAPasswordEncoder,
     * would require the client to pass in a salt value when encoding the password.
     *
     * BCrypt, however, will internally generate a random salt instead.
     * This is important to understand because it means that each call will have a different result,
     * and so we need to only encode the password once.
     *
     * Also be aware that the BCrypt algorithm generates a String of length 60,
     * so we need to make sure that the password will be stored in a column that can accommodate it.
     * A common mistake is to create a column of a different length and then get an Invalid Username
     * or Password error at authentication time.
     *
     * @return DelegatingPasswordEncoder
     * @since chapter02.01
     * @since chapter04.04 changed to BCrypt Password Encoder
     */
    @Bean
    @Description("Configure Password Encoder")
    public PasswordEncoder passwordEncoder() {

        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder(4));

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }


} // The End...

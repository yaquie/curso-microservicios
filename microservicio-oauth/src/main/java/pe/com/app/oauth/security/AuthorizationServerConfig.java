package pe.com.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private InfoAdicionalToken aditionalToken;

	@Autowired
//	@Qualifier("authenticationManager")
	private AuthenticationManager authentificationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// security -> permite dar seguridad a los endpoints

		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		System.out.println("id -> " + env.getProperty("config.security.oauth.client.id"));
//		System.out.println("secret -> " + env.getProperty("config.security.oauth.client.secret"));

		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
				.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
				.scopes("read", "write").authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(3600);

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(aditionalToken, accessTokenConverter()));

		endpoints.authenticationManager(authentificationManager).tokenStore(tokenStore())
				.accessTokenConverter(accessTokenConverter()).tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
//		System.out.println("key -> " + env.getProperty("config.security.oauth.jwt.key"));
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		// env.getProperty("config.security.oauth.jwt.key")
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));
		return tokenConverter;
	}
}

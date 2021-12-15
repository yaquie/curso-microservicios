package pe.com.app.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.com.app.commons.usuarios.model.entity.Usuario;
import pe.com.app.oauth.clients.UsuarioFeignClient;


@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {
private Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioFeignClient client;

	// metodo que permite obtener el usuario por el username
	@Override
	public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = client.findByUsername(username);
		if (usuario == null) {
			log.error("Error en el login, el usuario '" + username + "' no existe en el sistema ");
			throw new UsernameNotFoundException(
					"Error en el login, el usuario '" + username + "' no existe en el sistema ");
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		log.info("Usuario Autentificado " + username);

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

}

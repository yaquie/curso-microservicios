package pe.com.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import pe.com.app.commons.usuarios.model.entity.Rol;
import pe.com.app.commons.usuarios.model.entity.Usuario;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		// metodo que permite mostrar las llaves primarias en el response
//		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
		config.exposeIdsFor(Usuario.class, Rol.class);
	}

}

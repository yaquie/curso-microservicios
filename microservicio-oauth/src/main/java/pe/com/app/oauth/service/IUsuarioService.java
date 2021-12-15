package pe.com.app.oauth.service;

import pe.com.app.commons.usuarios.model.entity.Usuario;

public interface IUsuarioService {
public Usuario findByUsername(String username);
}

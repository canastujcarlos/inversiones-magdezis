/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.inversionesmagdezis.inversiones.service;

import main.java.com.inversionesmagdezis.inversiones.model.Usuario;
import main.java.com.inversionesmagdezis.inversiones.repository.UsuarioRepository;
import main.java.com.inversionesmagdezis.inversiones.security.BCrypt;

/**
 *
 * @author informatica
 */
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public boolean registrarUsuario(String nombre, String apellido, String nombreUsuario, String email, String contrasenaHash, int idRol) {
        if (camposInvalidos(nombre, apellido, nombreUsuario, email, contrasenaHash)) {
            return false;
        }

        String contrasenaEncriptada = BCrypt.hashpw(contrasenaHash, BCrypt.gensalt());

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        usuario.setContrasenaHash(contrasenaHash);
        usuario.setIdRol(idRol);

        return usuarioRepository.registrar(usuario);
    }
    
    private boolean camposInvalidos(String nombre, String apellido, String nombreUsuario, String correo, String contrasena) {
        return nombre == null || nombre.trim().isEmpty() ||
               apellido == null || apellido.trim().isEmpty() ||
               nombreUsuario == null || nombreUsuario.trim().isEmpty() ||
               correo == null || correo.trim().isEmpty() ||
               contrasena == null || contrasena.trim().isEmpty();
    }
}

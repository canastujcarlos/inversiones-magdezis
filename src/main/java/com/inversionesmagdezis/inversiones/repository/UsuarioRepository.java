/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.inversionesmagdezis.inversiones.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.inversionesmagdezis.inversiones.config.DataBaseConnection;
import main.java.com.inversionesmagdezis.inversiones.model.Usuario;

/**
 *
 * @author informatica
 */
public class UsuarioRepository {
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, nombre_usuario, correo, contrasena, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = DataBaseConnection.getDataBaseConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getNombreUsuario());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getContrasenaHash()); 
            pstmt.setInt(6, usuario.getIdRol());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                if (usuario.getIdRol() == 2) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idUsuario = rs.getInt(1);
                            String sqlCliente = "INSERT INTO usuario (id_usuario, telefono, direccion) VALUES (?, '', '')";
                            try (PreparedStatement pstmtCliente = conexion.prepareStatement(sqlCliente)) {
                                pstmtCliente.setInt(1, idUsuario);
                                pstmtCliente.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario en la BD: " + e.getMessage());
            return false;
        }
    }

}

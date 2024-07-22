
package com.DesignPatterns.services;

import com.DesignPatterns.Conexion.Conexion;
import com.DesignPatterns.models.*;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminService {

    Connection conn;

    // Métodos CRUD para la tabla Usuarios

    public int createUsuario(String nombre, String apellido, String email, String contra, String fotoPerfil, int tipo) {
        int idUsuario = -1;
        try {
            conn = Conexion.connectar();
            Statement statement = conn.createStatement();
            String query = "CALL CreateUsuario('" + nombre + "', '" + apellido + "', '" + email + "', '" + contra + "', '" + fotoPerfil + "', " + tipo + ")";
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idUsuario;
    }

    public void readUsuario(int idUsuario) {
        try {
            conn = Conexion.connectar();
            Statement statement = conn.createStatement();
            String query = "CALL ReadUsuario(" + idUsuario + ")";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String contra = rs.getString("contra");
                String fotoPerfil = rs.getString("foto_perfil");
                int tipo = rs.getInt("tipo");
                System.out.println("Nombre: " + nombre + ", Apellido: " + apellido + ", Email: " + email + ", Contraseña: " + contra + ", Foto de Perfil: " + fotoPerfil + ", Tipo: " + tipo);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUsuario(int idUsuario, String nombre, String apellido, String email, String contra, String fotoPerfil, int tipo, int exp) {
        try {
            conn = Conexion.connectar();
            Statement statement = conn.createStatement();
            String query = "CALL UpdateUsuario(" + idUsuario + ", '" + nombre + "', '" + apellido + "', '" + email + "', '" + contra + "', '" + fotoPerfil + "', " + tipo + ", " + exp + ")";
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUsuario(int idUsuario) {
        try {
            conn = Conexion.connectar();
            Statement statement = conn.createStatement();
            String query = "CALL DeleteUsuario(" + idUsuario + ")";
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


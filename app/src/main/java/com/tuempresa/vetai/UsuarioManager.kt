package com.tuempresa.vetai.ui.theme



import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object UsuariosManager {

    private const val PREFS_NAME = "VetaiPrefs"
    private const val KEY_USUARIOS = "usuarios"
    private const val KEY_USUARIO_ACTUAL = "usuario_actual"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Obtener todos los usuarios registrados
    fun obtenerUsuarios(context: Context): MutableList<Usuario> {
        val prefs = getPreferences(context)
        val usuariosJson = prefs.getString(KEY_USUARIOS, null)

        return if (usuariosJson != null) {
            val type = object : TypeToken<MutableList<Usuario>>() {}.type
            Gson().fromJson(usuariosJson, type)
        } else {
            mutableListOf()
        }
    }

    // Guardar lista de usuarios
    private fun guardarUsuarios(context: Context, usuarios: MutableList<Usuario>) {
        val prefs = getPreferences(context)
        val usuariosJson = Gson().toJson(usuarios)
        prefs.edit().putString(KEY_USUARIOS, usuariosJson).apply()
    }

    // Registrar nuevo usuario
    fun registrarUsuario(context: Context, usuario: Usuario): Boolean {
        val usuarios = obtenerUsuarios(context)

        // Verificar si el email ya existe
        if (usuarios.any { it.email == usuario.email }) {
            return false // Email ya registrado
        }

        usuarios.add(usuario)
        guardarUsuarios(context, usuarios)
        return true
    }

    // Validar login
    fun validarLogin(context: Context, email: String, contrasena: String): Usuario? {
        val usuarios = obtenerUsuarios(context)
        return usuarios.find { it.email == email && it.contrasena == contrasena }
    }

    // Guardar usuario actual en sesión
    fun guardarUsuarioActual(context: Context, usuario: Usuario) {
        val prefs = getPreferences(context)
        val usuarioJson = Gson().toJson(usuario)
        prefs.edit().putString(KEY_USUARIO_ACTUAL, usuarioJson).apply()
    }

    // Obtener usuario actual
    fun obtenerUsuarioActual(context: Context): Usuario? {
        val prefs = getPreferences(context)
        val usuarioJson = prefs.getString(KEY_USUARIO_ACTUAL, null)

        return if (usuarioJson != null) {
            Gson().fromJson(usuarioJson, Usuario::class.java)
        } else {
            null
        }
    }

    // Cerrar sesión
    fun cerrarSesion(context: Context) {
        val prefs = getPreferences(context)
        prefs.edit().remove(KEY_USUARIO_ACTUAL).apply()
    }
}
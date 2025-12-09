package com.tuempresa.vetai.ui.theme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tuempresa.vetai.ui.theme.dao.*
import com.tuempresa.vetai.ui.theme.entidades.*
@Database(
    entities = [
        Cliente::class,
        Citas::class,
        Mascota::class,
        Veterinario::class,
        Tratamiento::class
               ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun mascotaDao(): MascotaDao
    abstract fun veterinarioDao(): VeterinarioDao
    abstract fun citasDao(): CitasDao
    abstract fun tratamientoDao(): TratamientoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vetai_db"
                ).fallbackToDestructiveMigration() .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
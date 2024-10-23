package com.example.carwash.DataBase


import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carwash.DAO.ClienteDAO
import com.example.carwash.DAO.RegistrolavadoDAO
import com.example.carwash.DAO.ServicioDAO
import com.example.carwash.DAO.VehiculoDAO
import com.example.carwash.Models.Cliente
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Models.Servicio
import com.example.carwash.Models.Vehiculo

@Database(
    entities = [Cliente::class, Vehiculo::class, RegistroLavado::class,Servicio::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDAO(): ClienteDAO
    abstract fun vehiculoDAO(): VehiculoDAO
    abstract fun registroLavadoDAO(): RegistrolavadoDAO
    abstract fun servicioDAO(): ServicioDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
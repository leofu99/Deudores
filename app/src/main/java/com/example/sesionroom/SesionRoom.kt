package com.example.sesionroom
import android.app.Application
import androidx.room.Room
import com.example.sesionroom.model.local.DeudorDataBase
import com.example.sesionroom.model.local.UsuarioDataBase

class SesionRoom : Application(){
   companion object{
       lateinit var database: DeudorDataBase
       lateinit var database2: UsuarioDataBase
   }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            DeudorDataBase::class.java,
            "misdeudores_db"
        ).allowMainThreadQueries().build()


        database2 = Room.databaseBuilder(
            this,
            UsuarioDataBase::class.java,
            "usuarios_db"
        ).allowMainThreadQueries().build()
    }
}
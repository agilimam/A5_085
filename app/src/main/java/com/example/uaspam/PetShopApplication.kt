package com.example.uaspam

import android.app.Application
import com.example.uaspam.di.AppContainer
import com.example.uaspam.di.PetShopContainer


class PetShopApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = PetShopContainer()
    }
}
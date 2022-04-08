package com.wtfcompany.relax.di

import com.wtfcompany.relax.view.firstenter.FirstEnterViewModel
import com.wtfcompany.relax.view.home.HomeViewModel
import com.wtfcompany.relax.view.login.LoginViewModel
import com.wtfcompany.relax.view.menu.MenuViewModel
import com.wtfcompany.relax.view.photo.PhotoViewModel
import com.wtfcompany.relax.view.profile.ProfileViewModel
import com.wtfcompany.relax.view.registration.RegistrationViewModel
import org.koin.dsl.module

val appModule = module {
    single { RegistrationViewModel() }
    single { LoginViewModel() }
    single { ProfileViewModel() }
    single { PhotoViewModel(get()) }
    single { HomeViewModel() }
    single { MenuViewModel() }
    single { FirstEnterViewModel() }
}
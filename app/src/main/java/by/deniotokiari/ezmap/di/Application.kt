package by.deniotokiari.ezmap.di

import android.content.Context
import org.koin.dsl.module

val application = module {
    single { get<Context>().resources }
}
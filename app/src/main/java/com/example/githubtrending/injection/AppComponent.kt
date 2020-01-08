package com.example.githubtrending.injection

import com.example.githubtrending.MainActivity
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: MainActivity)

}
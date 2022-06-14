package apps.plinqdevelopers.lendtech.di

import android.app.Application
import androidx.room.Room
import apps.plinqdevelopers.lendtech.api.ApplicationAPI
import apps.plinqdevelopers.lendtech.room.ApplicationDatabase
import apps.plinqdevelopers.lendtech.utils.Constants.Companion.API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()


    @Provides
    @Singleton
    fun provideApplicationAPI(retrofit: Retrofit): ApplicationAPI =
        retrofit.create(ApplicationAPI::class.java)

    @Provides
    @Singleton
    fun provideApplicationDatabase(application: Application): ApplicationDatabase =
        Room.databaseBuilder(application, ApplicationDatabase::class.java, "LendTech_APP_DB")
            .build()

}
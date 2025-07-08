package com.example.drivequest.di

import android.content.Context
import com.example.drivequest.BuildConfig
import com.example.drivequest.data.remote.api.auth.AuthApiService
import com.example.drivequest.data.remote.datasource.auth.AuthRemoteDataSource
import com.example.drivequest.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.example.drivequest.data.repository.AuthRepositoryImpl
import com.example.drivequest.domain.repository.AuthRepository
import com.example.drivequest.domain.usecase.LoginUseCase
import com.example.drivequest.token.AuthInterceptor
import com.example.drivequest.token.TokenAuthenticator
import com.example.drivequest.token.TokenManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = BuildConfig.API_URL
    // Retrofit提供
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    // OkHttpClient提供
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    // APIサービス提供
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    // Repository提供
    // AuthRepositoryImpl のコンストラクタに @Inject が付いていることを確認してください。
    // 例: class AuthRepositoryImpl @Inject constructor(...) : AuthRepository { ... }
    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteDataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(remoteDataSource)
    }

    // RemoteDataSource提供
    // AuthRemoteDataSourceImple のコンストラクタに @Inject が付いていることを確認してください。
    // 例: class AuthRemoteDataSourceImple @Inject constructor(...) : AuthRemoteDataSource { ... }
    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        apiService: AuthApiService
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(apiService)
    }

    // TokenManager提供
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    // AuthInterceptor提供
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    // TokenAuthenticator提供
    // AuthApiService を Provider<AuthApiService> として受け取ることで循環を解消
    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenManager: TokenManager,
        authApiService: Provider<AuthApiService> // ★ここが Provider になっていることを確認
    ): TokenAuthenticator {
        return TokenAuthenticator(tokenManager, authApiService)
    }

    // UseCase提供
    @Provides
    fun provideLoginUseCase(
        authRepository: AuthRepository,
        tokenManager: TokenManager
    ): LoginUseCase {
        return LoginUseCase(authRepository, tokenManager)
    }
}

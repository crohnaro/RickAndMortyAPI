package network.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dmp.simplemorty.SimpleMortyApplication
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val retrofit = Retrofit.Builder()
        .client(getLogginHttpClient())
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val rickAndMortyService: RickandMortyService by lazy {
        retrofit.create(RickandMortyService::class.java)
    }

    val apiClient = ApiClient(rickAndMortyService)

    private fun getLogginHttpClient(): OkHttpClient{
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        })

        builder.addInterceptor(
            ChuckerInterceptor.Builder(SimpleMortyApplication.context)
                .collector(ChuckerCollector(SimpleMortyApplication.context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )

        return builder.build()
    }
}
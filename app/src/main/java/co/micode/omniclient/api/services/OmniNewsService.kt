package co.micode.omniclient.api.services

import co.micode.omniclient.api.model.generated.OmniNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmniNewsService {

    @GET("/search")
    suspend fun searchForTheNews(
        @Query("query")
        name: String
    ): OmniNewsResponse
}
package kr.genti.data.service

import kr.genti.data.dto.BaseResponse
import kr.genti.data.dto.response.FeedItemDto
import retrofit2.http.GET

interface FeedService {
    @GET("api/v1/users/examples/with-picture")
    suspend fun getExampleItems(): BaseResponse<List<FeedItemDto>>
}

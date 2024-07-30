package kr.genti.data.service

import kr.genti.data.dto.BaseResponse
import kr.genti.data.dto.request.SignupRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface InfoService {
    @POST("api/v1/users/signup")
    suspend fun postSignupData(
        @Body request: SignupRequestDto,
    ): BaseResponse<Boolean>
}
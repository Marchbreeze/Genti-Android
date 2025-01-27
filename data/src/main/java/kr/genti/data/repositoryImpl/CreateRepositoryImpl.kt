package kr.genti.data.repositoryImpl

import kr.genti.data.dataSource.CreateDataSource
import kr.genti.data.dto.request.CreateRequestDto.Companion.toDto
import kr.genti.data.dto.request.CreateTwoRequestDto.Companion.toDto
import kr.genti.data.dto.request.KeyRequestDto.Companion.toDto
import kr.genti.data.dto.request.PurchaseValidRequestDto.Companion.toDto
import kr.genti.data.dto.request.S3RequestDto.Companion.toDto
import kr.genti.domain.entity.request.CreateRequestModel
import kr.genti.domain.entity.request.CreateTwoRequestModel
import kr.genti.domain.entity.request.KeyRequestModel
import kr.genti.domain.entity.request.PurchaseValidRequestModel
import kr.genti.domain.entity.request.S3RequestModel
import kr.genti.domain.entity.response.PromptExampleModel
import kr.genti.domain.entity.response.S3PresignedUrlModel
import kr.genti.domain.repository.CreateRepository
import javax.inject.Inject

class CreateRepositoryImpl
@Inject
constructor(
    private val createDataSource: CreateDataSource,
) : CreateRepository {
    override suspend fun getS3SingleUrl(request: S3RequestModel): Result<S3PresignedUrlModel> =
        runCatching {
            createDataSource.getSingleS3Url(request.toDto()).response.toModel()
        }

    override suspend fun getS3MultiUrl(request: List<S3RequestModel>): Result<List<S3PresignedUrlModel>> =
        runCatching {
            createDataSource.getMultiS3Url(request.map { it.toDto() }).response.map { it.toModel() }
        }

    override suspend fun postToCreate(request: CreateRequestModel): Result<Boolean> =
        runCatching {
            createDataSource.postToCreate(request.toDto()).response
        }

    override suspend fun postToCreateOne(request: CreateRequestModel): Result<Boolean> =
        runCatching {
            createDataSource.postToCreateOne(request.toDto()).response
        }

    override suspend fun postToCreateTwo(request: CreateTwoRequestModel): Result<Boolean> =
        runCatching {
            createDataSource.postToCreateTwo(request.toDto()).response
        }

    override suspend fun postToVerify(request: KeyRequestModel): Result<Boolean> =
        runCatching {
            createDataSource.postToVerify(request.toDto()).response
        }

    override suspend fun getPromptExample(type: String): Result<List<PromptExampleModel>> =
        runCatching {
            createDataSource.getPromptExample(type).response.map { it.toModel() }
        }

    override suspend fun postToValidatePurchase(request: PurchaseValidRequestModel): Result<Boolean> =
        runCatching {
            createDataSource.postToValidatePurchase(request.toDto()).response
        }
}

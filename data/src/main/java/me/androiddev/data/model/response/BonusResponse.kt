package me.androiddev.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import me.androiddev.data.model.BonusModel
import me.androiddev.data.remote.DomainMapper
import java.io.Serializable

data class BonusResponse(
    @Expose
    @SerializedName("data")
    val data: BonusDataResponse,
    @Expose @SerializedName("resultOperation")
    val apiResult: ApiResult
) : Serializable

data class BonusDataResponse(
    @Expose
    @SerializedName("typeBonusName")
    val name: String?,
    @Expose @SerializedName("currentQuantity")
    val currentQuantity: Double?,
    @Expose @SerializedName("forBurningQuantity")
    val burningQuantity: Double?,
    @Expose @SerializedName("dateBurning")
    val dateBurning: String?
) : Serializable, DomainMapper<BonusModel> {
    override fun mapToDomainModel(): BonusModel = BonusModel(
        name = name ?: "",
        currentQuantity = currentQuantity?.toInt().toString(),
        burningQuantity = burningQuantity?.toInt().toString(),
        date = dateBurning ?: ""
    )
}

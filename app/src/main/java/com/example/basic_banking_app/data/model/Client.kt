package com.example.basic_banking_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "client_table")
@Parcelize
data class Client (
    val name: String,
    val email: String,
    val phoneNumber: String,
    var balance: Double,
    @PrimaryKey(autoGenerate = true) val client_id: Int  = 0,
): Parcelable
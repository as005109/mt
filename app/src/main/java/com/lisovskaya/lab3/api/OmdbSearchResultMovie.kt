package com.lisovskaya.lab3.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_result_movie")
data class OmdbSearchResultMovie (
    @SerializedName("Title")
    @ColumnInfo(name = "title")
    val title: String,

    @SerializedName("Year")
    @ColumnInfo(name = "year")
    val year: String,

    @PrimaryKey
    @SerializedName("imdbID")
    @ColumnInfo(name = "imdbId")
    val imdbId: String,

    @SerializedName("Type")
    @ColumnInfo(name = "type")
    val type: String,

    @SerializedName("Poster")
    @ColumnInfo(name = "posterUrl")
    val posterUrl: String
)
/*
{
"Title":"Beta Test",
"Year":"2016",
"imdbID":"tt4244162",
"Type":"movie",
"Poster":"https://m.media-amazon.com/images/M/MV5BODdlMjU0MDYtMWQ1NC00YjFjLTgxMDQtNDYxNTg2ZjJjZDFiXkEyXkFqcGdeQXVyMTU2NTcxNDg@._V1_SX300.jpg"
},
 */
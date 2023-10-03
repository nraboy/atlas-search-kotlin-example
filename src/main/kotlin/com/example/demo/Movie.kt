package com.example.demo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("movies")
data class Movie(
    @Id
    val id: ObjectId = ObjectId(),
    val title: String = "",
    val plot: String = "",
)
package com.example.demo

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Projections
import com.mongodb.client.model.search.SearchOperator
import com.mongodb.client.model.search.SearchPath
import org.bson.Document
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(var movieRepository: MovieRepository, var mongoOperations: MongoOperations) {

    @GetMapping("/movies")
    fun findAll(): ResponseEntity<List<Movie>> {
        val movies = movieRepository.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search")
    fun searchMovies(@RequestParam("q") query: String): ResponseEntity<List<Document>> {
        val collection = mongoOperations.getCollection("movies")
        val results = collection.aggregate(
            listOf(
                Aggregates.search(
                    SearchOperator.text(SearchPath.fieldPath("title"), query)
                ),
                Aggregates.project(
                    Projections.fields(
                        Projections.include("title"),
                        Projections.metaSearchScore("score")
                    )
                ),
                Aggregates.limit(4)
            )
        ).toList()
        return ResponseEntity.ok(results)
    }
}
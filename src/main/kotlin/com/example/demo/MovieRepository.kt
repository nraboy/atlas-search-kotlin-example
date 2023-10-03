package com.example.demo

import org.springframework.data.mongodb.repository.MongoRepository

interface MovieRepository : MongoRepository<Movie, String> {}
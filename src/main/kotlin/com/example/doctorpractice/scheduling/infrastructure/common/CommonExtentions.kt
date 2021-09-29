package com.example.doctorpractice.scheduling.infrastructure.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Any.logger(): Logger = LoggerFactory.getLogger(javaClass)

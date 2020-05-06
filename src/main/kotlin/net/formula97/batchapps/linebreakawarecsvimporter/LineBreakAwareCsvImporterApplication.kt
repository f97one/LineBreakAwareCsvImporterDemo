package net.formula97.batchapps.linebreakawarecsvimporter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LineBreakAwareCsvImporterApplication

fun main(args: Array<String>) {
    runApplication<LineBreakAwareCsvImporterApplication>(*args)
}

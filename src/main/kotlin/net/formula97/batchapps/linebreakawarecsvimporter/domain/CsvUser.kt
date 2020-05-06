package net.formula97.batchapps.linebreakawarecsvimporter.domain

import com.opencsv.bean.CsvBindByPosition

class CsvUser() {
    @CsvBindByPosition(position = 1, required = true)
    var username: String = ""

    @CsvBindByPosition(position = 2, required = false)
    var description: String? = null

    constructor(username: String, description: String?) : this() {
        this.username = username
        this.description = description
    }
}
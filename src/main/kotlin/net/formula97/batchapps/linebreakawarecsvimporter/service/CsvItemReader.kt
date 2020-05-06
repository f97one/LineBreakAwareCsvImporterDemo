package net.formula97.batchapps.linebreakawarecsvimporter.service

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import org.springframework.batch.item.ReaderNotOpenException
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.DefaultFieldSet
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.io.Resource
import org.springframework.util.Assert
import java.io.FileReader
import java.nio.charset.Charset

class CsvItemReader<T>() : AbstractItemCountingItemStreamItemReader<T>(),
        ResourceAwareItemReaderItemStream<T>, InitializingBean {

    var charset: Charset = Charset.defaultCharset()
    var linesToSkip: Int = 0;
    var delimiter: Char = ','
    var quotedChar: Char = '"'
    var escapeChar: Char = '"'
    var lineCount: Long = 0
        private set

    private lateinit var resourceToRead: Resource
    private lateinit var headers: Array<String>
    private lateinit var fieldSetMapper: FieldSetMapper<T>

    private var noInput: Boolean = false
    private lateinit var csvReader: CSVReader

    init {
        setName(this.javaClass.simpleName)
    }

    override fun doOpen() {
        Assert.notNull(resourceToRead, "Resource to read is required")

        noInput = true
        if (!resourceToRead.exists()) {
            throw IllegalStateException("Input resource does not exist : $resourceToRead")
        }
        if (!resourceToRead.isReadable) {
            throw IllegalStateException("Input resource must be readable : $resourceToRead")
        }

        // CSVParser
        val csvParserBuilder = CSVParserBuilder().withSeparator(delimiter)
                .withQuoteChar(quotedChar)
                .withStrictQuotes(true)
        // 同じ値を書き込むと怒られるので、不一致の場合のみにする
        if (quotedChar != escapeChar) {
            csvParserBuilder.withEscapeChar(escapeChar)
        }

        csvReader = CSVReaderBuilder(FileReader(resourceToRead.file, charset))
                .withCSVParser(csvParserBuilder.build())
                .withSkipLines(linesToSkip)
                .build()

        noInput = false
    }

    override fun doRead(): T? {
        if (noInput) {
            return null
        }

        if (csvReader == null) {
            throw ReaderNotOpenException("CSVReader is not initialized")
        }

        val line: Array<out String> = csvReader.readNext() ?: return null
        lineCount++

        val fs: FieldSet = DefaultFieldSet(line, headers)
        return fieldSetMapper.mapFieldSet(fs)
    }

    override fun doClose() {
        lineCount = 0
        if (csvReader != null) {
            csvReader.close()
        }
    }

    override fun setResource(resource: Resource) {
        this.resourceToRead = resource
    }

    override fun afterPropertiesSet() {
        Assert.notNull(this.headers, "header is required")
        Assert.notNull(this.fieldSetMapper, "FieldSetMapper is required")
    }

    fun setHeaders(headers: Array<String>) {
        this.headers = headers
    }

    fun setFieldSetMapper(fieldSetMapper: FieldSetMapper<T>) {
        this.fieldSetMapper = fieldSetMapper
    }
}
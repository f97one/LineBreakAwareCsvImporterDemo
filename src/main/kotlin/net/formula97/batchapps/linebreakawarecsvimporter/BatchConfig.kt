package net.formula97.batchapps.linebreakawarecsvimporter

import net.formula97.batchapps.linebreakawarecsvimporter.domain.AppUser
import net.formula97.batchapps.linebreakawarecsvimporter.domain.CsvUser
import net.formula97.batchapps.linebreakawarecsvimporter.service.CsvImporterProcessor
import net.formula97.batchapps.linebreakawarecsvimporter.service.CsvItemReader
import net.formula97.batchapps.linebreakawarecsvimporter.service.CsvItemWriter
import net.formula97.batchapps.linebreakawarecsvimporter.service.CsvUserMapper
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
@EnableBatchProcessing
class BatchConfig(
        private val jobBuilderFactory: JobBuilderFactory,
        private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun csvItemReader(csvUserMapper: CsvUserMapper): ItemReader<CsvUser> {
        val reader = CsvItemReader<CsvUser>()
        reader.setResource(ClassPathResource("/csv/userdata.csv"))
        reader.setFieldSetMapper(csvUserMapper)
        reader.linesToSkip = 1

        val headers = arrayOf("username", "description")
        reader.setHeaders(headers)

        return reader
    }

    @Bean
    fun step1(csvItemReader: ItemReader<CsvUser>, csvItemWriter: CsvItemWriter, csvImporterProcessor: CsvImporterProcessor): Step {
        return stepBuilderFactory.get("csvItemReaderStep")
                .chunk<CsvUser, AppUser>(10)
                .reader(csvItemReader)
                .processor(csvImporterProcessor)
                .writer(csvItemWriter)
                .build()
    }

    @Bean
    fun job1(step1: Step): Job {
        return jobBuilderFactory.get("csvItemImporterJob")
                .incrementer(RunIdIncrementer())
                .start(step1)
                .build()
    }
}
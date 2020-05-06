package net.formula97.batchapps.linebreakawarecsvimporter.service

import net.formula97.batchapps.linebreakawarecsvimporter.domain.AppUser
import net.formula97.batchapps.linebreakawarecsvimporter.repository.AppUserRepository
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class CsvItemWriter(private val appUserRepository: AppUserRepository): ItemWriter<AppUser> {
    override fun write(items: MutableList<out AppUser>) {
        appUserRepository.saveAll(items)
    }
}
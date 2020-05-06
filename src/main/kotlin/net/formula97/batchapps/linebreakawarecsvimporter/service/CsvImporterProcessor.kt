package net.formula97.batchapps.linebreakawarecsvimporter.service

import net.formula97.batchapps.linebreakawarecsvimporter.domain.AppUser
import net.formula97.batchapps.linebreakawarecsvimporter.domain.CsvUser
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class CsvImporterProcessor : ItemProcessor<CsvUser, AppUser> {
    override fun process(item: CsvUser): AppUser? {
        return AppUser(item.username, item.description)
    }
}
package net.formula97.batchapps.linebreakawarecsvimporter.service

import net.formula97.batchapps.linebreakawarecsvimporter.domain.CsvUser
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.stereotype.Component

@Component
class CsvUserMapper: FieldSetMapper<CsvUser> {
    override fun mapFieldSet(fieldSet: FieldSet): CsvUser {
        return CsvUser(fieldSet.readString(0), fieldSet.readString(1))
    }
}
package net.formula97.batchapps.linebreakawarecsvimporter.repository

import net.formula97.batchapps.linebreakawarecsvimporter.domain.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository: JpaRepository<AppUser, Int> {
}
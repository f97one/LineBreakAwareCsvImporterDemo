package net.formula97.batchapps.linebreakawarecsvimporter.domain

import javax.persistence.*

@Entity(name = "app_user")
class AppUser() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    var userId: Int? = null
    @Column(name = "username", nullable = false, length = 32)
    var username: String = ""
    @Column(name = "description", length = 1024)
    var description: String? = null

    constructor(username: String, description: String?): this() {
        this.username = username
        this.description = description
    }
}
package info.ognibeni.memberservice.service

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.runtime.annotations.RegisterForReflection
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.ws.rs.NotFoundException

@Entity(name = "members")
@RegisterForReflection
class MemberEntity : PanacheEntity() {
    @Column(nullable = false, unique = true)
    lateinit var uuid: UUID

    @Column(nullable = false)
    lateinit var firstname: String

    @Column(nullable = false)
    lateinit var lastname: String

    companion object : PanacheCompanion<MemberEntity> {
        fun findByUuid(uuid: UUID) = find("uuid", uuid).firstResult() ?: throw NotFoundException()
        fun deleteByUuid(uuid: UUID) = delete("uuid", uuid)
    }
}


//
//@RegisterForReflection
//@Entity(name = "members")
//data class MemberEntity(
//	@Column(nullable = false, unique = true, name = "uuid")
//	@field:JsonProperty("uuid")
//	val uuid: UUID = UUID.randomUUID(),
//
//	@Column(nullable = false, name = "firstname")
//	@field:JsonProperty("firstname")
//	val firstname: String = "",
//
//	@Column(nullable = false, name = "lastname")
//	@field:JsonProperty("lastname")
//	val lastname: String = ""
//
//) : PanacheEntity() {
//	companion object : PanacheCompanion<MemberEntity> {
//		fun findByUuid(uuid: UUID) = find("uuid", uuid).firstResult() ?: throw NotFoundException()
//        fun deleteByUuid(uuid: UUID) = delete("uuid", uuid)
//	}
//}

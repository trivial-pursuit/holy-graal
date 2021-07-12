package info.ognibeni.memberservice.service

import info.ognibeni.memberservice.rest.Member
import java.util.UUID
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class MemberService {

	fun findAllMembers(): List<Member> =
		MemberEntity.findAll().list().map { toMember(it) }

	fun findSpecificMember(uuid: UUID): Member =
		toMember(MemberEntity.findByUuid(uuid))

	@Transactional
	fun saveNewMember(member: Member): Member {
		toMemberEntity(member).persist()
		return member
	}

	@Transactional
	fun deleteMember(uuid: UUID) {
		MemberEntity.deleteByUuid(uuid)
	}


	private fun toMember(memberEntity: MemberEntity): Member =
		Member(memberEntity.uuid, memberEntity.firstname, memberEntity.lastname)

	private fun toMemberEntity(member: Member): MemberEntity =
		MemberEntity().apply {
			uuid = member.uuid
			firstname = member.firstName
			lastname = member.lastName
		}
}
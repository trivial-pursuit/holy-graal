package info.ognibeni.memberservice.rest

import java.util.UUID

data class Member(val uuid: UUID, val firstName: String, val lastName: String)

package info.ognibeni.memberservice.rest

import com.fasterxml.jackson.databind.ObjectMapper
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class MemberServiceExceptionMapper : ExceptionMapper<Exception> {

	@Inject
	@field: Default
	lateinit var objectMapper: ObjectMapper

	override fun toResponse(exception: Exception): Response {
		val code = if (exception is WebApplicationException) {
			exception.response.status
		} else {
			500
		}

		val exceptionJson = objectMapper.createObjectNode()
		exceptionJson.put("exceptionType", exception.javaClass.name)
		exceptionJson.put("code", code)

		if (exception.message != null) {
			exceptionJson.put("error", exception.message)
		}

		return Response.status(code)
			.entity(exceptionJson)
			.build()
	}
}

package info.ognibeni.memberservice.rest

import info.ognibeni.memberservice.service.MemberService
import org.jboss.resteasy.annotations.jaxrs.PathParam
import java.util.UUID
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/members")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class MemberResource(private val memberService: MemberService) {

    @GET
    fun getAllMembers(): List<Member> =
        memberService.findAllMembers()

    @GET
    @Path("{uuid}")
    fun getMember(@PathParam uuid: UUID): Member =
        memberService.findSpecificMember(uuid)

    @POST
    fun saveMember(member: Member): Member =
        memberService.saveNewMember(member)

    @DELETE
    @Path("{uuid}")
    fun deleteMember(@PathParam uuid: UUID): Response {
        memberService.deleteMember(uuid)
        return Response.status(204).build()
    }
}
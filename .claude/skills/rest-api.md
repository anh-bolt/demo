# Axon Ivy REST API Development

## REST Service Class
```java
@Path("/customers")
public class CustomerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Customer> customers = Ivy.persistence()
            .get("main")
            .findAll(Customer.class);
        return Response.ok(customers).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        Customer customer = Ivy.persistence()
            .get("main")
            .find(Customer.class, id);
        if (customer == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(customer).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Customer customer) {
        Ivy.persistence().get("main").save(customer);
        return Response.status(Status.CREATED)
            .entity(customer).build();
    }
}
```

## Authentication
```java
// Get current user
IUser user = Ivy.session().getSessionUser();

// Check permission
if (!user.has(IPermission.of("CustomerAdmin"))) {
    return Response.status(Status.FORBIDDEN).build();
}
```

## OpenAPI Documentation
- Add @Operation, @Parameter annotations
- Access Swagger UI: /api-docs
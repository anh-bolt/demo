# Axon Ivy REST API Development

## Overview
Axon Ivy uses JAX-RS (Jersey) for REST service development. REST resources are Java classes in `src/` with JAX-RS annotations, automatically published at `/<appName>/api/`. OpenAPI specs are auto-generated at `/<appName>/api/openapi.json`.

---

## Creating REST Services

### Basic REST Resource
Place Java classes in `src/` with JAX-RS annotations:

```java
@Path("person")
public class PersonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person get() {
        Person p = new Person();
        p.setFirstname("Renato");
        p.setLastname("Stalder");
        return p;
    }
}
```

### Full CRUD Example
```java
@Path("/customers")
public class CustomerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Customer> customers = Ivy.repo().search(Customer.class).execute().getAll();
        return Response.ok(customers).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        Customer customer = Ivy.repo().find(id, Customer.class);
        if (customer == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(customer).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Customer customer) {
        Ivy.repo().save(customer);
        return Response.status(Status.CREATED).entity(customer).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Customer customer) {
        Customer existing = Ivy.repo().find(id, Customer.class);
        if (existing == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        Ivy.repo().save(customer);
        return Response.ok(customer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Customer existing = Ivy.repo().find(id, Customer.class);
        if (existing == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        Ivy.repo().delete(existing);
        return Response.noContent().build();
    }
}
```

### JAX-RS Annotations Reference
| Annotation | Purpose |
|---|---|
| `@Path` | Define resource endpoint path |
| `@GET`, `@POST`, `@PUT`, `@DELETE` | HTTP method declaration |
| `@Produces(MediaType.APPLICATION_JSON)` | Response content type |
| `@Consumes(MediaType.APPLICATION_JSON)` | Request content type |
| `@PathParam("id")` | Extract path parameter |
| `@QueryParam("name")` | Extract query parameter |
| `@HeaderParam("X-Custom")` | Extract header value |

---

## Security

### Authentication Annotations
REST APIs are protected with **Basic authentication** by default. Use JAX-RS security annotations:

```java
@Path("secure")
public class SecureResource {

    @GET
    @PermitAll  // No authentication required
    public Response publicEndpoint() {
        return Response.ok("public").build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("Administrator")  // Requires role
    public Response adminOnly() {
        return Response.ok("admin data").build();
    }

    @GET
    @Path("/restricted")
    @DenyAll  // Always denied
    public Response restricted() {
        return Response.status(Status.FORBIDDEN).build();
    }
}
```

| Annotation | Effect |
|---|---|
| `@PermitAll` | Unauthenticated access allowed |
| `@RolesAllowed("RoleName")` | Requires authentication + specific role |
| `@DenyAll` | Completely denies access |

### Programmatic Security Check
```java
IUser user = Ivy.session().getSessionUser();
if (!user.has(IPermission.of("CustomerAdmin"))) {
    return Response.status(Status.FORBIDDEN).build();
}
```

### CSRF Protection
For `PUT`, `POST`, `DELETE` requests, callers must provide the HTTP header `X-Requested-By` with any value. Omitting it returns HTTP 400.
- Enabled by default
- Configurable via `REST.Servlet.CSRF.Protection` in `ivy.yaml`

---

## OpenAPI Documentation

### Automatic Generation
OpenAPI specs auto-generated at `/<appName>/api/openapi.json`. Access the **API Browser** at `/system/api-browser` to inspect and test services.

### Swagger Annotations
Enhance auto-generated docs with annotations from `io.swagger.v3.oas.annotations`:

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("zip")
@Tag(name = "CRM")
public class OpenApiResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Finds customers in the CRM by ZIP code")
    @ApiResponse(responseCode = "200", description = "Matching persons")
    @ApiResponse(responseCode = "404", description = "No persons found")
    public List<Person> findPersonByZip(
        @Parameter(required = true, example = "CH-6300",
            description = "A ZIP with prefixed country code")
        @QueryParam("zip") String zip,
        @Parameter(example = "active, inactive, suspended")
        @QueryParam("status") String status
    ) {
        return UserRepo.findByZip(zip, status);
    }
}
```

| Annotation | Purpose |
|---|---|
| `@Tag(name = "...")` | Group related services |
| `@Operation(description = "...")` | Describe endpoint |
| `@ApiResponse(responseCode, description)` | Document HTTP responses |
| `@Parameter(required, example, description)` | Document parameters |

---

## REST Client (Calling External Services)

### Configuration
REST clients are configured in the Axon Ivy Designer under REST Clients configuration:
- **UUID**: Immutable unique identifier
- **Name**: Referenced in code via `Ivy.rest().client("name")`
- **Base URI**: Supports template placeholders, e.g., `https://api.example.com/{version}`

### Authentication Options
- HTTP Basic authentication
- HTTP Digest authentication
- NTLM (Windows) authentication

### Using Ivy.rest() API
Call external REST services programmatically without the REST Client Activity:

```java
// Simple GET
javax.ws.rs.client.WebTarget target = Ivy.rest().client("myService");
Response response = target.path("users").request().get();

// With path template resolution
Ivy.rest().client("twitter")
    .resolveTemplate("version", "1.1")
    .path("statuses/show.json")
    .queryParam("id", "210462857140252672")
    .request()
    .get();

// POST with JSON body
Response response = Ivy.rest().client("myService")
    .path("users")
    .request(MediaType.APPLICATION_JSON)
    .post(Entity.json(newUser));
```

### Key Properties
| Property | Purpose |
|---|---|
| `Deserialization.FAIL_ON_UNKNOWN_PROPERTIES=false` | Allow partial JSON mapping |
| `SSL.keyAlias` | SSL client authentication key alias |
| `PATH.api.version` | Global path template resolution |

### Dynamic Configuration
Use Ivy variables for environment-specific values:
```
URI: https://${ivy.var.apiHost}/api
Property: appId=${ivy.var.cloudAppId}
```

### OpenAPI Client Generation
Axon Ivy can generate typed Java client classes from OpenAPI 3.0 and Swagger 2.0 descriptors.

---

## Tips & Best Practices

- **JSON pretty printing**: Add `?pretty` query parameter for formatted output
- **HTTPS**: Always use HTTPS for Basic authentication endpoints
- **Error responses**: Return appropriate HTTP status codes with `Response.status(...)`
- **Content negotiation**: Always declare `@Produces` and `@Consumes`
- **Ivy.repo()**: Use `BusinessDataRepository` for data persistence in REST endpoints
- **ConnectivityDemos**: Reference project with comprehensive REST examples

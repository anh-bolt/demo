# IvyScript Development Skill

## IvyScript Basics
- Auto-initialization: Strings → "", Numbers → 0, Lists → empty
- Use `.#` operator to suppress auto-creation: `if (in.#customer == null)`
- Use `is initialized` for null checks

## Common Patterns

### Database Query
```ivyscript
import ch.ivyteam.ivy.persistence.*;

IQueryResult<Customer> result = ivy.persistence.get("myPersistenceUnit")
    .createQuery("SELECT c FROM Customer c WHERE c.status = :status", Customer.class)
    .setParameter("status", "ACTIVE")
    .getResultList();
```

### REST Client Call
```ivyscript
import ch.ivyteam.ivy.rest.client.*;

var client = ivy.rest.client("myRestClient");
var response = client.path("/api/customers/{id}")
    .resolveTemplate("id", customerId)
    .request().get();
```

### CMS Access
```ivyscript
String content = ivy.cms.co("/content/emails/welcomeEmail");
```

### Task Creation
```ivyscript
ivy.wf.createTask()
    .name("Review Document")
    .description("Please review the uploaded document")
    .activator("role:Reviewer")
    .start();
```

### Logging
```ivyscript
ivy.log.info("Processing order: {0}", orderId);
ivy.log.error("Error occurred", exception);
```
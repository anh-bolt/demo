# Rule: Axon Ivy Coding Standards

**Scope:** Every Java file, process (`.p.json`), dialog (`.xhtml`), CMS entry, config, and name in this Axon Ivy project.
**Source:** Axon Ivy Coding Guidelines (based on Google Java Style + Oracle Java Conventions + Axon Ivy specifics).

> These are MUST-follow rules. Apply them automatically when writing or reviewing code — do not wait to be asked.

---

## 1. Java Source File Structure

- **One** top-level public class/interface per `.java` file. Filename matches the type name.
- Package by **role**: `service`, `persistence`, `rest`, `ui`, `model`, `dto`.
- **No wildcard imports** (`import java.util.*;` forbidden).
- Required member order inside a class:
  1. Package → 2. Imports → 3. Class annotations → 4. Class declaration
  5. Static fields → static methods
  6. Instance fields → 7. Constructors
  8. Public → protected → package-private → private methods
  9. Inner classes / enums last

## 2. Formatting

| Rule | Value |
|------|-------|
| Indent | **Spaces only**, never tabs |
| Line length | ≤ 120 characters |
| Statements | One per line |
| Braces | K&R (same line as control statement) |
| Single-statement blocks | **Always brace them** |

```java
// REQUIRED
if (condition) {
  doSomething();
}
```

## 3. Javadoc and Comments

- Javadoc on: public APIs, cross-module utilities, complex business logic, non-trivial public methods.
- Inline comments: **sparingly**, explain **why** not **what**.
- Do not reference tickets, tasks, or callers in comments — that belongs in the PR description.

```java
/**
 * Calculates the price after discount.
 *
 * @param amount the original amount in the default currency
 * @param customer the customer to evaluate discount rules for
 * @return the discounted amount, never negative
 */
BigDecimal calculateDiscountedPrice(BigDecimal amount, Customer customer) { ... }
```

## 4. Enums and Bean Validation

- Prefer **enums** over magic strings or int constants for conceptual sets.
- Use `jakarta.validation` annotations on DTOs and entities.
- Validate **early** at process boundaries (REST controllers, process starts).

```java
public class RegistrationForm {
  @NotNull @Email
  private String email;

  @NotNull @Size(min = 8, max = 64)
  private String password;
}
```

## 5. Naming Conventions (Axon Ivy)

### General
- **Descriptive beats short.** English for every identifier.
- Avoid abbreviations unless universal (`id`, `URL`, `HTTP`).

### Packages — reverse-domain, feature-first then technical layer
```
com.company.myapp.customer
  ├─ api
  ├─ service
  ├─ persistence
  └─ ui
```

### Processes
- **Verb-noun**: `CreateCustomer`, `ApproveInvoice`, `HandleSupportTicket`.
- Sub-processes prefixed with feature: `Customer_Create`, `Customer_UpdateAddress`.

### Dialogs / UI
- Reflect the use case: `customerEditDialog`, `invoiceApprovalView`.
- **Never** `Dialog1`, `ScreenX`, `View2`.

### Global variables and config — grouped namespaces
```
global.config.customer.maxRetries
global.config.notification.emailSender
```

## 6. Logging

### Log levels

| Level | Use for |
|-------|---------|
| FATAL | System cannot continue |
| ERROR | Operation failed, needs investigation |
| WARN  | Unexpected, but system continues |
| INFO  | Business events, important state changes |
| DEBUG | Technical troubleshooting detail |

### Rules
- **NEVER** log passwords, secrets, full credit-card numbers, or PII.
- Use **SLF4J parameterized logging** (`{}`), never string concatenation.
- Include correlation / process instance IDs.
- Prefer structured logging (key/value) where available.

### Agents / scheduled jobs — log
- Start and end of job
- Count of processed items
- Summary of failures

```java
LOG.info("Agent {} started, executionId={}", agentName, executionId);
LOG.info("Processed {} orders, {} failed", processed, failed);
```

### REST / Web services
- **INFO** on incoming requests (sanitized — no sensitive payloads).
- **ERROR** on 5xx / 4xx server errors. Include endpoint, technical reason, correlation ID.

## 7. CMS Organization

### Folder structure — by domain + purpose
```
/cms
  /messages
  /errors
  /ui
  /templates
  /email
```
- **Never mix** technical error messages with user-facing UI texts.

### Access via enum, not ad-hoc strings
```java
public enum CmsMessageKey {
  ERROR_GENERIC("errors.generic"),
  ERROR_CUSTOMER_NOT_FOUND("errors.customer.notFound"),
  UI_SAVE_SUCCESS("ui.save.success");

  private final String key;
  CmsMessageKey(String key) { this.key = key; }
  public String getKey() { return key; }
}
```
```java
String msg = cmsService.getMessage(CmsMessageKey.ERROR_CUSTOMER_NOT_FOUND);
```

### Localization
- Keep **English as reference**; add new keys for **all supported locales**.
- **No concatenated messages** — use placeholders:
```
errors.customer.notFound = Customer with ID {0} not found.
```

## 8. Exception and Error Handling

- Exceptions are for **exceptional conditions**, never control flow.
- Distinguish **technical** vs **business** exceptions.
- **Always preserve the root cause**: `throw new X("...", cause);`.

### Stable error codes mapped to CMS
```java
public enum ErrorCode {
  TECHNICAL_GENERAL("T-0001"),
  BUSINESS_VALIDATION("B-1000"),
  CUSTOMER_NOT_FOUND("B-1001");
}
```

### BPM error handling
- Surface **user-friendly** messages in process UIs.
- Log **technical details** backend-only; show safe info to end users.
- Use the dedicated BPM error dialog widget where available (accepts: error code, user message, optional details).

## 9. Layered Process Modeling

Three layers — **never** skip them. Never call low-level services directly from the business layer.

| Layer | Contains | Audience |
|-------|----------|----------|
| **Business** | Coarse steps: *Validate Order*, *Calculate Price* | Business users |
| **Detail**   | Sub-processes per business step: *Check Customer Credit* | Mixed |
| **Technical**| Concrete service / DB / REST calls; error handling + logging live here | Developers |

Name processes by **intent**, not implementation:
```
GOOD: ApproveLoan
BAD:  CallLoanService, UpdateLoanStatusDB
```

## 10. SonarQube / Code Quality

- Use the **shared Axon Ivy quality profile** when available.
- Enable Axon-specific rulesets: BPM misuse, CMS access patterns, logging/exception patterns, layered architecture constraints.
- Review Sonar findings regularly; keep technical debt within agreed limits.

---

## Pre-submission Checklist

Before declaring any change complete, verify:

- [ ] One public type per file; filename matches type
- [ ] No wildcard imports
- [ ] Spaces, ≤120 cols, braces on single-line blocks
- [ ] Javadoc on public APIs; comments explain *why*
- [ ] Verb-noun process names; feature-prefixed sub-processes
- [ ] No `Dialog1`, `tmp`, `data2` — names reflect intent
- [ ] No secrets / PII in logs; parameterized SLF4J calls
- [ ] CMS keys via enum, not string literals
- [ ] Exceptions preserve cause; business vs technical distinguished
- [ ] Low-level calls in technical layer only, not business layer

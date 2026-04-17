---
name: axon-ivy-java-data
description: Rules and patterns for Java model classes, enums, DTOs, and persistence (Ivy.repo() or JPA/SQL) in Axon Ivy projects.
---

## Coding Standards (MUST follow)

All Java models, enums, and DTOs MUST comply with [.claude/rules/axon-ivy-coding.md](../../rules/axon-ivy-coding.md):

- **One top-level public type per file**; filename matches the type.
- **No wildcard imports** (`java.util.*` forbidden).
- **Role-based packages** — `model/`, `dto/`, `repository/`, `service/`, `rest/`, `ui/`.
- **Spaces, ≤120 cols, K&R braces, always brace single-statement blocks.**
- **Javadoc** on public APIs and non-trivial public methods; explain *why*, not *what*.
- **Enums** over magic strings or int constants for conceptual sets; UPPER_SNAKE_CASE values.
- **Bean validation** (`jakarta.validation`) on DTOs/entities; validate at process boundaries.

## Use Together With

- `axon-ivy-repository` - For persistence with Ivy.repo()

## File Locations

| Type | Location |
|------|----------|
| Model Class | `src/package/model/` |
| Enum | `src/package/model/` |
| DTO | `src/package/dto/` |

## Java Model Pattern

**File:** `src/com/example/customer/model/Customer.java` — one public type per file; role-based package.

```java
package com.example.customer.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Customer aggregate. Immutable identity, mutable descriptive fields.
 *
 * <p>Validated at process boundaries (REST controllers, process starts) via
 * the {@code jakarta.validation} annotations on its fields.
 */
public class Customer {

  private String id;

  @NotBlank
  @Size(max = 120)
  private String name;

  private CustomerStatus status;

  public Customer() {
    this.id = UUID.randomUUID().toString();
    this.status = CustomerStatus.NEW;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CustomerStatus getStatus() {
    return status;
  }

  public void setStatus(CustomerStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Customer[id=" + id + ", status=" + status + "]";
  }
}
```

## Enum Pattern

**File:** `src/package/model/EntityStatus.java`

```java
package package.model;

public enum EntityStatus {
  NEW("New"),
  IN_PROGRESS("In Progress"),
  COMPLETED("Completed"),
  CANCELLED("Cancelled");

  private final String description;

  EntityStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return this == COMPLETED;
  }

  public boolean isCancelled() {
    return this == CANCELLED;
  }

  public boolean isActive() {
    return this != COMPLETED && this != CANCELLED;
  }
}
```

## Model with Builder Pattern

```java
public class Project {

  private String projectId;
  private String name;
  private String description;

  public Project() {
    this.projectId = UUID.randomUUID().toString();
  }

  // Fluent builder methods
  public Project name(String name) {
    this.name = name;
    return this;
  }

  public Project description(String description) {
    this.description = description;
    return this;
  }

  // Standard getters/setters...
}
```

## Model with AI Description

ONLY use if this project depend on the `smart-workflow` project or LangChain4j by checking the `pom.xml`.

For models used with AI/LLM processing:

```java
package package.model;

import dev.langchain4j.model.output.structured.Description;

@Description("Brief description for AI context")
public class Candidate {

  @Description("Unique identifier")
  private String id;

  @Description("Full name of the candidate")
  private String name;

  @Description("List of technical skills")
  private List<String> skills;
}
```

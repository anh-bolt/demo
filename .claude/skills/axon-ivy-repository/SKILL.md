````skill
---
name: axon-ivy-repository
description: Create repository classes for persisting entities. Dispatches between Ivy.repo() (default) and JPA/SQL (advanced) approaches.
---

## Coding Standards (MUST follow)

All repository classes MUST comply with [.claude/rules/axon-ivy-coding.md](../../rules/axon-ivy-coding.md):

- **Package** — `com.<company>.<feature>.repository` (role-based, rule §5).
- **Class name** — `<Entity>Repository`, one public class per file.
- **Javadoc** on every public finder / mutator (explain *why*, not *what*).
- **No wildcard imports**.
- **Logging** — SLF4J parameterized (`LOG.error("Failed to load {}: {}", id, ex.getMessage(), ex)`); never log raw entities that may contain PII or credentials.
- **Exceptions** — preserve the cause, distinguish technical (`TECHNICAL_*`) vs business (`B-*`) errors using the stable `ErrorCode` enum; never swallow.
- **Repositories live in the technical layer** — business processes must not call them directly from the business-layer `.p.json`; they are called from technical-layer Script / `ProgramInterface` nodes only.

## Persistence Decision

When user needs data persistence, choose the approach:

```text
Does the user explicitly request SQL/JPA/database/persistence-utils?
├── NO  → Load `ivy-repo.md` — DEFAULT (simple, no DB config needed)
└── YES → Load `jpa-persistence.md` (requires databases.yaml, persistence.xml, DAOs)
```

**DEFAULT**: Always load `ivy-repo.md` unless the user explicitly asks for:
- SQL database, JPA entities, Hibernate, persistence-utils
- DAO classes, CriteriaQuery, AuditableIdEntity
- databases.yaml, persistence.xml configuration
- An existing project already uses the JPA pattern

## Always Load One

- Default persistence (Ivy Business Data) → Load `ivy-repo.md`
- JPA/SQL persistence (entities, DAOs, services, database config) → Load `jpa-persistence.md`

## After Using This Skill

**MANDATORY**: After creating or modifying any entity or repository using `Ivy.repo()`, run the checklist in `ivy-repo-verify.md` to catch common errors (duplicate key violations, manual ID issues).

## Entity/Model Definition

Both approaches use `axon-ivy-java-data` skill for creating model classes and enums.
````

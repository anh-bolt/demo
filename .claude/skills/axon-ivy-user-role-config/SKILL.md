---
name: axon-ivy-user-role-config
description: Provide format for Axon Ivy users/roles configurations. Use when wokring with Axon Ivy users/roles.
---

## Coding Standards (MUST follow)

Role / user configuration MUST comply with [.claude/rules/axon-ivy-coding.md](../../rules/axon-ivy-coding.md):

- **Role names** — PascalCase, descriptive (`LineManagers`, `DepartmentManagers`); never `Role1`, `Test`, `Admins2`.
- **Group with hierarchy** — use `parent:` to express containment, mirroring the grouped-namespace principle (rule §5.5).
- **Test users** — synthetic names and emails only; never copy production identities or real passwords. Passwords in `users.yaml` must be clearly disposable (e.g., `Password1`).
- **No PII** in `fullName` or `email` unless it is a fabricated test identity.

## Configuration Files

`config/roles.yaml` : Role hierarchy and permissions
`config/users.yaml` : User definitions

## roles.yaml

```yaml
Roles:
  # Parent roles
  Everybody:
  HR:
    parent: Everybody
  Manager:
    parent: Everybody

  # Child roles
  Recruiter:
    parent: HR
  ProjectManager:
    parent: Manager
```

## users.yaml

```yaml
Users:
  pm_user:
    fullName: Project Manager
    password: pm_user
    email: pm@example.com
    roles:
      - HR
      - ProjectManager
```

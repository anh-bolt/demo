# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an **Axon Ivy Workflow Application** (`leave-request`) for the **HR domain** (REQ-PRC-001), replacing informal email/verbal leave requests with a structured, trackable digital workflow. Employees submit leave requests through an HTML Dialog form, which then go through a **two-level approval chain** (Line Manager → Department Manager) with automatic email notifications at each decision point.

**Business Goals:**
- All leave requests tracked digitally with full audit trail
- Approval decisions recorded with mandatory comments (on reject)
- Employees notified via email within minutes of any decision

**Process Flow:** Employee submits → Line Manager reviews (2-day SLA) → Department Manager reviews (2-day SLA) → APPROVED or REJECTED

**Supported Leave Types:** Annual, Sick, Unpaid, Parental, Bereavement, Other

Built on Axon Ivy 14.0 with Maven, packaged as an IAR (Ivy Archive).

## Build Commands

```bash
# Build project
mvn clean install -f leave-request/pom.xml

# Run tests
mvn test -f leave-request/pom.xml
```

After modifying data classes (`.d.json`), rebuild to regenerate Java source classes in `src_dataClasses/`.

## Architecture

### Process Flow
```
Employee submits → Line Manager reviews → Department Manager reviews → APPROVED/REJECTED
```

**States:** SUBMITTED → IN_LINE_MANAGER_REVIEW → IN_DEPT_MANAGER_REVIEW → APPROVED | REJECTED

### Key Directories (under `leave-request/`)

| Directory | Purpose |
|-----------|---------|
| `dataclasses/` | Data class definitions (`.d.json`) |
| `processes/` | Process definitions (`.p.json`) |
| `src_hd/` | HTML Dialog forms (`.xhtml` + logic process) |
| `src/` | Custom Java logic |
| `src_dataClasses/` | Auto-generated Java from `.d.json` (do not edit) |
| `config/` | Roles, users, variables, custom fields (YAML) |
| `cms/` | Content Management System (multilingual labels, email templates) |
| `documents/requirements/` | Requirements specification |
| `.claude/skills/` | 17 AI skills for guided implementation |

### Naming Conventions

- **Package:** `com.axonivy.leaveRequest`
- **Process element IDs:** Sequential from `f0` (f0, f1, f2, ...); connection IDs must not conflict with element IDs
- **Roles:** `LeaveRequest/Employees`, `LeaveRequest/LineManagers`, `LeaveRequest/DepartmentManagers`
- **Duration format:** `new Duration("2D")` (capital letters: D=days, H=hours)

### Test Users (all password: `Password1`)

| User | Role | Email |
|------|------|-------|
| `john.employee` | Employee | john.employee@company.com |
| `mary.manager` | Line Manager | mary.manager@company.com |
| `david.director` | Department Manager | david.director@company.com |

## AI Skills

The project includes specialized Claude skills in `.claude/skills/` that must be used in sequence:

1. **axon-ivy-workflow-guide** — Start here for new workflows
2. **axon-ivy-data** — Data class definitions
3. **axon-ivy-java-data** — Java models, enums, DTOs
4. **axon-ivy-process** → then **axon-ivy-process-verify** — Process definitions (verification is mandatory)
5. **axon-ivy-html** — HTML dialogs with PrimeFaces/PrimeFlex
6. **axon-ivy-cms** → then **axon-ivy-cms-verify** — CMS entries (verification is mandatory)
7. **axon-ivy-test** — Process, REST, and unit tests

Other skills: `axon-ivy-custom-fields`, `axon-ivy-variable-config`, `axon-ivy-user-role-config`, `axon-ivy-repository`, `axon-ivy-implement-story`, `axon-ivy-verify-story`, `axon-ivy-requirements-creation`, `axon-ivy-smart-workflow`

## Key Reference

- **Full requirements:** [leave-request-requirements.md](leave-request/documents/requirements/leave-request-requirements.md)
- **Data model:** [LeaveRequest.d.json](leave-request/dataclasses/com/axonivy/leaveRequest/LeaveRequest.d.json)
- **Configuration schemas:** `https://json-schema.axonivy.com/14.0-dev/config/`
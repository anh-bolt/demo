# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is **workflow-demos**, an Axon Ivy 14.0.0 demonstration application showcasing BPM (Business Process Management) capabilities: workflow automation, human task management, business data, signals, triggers, and business rules.

- **Artifact:** `com.axonivy.demo:workflow-demos` (packaging: `iar` — Ivy Application aRchive)
- **Java version:** 21
- **Build:** Maven with `com.axonivy.ivy.ci:project-build-plugin`

## Build Commands

```bash
mvn clean install          # Full build producing target/workflow-demos-14.0.0-SNAPSHOT.iar
mvn compile                # Compile only
mvn test                   # Run tests (src_test/)
```

The project requires access to Axon Ivy's Maven repository (`https://maven.axonivy.com`) for parent POM and dependencies.

## Architecture

### Source Layout

| Directory | Purpose |
|---|---|
| `src/` | Hand-written Java code (helpers, business data models, maintenance jobs) |
| `src_dataClasses/` | **Generated** — Java data classes from `dataclasses/*.d.json` definitions. Do not edit directly. |
| `src_generated/` | **Generated** — Other generated sources. Do not edit directly. |
| `src_hd/` | HTML Dialog definitions — XHTML views + per-dialog process/data JSON files |
| `src_test/` | Test sources |
| `processes/` | Workflow process definitions (`.p.json` files) — the core BPM logic |
| `dataclasses/` | Data class schema definitions (`.d.json`) that generate `src_dataClasses/` |
| `config/` | Runtime config: `variables.yaml`, `roles.yaml`, `users.xml`, `persistence.xml` |
| `rules/` | Drools business rules (`.drl` files) |
| `cms/` | Content Management — localized strings (`cms_en.yaml`, `cms_de.yaml`) and templates |
| `webContent/` | Web resources: master layout (`layouts/frame.xhtml`), CSS, images |

### Key Patterns

- **Process-centric architecture:** Workflow logic lives in `.p.json` process definitions under `processes/` and `src_hd/`, not primarily in Java code. Java is used for helpers and data access.
- **HTML Dialogs (src_hd/):** Each dialog is a triplet: XHTML view + `.p.json` process + `.d.json` data class. These are organized by workflow module (AgileBPM, Credit, HumanTask, Order, Signal, Trigger, etc.).
- **UI stack:** JSF (Facelets) with PrimeFaces components and Ivy Freya theme. Master layout at `webContent/layouts/frame.xhtml`.
- **Lazy data loading:** `AbstractBusinessDataLazyDataModel` in `src/` provides paginated, sortable PrimeFaces DataTable support via Axon Ivy's `BusinessDataRepository` API.
- **Business rules:** Drools rules in `rules/workflow/credit/` drive credit approval decisions (level 1/2 approval thresholds).
- **Role-based access:** Defined in `config/roles.yaml` with hierarchies (Employee → Teamleader → Manager; specialized manager roles; Order processing roles).
- **Localization:** English and German via CMS YAML files in `cms/`.

### Workflow Modules (processes/)

The 10 demo modules: AgileBPM, BusinessCaseData, BusinessData, BusinessNotification, CaseMap, Expiry, Humantask, Maintenance, Signal, Trigger. Each demonstrates a different Axon Ivy capability.

### Important Java Classes

- `CaseChore` — scheduled maintenance that cleans up old cases based on `maintenance.caze.cleanup.days` variable
- `DemoDataCreator` — seeds demo Dossier/Person records
- `AbstractBusinessDataLazyDataModel` — base for paginated PrimeFaces tables
- `SystemDo` — privileged operations using Ivy's Sudo API

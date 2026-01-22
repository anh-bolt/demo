# CLAUDE.md - Axon Ivy Workflow Demos Project

## Project Overview

This is an **Axon Ivy 13.2** demo project showcasing workflow management capabilities. It contains 10 different workflow scenarios demonstrating various Ivy platform features including human tasks, business data, signals, triggers, case management, and business rules.

## Technology Stack

- **Platform:** Axon Ivy 13.2.0-SNAPSHOT
- **Java Version:** JavaSE-21
- **Build Tool:** Maven 4.0.0
- **Packaging:** IAR (Ivy Archive)
- **Web Framework:** JSF/Facelets (XHTML dialogs)
- **Rules Engine:** Drools (DRL files)
- **Persistence:** JPA 2.2 via Ivy Business Data API

## Project Structure

```
workflow-demos/
├── processes/           # Workflow process definitions (*.p.json, *.icm)
├── dataclasses/         # Data class definitions (*.d.json)
├── src/                 # Java source code
├── src_hd/              # HTML Dialog definitions (XHTML views + logic)
├── src_dataClasses/     # Generated data class sources
├── src_generated/       # Auto-generated code
├── config/              # Configuration files (persistence.xml, users.xml)
├── cms/                 # Content Management System (localization, images)
├── webContent/          # Web resources (images, layouts)
├── rules/               # Drools business rules (*.drl)
├── .ivyproject          # Ivy project configuration
└── pom.xml              # Maven build configuration
```

## Build Commands

```bash
# Build the project
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Package only
mvn package
```

## Demo Scenarios

The project contains 10 demo categories in `processes/`:

1. **AgileBPM** - Agile workflow patterns, ad-hoc task handling
2. **BusinessCaseData** - Business case data integration
3. **BusinessData** - CRUD operations, concurrent modification, data migration
4. **BusinessNotification** - Order processing with email notifications
5. **CaseMap** - Complete lending workflow with case management
6. **Expiry** - Deadline and expiry handling
7. **Humantask** - User tasks, parallel processing, dynamic role assignment
8. **Maintenance** - Case cleanup and housekeeping
9. **Signal** - Inter-process communication, employee onboarding/offboarding
10. **Trigger** - Event-driven process execution

## Key Components

### Process Files
- Location: `processes/workflow/<category>/`
- Format: JSON-based process definitions (`.p.json`)
- Case Maps: `.icm` files for case management

### Data Classes
- Location: `dataclasses/workflow/`
- Format: JSON definitions (`.d.json`) generating Java classes
- Key domains: businessdata, credit, humantask, signal, trigger

### HTML Dialogs
- Location: `src_hd/workflow/<category>/`
- Each dialog has:
  - `<DialogName>.xhtml` - View template
  - `<DialogName>Data.d.json` - Data class
  - `<DialogName>Process.p.json` - Dialog logic

### Java Classes
- Location: `src/ch/ivyteam/ivy/`
- `Helper.java` - Utility functions
- `SystemDo.java` - Task/Case custom field management
- `wfdemo/businessdata/` - Lazy data models for efficient browsing
- `wfdemo/maintenance/CaseChore.java` - Case cleanup job

### Business Rules
- Location: `rules/workflow/credit/`
- `NeedsApproval1.drl` - Risk assessment (amount/salary ratio)
- `NeedsApproval2.drl` - Geographic approval requirements

## Configuration

### Users (config/users.xml)
Demo users with predefined roles:
- `ldv` (Leonardo Da Vinci) - Employee
- `hb` (Hugo Boss) - Teamleader, multiple manager roles
- `wt` (William Tell) - Teamleader
- `mc` (Marie Curie) - Facility Manager
- `md` (Marlene Dietrich) - HR Manager
- `jb` (James Bond) - Office Manager, Deliverer
- `hf` (Henry Ford) - IT Manager
- `bf` (Benjamin Franklin) - Executive Manager, Finance

### Localization (cms/)
- `cms_en.yaml` - English content
- `cms_de.yaml` - German content

## Development Guidelines

### Process Definitions
- Process files are JSON-based (`.p.json`)
- Use the Axon Ivy Designer to edit processes visually
- Callable sub-processes are referenced by signature

### Data Classes
- Define in `.d.json` files in `dataclasses/`
- Business Data classes use `@businessdata` annotation for persistence
- Generated sources appear in `src_dataClasses/`

### HTML Dialogs
- Use XHTML with JSF components
- Reference Ivy namespace: `xmlns:h="http://xmlns.jcp.org/jsf/html"`
- Component libraries: PrimeFaces, Ivy UI components

### Java Code
- Use `ivy.*` API for platform services
- `Sudo.call()` for elevated permission operations
- Business Data accessed via `Ivy.repo()` API

### Business Rules
- Drools DRL format
- Import data classes at top of file
- Rules evaluated against in-memory facts

## Testing

Run tests using Maven:
```bash
mvn test
```

Test sources location: `src_test/`

## Important Notes
1. **Demo Data:** Uses famous scientists names for sample data
2. **Case Retention:** Configurable cleanup period (default 1 year)
3. **Parent POM:** Inherits from `com.axonivy.ivy.api:ivy-project-parent`

## Resources

- [Axon Ivy Documentation](https://developer.axonivy.com/)
- [Axon Ivy Designer](https://developer.axonivy.com/download)
- [Ivy API Reference](https://developer.axonivy.com/api)

---
name: axon-ivy-workflow-guide
description: Step-by-step guide for creating complete Axon Ivy workflow processes. Use this skill FIRST when building new workflows.
---

## Coding Standards (MUST follow)

Every artifact produced through this skill — Java, `.p.json`, `.xhtml`, CMS YAML, config — MUST comply with [.claude/rules/axon-ivy-coding.md](../../rules/axon-ivy-coding.md). Apply automatically; do not wait to be asked.

Pay special attention to:
- **Layered process modeling** — keep the business layer coarse (verb-noun names); push services, DB/REST calls, and technical logging into the technical layer.
- **Naming** — processes `VerbNoun`, sub-processes `Feature_Action`, dialogs reflect the use case (never `Dialog1`).
- **Logging** — parameterized SLF4J / `ivy.log`, never string concatenation, never PII/secrets.
- **CMS** — access via enum/constant keys, organized under `/messages`, `/errors`, `/ui`, `/templates`, `/email`.

## When to Use

Use this skill when:

- Creating a new workflow process from scratch
- Adding a new feature that requires process + UI + data
- User asks to "create a workflow" or "implement a process"

## Workflow Creation Steps

### Step 1: Define Domain Model

**Skill:** `axon-ivy-data`

Create Java model classes in `src/package/model/`:

- [ ] Entity class with fields, getters, setters, builder pattern
- [ ] Status enum if workflow has states
- [ ] Repository class for persistence

### Step 2: Create Master Data Class

**Skill:** `axon-ivy-data`

Create master data in `dataclasses/package/`:

- [ ] Create `.d.json` file with workflow state fields
- [ ] Include main entity and temp entity (for AI operations)

### Step 3: Design Process Flow (Layered)

**Skill:** `axon-ivy-process`

Follow the **three-layer** approach from the coding rules — never collapse layers.

| Layer | Lives in | Contains |
|-------|----------|----------|
| **Business** | `processes/<feature>/<VerbNoun>.p.json` | Coarse steps named by intent (`ValidateRequest`, `ApproveLeave`) |
| **Detail** | `processes/<feature>/sub/<Feature_Action>.p.json` | Sub-processes decomposing each business step |
| **Technical** | `processes/<feature>/tech/` or Script/ProgramInterface nodes | Concrete service/DB/REST calls, error boundaries, logging |

Create process in `processes/package/`:

- [ ] RequestStart with signature
- [ ] Business-layer activities named by intent (verb-noun), NOT by implementation (no `CallXxxService`)
- [ ] Detail-layer sub-processes prefixed with feature (`Leave_Validate`, `Leave_Notify`)
- [ ] Technical-layer scripts / `ProgramInterface` nodes contain the logging + error handling
- [ ] Alternative nodes for conditions (labeled, null-safe)
- [ ] TaskEnd for every terminal path

### Step 4: Create Managed Beans (if needed)

**Skill:** `axon-ivy-bean`

Create beans in `src/package/managedbean/` when dialog needs Java logic beyond `#{data.xxx}`:

- [ ] Create main bean with `@ManagedBean @ViewScoped`
- [ ] Create sub-beans for complex tabs (optional)
- [ ] Create document beans for file upload (optional)

### Step 5: Create Dialog UIs

**Skills:** `axon-ivy-html`, `axon-ivy-bean`

Create dialogs in `src_hd/package/`:

- [ ] Create dialog folder structure
- [ ] Create XHTML with form layout
- [ ] Bind to managed beans or `#{data.xxx}` as appropriate
- [ ] Create Process.p.json for dialog flow

### Step 6: Configure Roles & Users

**Skill:** `axon-ivy-config`

Update config files:

- [ ] Add roles to `config/roles.yaml`
- [ ] Add test users to `config/users.yaml`
- [ ] Add variables to `config/variables.yaml`

### Step 7: Test & Verify

- [ ] Start the process from Portal
- [ ] Complete each task in sequence
- [ ] Verify data persistence
- [ ] Check error handling paths

## File Creation Order

```text
1. src/package/model/Entity.java
2. src/package/model/EntityStatus.java
3. src/package/repository/EntityRepository.java
4. dataclasses/package/WorkflowMasterData.d.json
5. processes/package/Workflow.p.json
6. src_hd/package/DialogName/DialogName.xhtml
7. src_hd/package/DialogName/DialogNameProcess.p.json
8. config/roles.yaml (update)
```

## Common Patterns

### Basic CRUD Workflow

```text
Start → Create Entity → Review → Approve/Reject → End
```

### AI-Assisted Workflow

```text
Start → Input Data → AI Generate → Human Review → Approve → End
```

### Multi-Step Approval

```text
Start → Create → Manager Approve → Director Approve → End
```

## Checklist Before Completion

- [ ] All process paths lead to TaskEnd
- [ ] Error boundaries on risky operations
- [ ] Case/task names set dynamically
- [ ] Repository saves at appropriate points
- [ ] Roles assigned to tasks

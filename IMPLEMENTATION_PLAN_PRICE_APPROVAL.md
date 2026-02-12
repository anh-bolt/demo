# Implementation Plan: Price-Based Approval for Procurement Requests

## Business Requirement

Add a price-based approval rule to the Humantask Procurement Request workflow:
- **Requests with total price > $5000**: Require Executive Manager approval after Manager verification
- **Requests with total price ≤ $5000**: Auto-approve after Manager verification (skip Executive step)

---

## Part 1: Implementation WITHOUT CLAUDE.md

### Discovery Phase - Questions AI Must Answer

Without CLAUDE.md, an AI assistant must explore and discover:

| Question | How to Find Answer |
|----------|-------------------|
| What framework is this? | Read pom.xml, .ivyproject, search configs |
| Where are processes defined? | Search for workflow/process patterns |
| What file format for processes? | Discover through file exploration |
| Where are Humantask files? | Recursive directory search |
| What data model exists? | Search for "procurement", read multiple files |
| Where is localization? | Search for translation patterns |
| What roles exist? | Search user configurations |
| How are gateways structured? | Read existing process files for patterns |

### Exploration Steps Required

```
1. Search for project type
   → Read pom.xml → discover Axon Ivy
   → Read .ivyproject → understand structure

2. Find process files
   → Search *.p.json, *.bpmn, *.xml
   → Discover processes/ directory
   → Find Humantask folder

3. Understand process format
   → Read ProcurementRequestUserTask.p.json
   → Study Alternative gateway syntax
   → Study Script element syntax

4. Find data classes
   → Search for "ProcurementRequest"
   → Discover dataclasses/ directory
   → Read ProcurementRequest.d.json

5. Find localization
   → Search for "cms", "i18n", "messages"
   → Discover cms/ directory
   → Read cms_en.yaml, cms_de.yaml

6. Understand role structure
   → Search for "Executive", "Manager"
   → Find config/users.xml
   → Map roles to users
```

**Estimated tool calls: 50-70**
**Risk: May miss patterns, make inconsistent assumptions**

---

## Part 2: Implementation WITH CLAUDE.md

### Instant Knowledge Available

From CLAUDE.md, AI knows immediately:

| Information | Source in CLAUDE.md |
|-------------|---------------------|
| Platform: Axon Ivy 14.0 | Technology Stack section |
| Processes: `processes/workflow/<category>/` | Project Structure |
| Format: `.p.json` (JSON-based) | Process Files section |
| Data classes: `dataclasses/workflow/` | Data Classes section |
| Dialogs: `src_hd/workflow/<category>/` | HTML Dialogs section |
| Localization: `cms/cms_en.yaml`, `cms/cms_de.yaml` | Configuration section |
| Executive Manager: User `bf` (Benjamin Franklin) | Users section |
| Humantask demo: #7 in list | Demo Scenarios section |

### Direct File Access

```
1. Read processes/Humantask/ProcurementRequestUserTask.p.json
2. Read cms/cms_en.yaml
3. Read cms/cms_de.yaml
4. Implement changes
```

**Estimated tool calls: 8-12**
**Risk: Low - follows documented patterns**

---

## Comparison Summary

| Aspect | Without CLAUDE.md | With CLAUDE.md |
|--------|-------------------|----------------|
| **Discovery time** | Extensive exploration | Immediate |
| **File path accuracy** | Trial and error | Direct access |
| **Pattern consistency** | May deviate | Follows documented patterns |
| **Tool calls needed** | ~50-70 | ~8-12 |
| **Error risk** | Moderate-High | Low |
| **Context efficiency** | Poor | Excellent |
| **Improvement factor** | Baseline | **~6x more efficient** |

---

## Part 3: Detailed Implementation Plan

### Current Workflow Flow

```
Employee (Enter Request) → Manager (Verify) → [Verified?] → Executive (Accept) → Notify
                                                ↓ No
                                              Notify
```

### Proposed Workflow Flow

```
Employee (Enter Request) → Manager (Verify) → [Verified?] → [Price > $5000?] → Executive (Accept) → Notify
                                                ↓ No              ↓ No
                                              Notify        Auto Approve → Notify
```

### Files to Modify

| File | Change Type | Description |
|------|-------------|-------------|
| `processes/Humantask/ProcurementRequestUserTask.p.json` | Major | Add gateway + script elements |
| `cms/cms_en.yaml` | Minor | Add auto-approval message |
| `cms/cms_de.yaml` | Minor | Add German translation |

### Process JSON Changes

#### 1. New Element: Price Threshold Gateway (f17)

```json
{
  "id" : "f17",
  "type" : "Alternative",
  "name" : "Needs Executive\nApproval?",
  "config" : {
    "conditions" : {
      "f18" : "in.totalPrice > 5000"
    }
  },
  "visual" : {
    "at" : { "x" : 512, "y" : 440 },
    "labelOffset" : { "x" : 75, "y" : 13 }
  },
  "connect" : [
    { "id" : "f18", "to" : "f7", "label" : {
        "name" : "> $5000",
        "segment" : 0.5,
        "offset" : { "y" : -12 }
      }, "var" : "in1" },
    { "id" : "f19", "to" : "f20", "via" : [ { "x" : 512, "y" : 520 } ], "label" : {
        "name" : "<= $5000",
        "segment" : 0.5,
        "offset" : { "x" : 15 }
      } }
  ]
}
```

#### 2. New Element: Auto Approve Script (f20)

```json
{
  "id" : "f20",
  "type" : "Script",
  "name" : "Auto Approve",
  "config" : {
    "output" : {
      "map" : {
        "out" : "in",
        "out.accepted" : "true"
      },
      "code" : [
        "import workflow.humantask.LogEntry;",
        "",
        "LogEntry autoApproveLog = new LogEntry();",
        "autoApproveLog.activity = ivy.cms.co(\"/Dialogs/procurementRequest/autoApprovedBy\");",
        "autoApproveLog.timestamp = new DateTime();",
        "autoApproveLog.user = new workflow.humantask.User();",
        "autoApproveLog.user.fullName = \"System\";",
        "autoApproveLog.user.role = \"Auto-Approval\";",
        "out.activityLog.add(autoApproveLog);"
      ]
    }
  },
  "visual" : {
    "at" : { "x" : 640, "y" : 520 }
  },
  "connect" : [
    { "id" : "f21", "to" : "f9", "via" : [ { "x" : 800, "y" : 520 } ] }
  ]
}
```

#### 3. Modify Existing Connection (f8)

**Before:**
```json
{ "id" : "f8", "to" : "f7", ... }
```

**After:**
```json
{ "id" : "f8", "to" : "f17", ... }
```

### Localization Changes

#### cms_en.yaml
```yaml
Dialogs:
  procurementRequest:
    autoApprovedBy: Auto-approved (under $5000 threshold)
```

#### cms_de.yaml
```yaml
Dialogs:
  procurementRequest:
    autoApprovedBy: Automatisch genehmigt (unter $5000 Schwelle)
```

---

## Testing Scenarios

| # | Scenario | Input | Expected Result |
|---|----------|-------|-----------------|
| 1 | Low value approved | 10 × $400 = $4000, Manager verifies | Auto-approved, email sent |
| 2 | High value flow | 10 × $600 = $6000, Manager verifies | Executive task created |
| 3 | High value approved | $6000, Executive accepts | Approved, email sent |
| 4 | High value declined | $6000, Executive declines | Declined, email sent |
| 5 | Manager declines | Any amount | Declined (no Executive task) |
| 6 | Boundary case | 50 × $100 = $5000 | Auto-approved (≤ threshold) |

---

## Why CLAUDE.md Makes This Better

### Without CLAUDE.md
- AI must **discover** project structure through exploration
- May **miss** important patterns (like activity logging convention)
- Could use **wrong file paths** or formats
- Might **create inconsistent** implementations
- Wastes tokens on **redundant exploration**

### With CLAUDE.md
- AI has **immediate context** of entire project
- Follows **documented patterns** consistently
- Uses **correct paths** and formats
- Maintains **code consistency** with existing implementation
- Focuses tokens on **actual implementation**

**Bottom line: CLAUDE.md transforms "exploring unknown territory" into "implementing with full context".**

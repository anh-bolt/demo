# Instructions: Create Axon Ivy Process (.p.json)

Process files define workflow logic. They live in `processes/` (workflow processes) and `src_hd/` (HTML Dialog processes).

---

## 1. Top-Level Structure

```json
{
  "$schema": "https://json-schema.axonivy.com/process/13.2.0/process.json",
  "id": "UNIQUE_16CHAR_HEX",
  "config": { "data": "namespace.DataClassName" },
  "elements": [ /* process elements */ ],
  "layout": { "lanes": [...], "colors": {...} }
}
```

- **id**: Unique 16-character uppercase hex (e.g., `15254DCE818AD7A2`)
- **config.data**: Fully qualified data class for `in`/`out` variables
- **elements**: Array of all nodes (starts, ends, activities, gateways, annotations)
- **layout**: Optional swimlane definitions

---

## 2. Common Element Structure

Every element shares:

```json
{
  "id": "f0",
  "type": "ElementType",
  "name": "Display Name",
  "config": { /* type-specific */ },
  "visual": {
    "at": { "x": 96, "y": 192 },
    "size": { "width": 128, "height": 48 },
    "labelOffset": { "x": 12, "y": 37 }
  },
  "connect": [
    { "id": "f2", "to": "f1" }
  ]
}
```

- **name**: String or array of strings (multi-line label)
- **visual.at**: Canvas position (required)
- **visual.size**: Optional custom dimensions
- **connect**: Outgoing connections to other elements

---

## 3. Connector Structure

```json
{
  "id": "f2",
  "to": "f1",
  "condition": "ivp==\"TaskA.ivp\"",
  "via": [{ "x": 512, "y": 440 }],
  "var": "in1",
  "label": {
    "name": "Yes",
    "segment": 0.85,
    "offset": { "x": 15, "y": 0 }
  },
  "color": "default"
}
```

| Property | Purpose |
|----------|---------|
| `to` | Target element ID (required) |
| `condition` | Boolean expression for gateway routing |
| `via` | Waypoints for line routing |
| `var` | Variable name for parallel merge (`in1`, `in2`) |
| `label` | Connector label with positioning |

---

## 4. Element Types — Starts

### RequestStart — HTTP entry point

```json
{
  "id": "f0", "type": "RequestStart", "name": "start.ivp",
  "config": {
    "signature": "start",
    "input": {
      "params": [{ "name": "id", "type": "String", "desc": "" }],
      "map": { "out.id": "param.id" }
    },
    "request": {
      "name": "Process Display Name",
      "description": "Description text",
      "category": "category/path",
      "isVisibleOnStartList": true,
      "customFields": [
        { "name": "cssIcon", "value": "fa fa-list" }
      ]
    },
    "permission": {
      "anonymous": false,
      "roles": ["Employee"]
    },
    "task": {
      "name": "Task name template",
      "category": "Input/Category"
    },
    "case": {
      "name": "Case name",
      "category": "Module/Action",
      "attachToBusinessCase": false
    },
    "persistOnStart": false
  },
  "tags": ["demo"],
  "visual": { "at": { "x": 96, "y": 192 } },
  "connect": [{ "id": "f2", "to": "f1" }]
}
```

**Key properties:**
- `signature`: Method name for URL routing
- `input.params`: Typed request parameters
- `input.map`: Map params to process data (`out.field = param.name`)
- `request.isVisibleOnStartList`: Show in portal start list (default: true)
- `permission.roles`: Restrict access by role
- `case.attachToBusinessCase`: `false` = create new business case

### SignalStartEvent — Signal receiver

```json
{
  "id": "f32", "type": "SignalStartEvent", "name": "user:created",
  "config": {
    "signalCode": "user:created",
    "output": {
      "map": {},
      "code": [
        "import workflow.signal.User;",
        "import com.google.gson.Gson;",
        "",
        "out.user = new Gson().fromJson(signal.getSignalData() as String, User.class) as User;"
      ]
    }
  },
  "visual": { "at": { "x": 96, "y": 288 } },
  "connect": [{ "id": "f29", "to": "f8", "var": "in2" }]
}
```

- `signalCode`: Signal pattern (supports wildcards: `user:*`, `*:created`)
- Access signal data via `signal.getSignalData()`

### ProgramStart — Automated/scheduled entry

```json
{
  "id": "f0", "type": "ProgramStart", "name": "HouseKeeper",
  "config": {
    "javaClass": "ch.ivyteam.ivy.process.extension.impl.AbstractUserProcessExtension",
    "userConfig": { "key": "value" }
  },
  "visual": { "at": { "x": 96, "y": 64 } },
  "connect": [{ "id": "f2", "to": "f1" }]
}
```

### ErrorStartEvent — Error handler

```json
{
  "id": "f5", "type": "ErrorStartEvent", "name": "taskExpired",
  "visual": { "at": { "x": 224, "y": 464 } },
  "connect": [{ "id": "f6", "to": "f1" }]
}
```

- Referenced from `TaskSwitchEvent.config.task.expiry.error` by ID

---

## 5. Element Types — End

### TaskEnd

```json
{ "id": "f1", "type": "TaskEnd", "visual": { "at": { "x": 352, "y": 192 } } }
```

---

## 6. Element Types — Activities

### Script — Code execution

```json
{
  "id": "f3", "type": "Script", "name": "Set Business Case Name",
  "config": {
    "output": {
      "code": [
        "ivy.case.getBusinessCase().setName(\"New Employee: \" + in.name);",
        "out.calculated = in.amount * 1.19;"
      ],
      "map": { "out.field": "expression" }
    },
    "sudo": true
  },
  "visual": { "at": { "x": 432, "y": 224 }, "size": { "width": 160, "height": 48 } },
  "connect": [{ "id": "f8", "to": "f7" }]
}
```

- `output.code`: Array of IvyScript lines (or single string)
- `output.map`: Output mapping expressions
- `sudo`: Run with system privileges

### DialogCall — Invoke HTML Dialog

```json
{
  "id": "f3", "type": "DialogCall", "name": "Enter Request",
  "config": {
    "dialog": "workflow.humantask.EnterRequest:start()",
    "call": {
      "map": { "param.procurementRequest": "in" }
    },
    "output": {
      "map": {
        "out": "result.procurementRequestData",
        "out.totalPrice": "result.procurementRequestData.amount * result.procurementRequestData.pricePerUnit"
      }
    }
  },
  "visual": { "at": { "x": 224, "y": 192 } },
  "connect": [{ "id": "f6", "to": "f5" }]
}
```

- `dialog`: `namespace.DialogName:methodName(ParamTypes)`
- `call.map`: Map process data to dialog params (`param.X = in.Y`)
- `output.map`: Map dialog result back (`out.X = result.Y`)

### UserTask — Combined task switch + dialog

```json
{
  "id": "f5", "type": "UserTask", "name": "Verify Request",
  "config": {
    "dialog": "workflow.humantask.VerifyRequest:start(workflow.humantask.ProcurementRequest)",
    "call": {
      "map": { "param.procurementRequest": "in" }
    },
    "task": {
      "name": "<%=ivy.cms.co(\"/TaskDescriptions/verifyRequest\")%>: <%=in.amount%>",
      "description": "Task description text",
      "category": "Review/Procurement",
      "responsible": {
        "roles": ["Manager"]
      },
      "priority": { "level": "NORMAL" },
      "expiry": {
        "timeout": "'1m'",
        "priority": { "level": "HIGH" },
        "responsible": { "roles": ["Manager"] },
        "error": "errorStartId"
      },
      "customFields": [
        { "name": "cssIcon", "value": "fa fa-check" }
      ]
    },
    "case": {
      "category": "Office/Key/Assignment"
    },
    "output": {
      "map": {
        "out": "in",
        "out.dataOkManager": "result.dataOk"
      }
    }
  },
  "visual": { "at": { "x": 384, "y": 320 } },
  "boundaries": [ /* SignalBoundaryEvent elements */ ],
  "connect": [
    { "id": "f12", "to": "f11", "condition": "ivp==\"TaskA.ivp\"" }
  ]
}
```

- Combines `TaskSwitchEvent` + `DialogCall` in one element
- `task.responsible.roles`: Role-based assignment
- `task.responsible.type`: `"USER_FROM_ATTRIBUTE"` or `"ROLE_FROM_ATTRIBUTE"` for dynamic
- `task.responsible.script`: IvyScript expression for dynamic assignment
- Connector condition `ivp=="TaskA.ivp"` routes after task completion

### TaskSwitchEvent — Create human task (without dialog)

```json
{
  "id": "f2", "type": "TaskSwitchEvent",
  "config": {
    "task": {
      "name": "Task display name",
      "description": "Description",
      "category": "Accept/Procurement",
      "responsible": { "roles": ["Executive Manager"] },
      "priority": { "level": "NORMAL" },
      "expiry": {
        "timeout": "'1m'",
        "priority": { "level": "HIGH" },
        "responsible": { "type": "DELETE_TASK" },
        "error": "f5"
      },
      "customFields": [
        { "name": "KindCode", "type": "STRING", "value": "in.task.kind" }
      ]
    },
    "case": { "name": "Case name", "category": "System" }
  },
  "visual": { "at": { "x": 224, "y": 384 } },
  "connect": [
    { "id": "f4", "to": "f1", "condition": "ivp==\"TaskA.ivp\"" }
  ]
}
```

**Expiry options:**
- Escalate priority: `"priority": { "level": "HIGH" }`
- Reassign role: `"responsible": { "roles": ["Manager"] }`
- Delete & trigger error: `"responsible": { "type": "DELETE_TASK" }`, `"error": "errorStartId"`

### EMail — Send email

```json
{
  "id": "f9", "type": "EMail", "name": "Notify Requester",
  "config": {
    "headers": {
      "subject": "<%=ivy.cms.co(\"/Emails/subject\")%> <%=in.name%>",
      "from": "<%=ivy.cms.co(\"/Emails/senderMail\")%>",
      "to": "<%=in.requester.email%>"
    },
    "message": {
      "body": [
        "<html>",
        "  <style type=\"text/css\"><%=ivy.cms.co(\"/Styles/Classic\")%></style>",
        "  <%=ivy.cms.co(\"/Images/Logo\")%>",
        "  <%=ivy.cms.co(\"/Emails/bodyContent\")%>",
        "</html>"
      ],
      "contentType": "text/html"
    },
    "exceptionHandler": ">> Ignore Exception"
  },
  "visual": { "at": { "x": 800, "y": 192 }, "size": { "width": 128 } },
  "connect": [{ "id": "f2", "to": "f1" }]
}
```

- `exceptionHandler: ">> Ignore Exception"` prevents email failures from stopping the process

### TriggerCall — Fire-and-forget subprocess

```json
{
  "id": "f13", "type": "TriggerCall", "name": "Reserve parking lot",
  "config": {
    "processCall": "Trigger/ParkingLotReservation:start(workflow.trigger.NewEmployeeData)",
    "call": {
      "map": { "param.newEmployeeData": "in" }
    }
  },
  "visual": { "at": { "x": 712, "y": 224 } },
  "connect": [{ "id": "f4", "to": "f10" }]
}
```

- `processCall`: `Folder/ProcessName:signature(ParamTypes)`
- Starts another process asynchronously without waiting

---

## 7. Element Types — Gateways

### Alternative — Exclusive decision (if/else)

```json
{
  "id": "f11", "type": "Alternative", "name": "Verified?",
  "config": {
    "conditions": {
      "f8": "in.dataOkManager"
    }
  },
  "visual": { "at": { "x": 512, "y": 320 } },
  "connect": [
    {
      "id": "f8", "to": "f7",
      "label": { "name": "Yes", "segment": 0.85, "offset": { "x": 15 } }
    },
    {
      "id": "f13", "to": "f9",
      "label": { "name": "No", "segment": 1.06, "offset": { "y": 13 } }
    }
  ]
}
```

- `conditions`: Maps connector ID to boolean expression
- The connector whose ID matches a condition follows the "true" path
- The other connector is the default (else) path

### TaskSwitchGateway — Parallel task branching

```json
{
  "id": "f2", "type": "TaskSwitchGateway",
  "config": {
    "tasks": [
      {
        "id": "TaskA",
        "name": "Task for Teamleader",
        "category": "Review/Procurement",
        "responsible": { "roles": ["Teamleader"] }
      },
      {
        "id": "TaskB",
        "name": "Task for Manager",
        "category": "Review/Procurement",
        "responsible": { "roles": ["Manager"] }
      }
    ]
  },
  "visual": { "at": { "x": 312, "y": 304 } },
  "connect": [
    { "id": "f10", "to": "f5", "condition": "ivp==\"TaskA.ivp\"" },
    { "id": "f11", "to": "f6", "condition": "ivp==\"TaskB.ivp\"" }
  ]
}
```

**Join gateway** (merge parallel branches):

```json
{
  "id": "f7", "type": "TaskSwitchGateway",
  "config": {
    "tasks": [{
      "id": "TaskB",
      "responsible": { "roles": ["SYSTEM"] },
      "skipTasklist": true
    }],
    "output": {
      "map": {
        "out": "in1",
        "out.dataOkManager": "in2.dataOkManager",
        "out.dataOkTeamLeader": "in1.dataOkTeamLeader"
      }
    }
  },
  "connect": [
    { "id": "f20", "to": "f19", "condition": "ivp==\"TaskB.ivp\"" }
  ]
}
```

- Incoming connectors use `"var": "in1"` and `"var": "in2"` to identify branches
- Output map merges data from both branches

---

## 8. Boundary Events

### SignalBoundaryEvent — Interrupt a running task

```json
{
  "id": "f5", "type": "UserTask", "name": "Set up workstation",
  "config": { /* UserTask config */ },
  "boundaries": [
    {
      "id": "St0",
      "type": "SignalBoundaryEvent",
      "name": "admin:quit:[userKey]",
      "config": {
        "signalCode": "admin:quit:<%=in.user.userKey%>",
        "output": {
          "code": [
            "import workflow.signal.QuitUserEvent;",
            "import com.google.gson.Gson;",
            "",
            "out.quitUserEvent = new Gson().fromJson(signal.getSignalData() as String, QuitUserEvent.class) as QuitUserEvent;",
            "ivy.task.setName(\"CANCEL TASK: \" + ivy.task.getName());"
          ]
        }
      },
      "visual": {
        "at": { "x": 256, "y": 320 },
        "labelOffset": { "x": 89, "y": 25 }
      },
      "connect": [
        { "id": "f20", "to": "f19", "via": [{ "x": 256, "y": 384 }] }
      ]
    }
  ]
}
```

- Defined inside the parent element's `boundaries` array
- Signal code supports `<%= %>` template expressions for dynamic matching
- When triggered, interrupts the parent task and follows boundary connector

---

## 9. Annotations

```json
{
  "id": "f14", "type": "ProcessAnnotation",
  "name": [
    "Line 1 of the annotation.",
    "Line 2 of the annotation."
  ],
  "visual": {
    "at": { "x": 312, "y": 392 },
    "size": { "width": 176, "height": 44 }
  },
  "connect": [
    { "id": "f15", "to": "f5" }
  ]
}
```

- Non-executable documentation elements
- `connect` draws a dashed line to the referenced element

---

## 10. Layout — Swimlanes

```json
"layout": {
  "lanes": [
    {
      "name": "Process Name",
      "offset": 128,
      "size": 360,
      "lanes": [
        { "name": "Employee", "size": 128 },
        { "name": "Manager", "size": 120 },
        { "name": "Executive", "size": 112 }
      ]
    }
  ],
  "colors": {
    "NodeStyle2": "rgb(57, 99, 173)"
  }
}
```

- `offset`: Y-position where the pool starts
- `size`: Total height of the pool
- Nested `lanes`: Sub-lanes for role-based organization

---

## 11. Data Mapping Reference

| Variable | Context |
|----------|---------|
| `in` | Current process data (input) |
| `out` | Output data (modified in scripts/mappings) |
| `param` | RequestStart input parameters |
| `result` | DialogCall return value |
| `signal` | SignalStartEvent signal data |
| `in1`, `in2` | Parallel branch data at merge gateways |

**Mapping patterns:**
```json
"map": {
  "out": "in",                    // Copy entire input to output
  "out.field": "result.field",    // Map specific field
  "out.list": "in.list.add(result.item)",  // Collection operation
  "out.id": "param.id",          // From request parameter
  "out.user": "signal.getSignalData() as workflow.signal.User"  // From signal
}
```

**Code output** (runs after map):
```json
"code": [
  "import some.package.ClassName;",
  "",
  "out.calculated = in.amount * 1.19;",
  "ivy.case.getBusinessCase().setName(\"Title: \" + in.name);"
]
```

---

## 12. Template Expressions

Use `<%= expression %>` in names, descriptions, task names, email subjects:

```
<%=in.amount%>                           // Field value
<%=ivy.cms.co("/TaskDescriptions/key")%>  // CMS localized string
<%=(in.accepted ? "Yes" : "No")%>        // Ternary expression
<%=in.user.name%> [<%=in.user.userKey%>] // Concatenation
```

---

## 13. Special Configuration Flags

| Flag | Purpose | Example |
|------|---------|---------|
| `sudo: true` | Run Script with system privileges | Setting business case name |
| `exceptionHandler: ">> Ignore Exception"` | Suppress errors | EMail steps |
| `persistOnStart: true` | Save state at process start | Long-running processes |
| `triggerable: true` | Can be triggered externally | API-callable starts |
| `isVisibleOnStartList: false` | Hide from portal | Internal sub-processes |
| `attachToBusinessCase: false` | Create new business case | Top-level processes |

---

## 14. Common Workflow Patterns

### A. Simple Sequential
```
RequestStart → DialogCall → TaskEnd
```

### B. Approval with Decision
```
RequestStart → DialogCall → TaskSwitchEvent → DialogCall → Alternative → [Yes: next step] / [No: end]
```

### C. UserTask (Modern Pattern)
```
RequestStart → DialogCall → UserTask → Alternative → UserTask → TaskEnd
```
UserTask replaces separate TaskSwitchEvent + DialogCall.

### D. Parallel Approval
```
RequestStart → DialogCall → TaskSwitchGateway(split) → [TaskA: DialogCall] + [TaskB: DialogCall] → TaskSwitchGateway(join) → Alternative → TaskEnd
```

### E. Signal-Driven
```
SignalStartEvent → UserTask(with SignalBoundaryEvent) → TaskEnd
                                    ↓ (boundary)
                              Script → TaskEnd
```

### F. Async Trigger
```
RequestStart → DialogCall → Alternative → [Yes: TriggerCall] → EMail → TaskEnd
                                          [No: EMail → TaskEnd]
```

### G. Expiry Handling
```
RequestStart → TaskSwitchEvent(expiry → ErrorStartEvent) → TaskEnd
                                           ↓
                                    ErrorStartEvent → TaskEnd
```

---

## 15. ID Generation

- **Element IDs**: Use format `f0`, `f1`, `f2`, ... (sequential within process)
- **Process IDs**: 16-character uppercase hex (e.g., `15254DCE818AD7A2`)
- **Boundary IDs**: Use format `St0`, `St1`, ...
- **Task IDs in gateways**: `TaskA`, `TaskB`, `TaskC`, ...

---

## 16. Visual Positioning Guidelines

- Start elements: x ~96, leftmost position
- Elements flow left to right, ~128px apart horizontally
- Swimlane sub-lanes are ~120px tall each
- Default element size: ~26x26 (circle/diamond) or 128x48 (activity)
- Use `via` waypoints for non-straight connectors

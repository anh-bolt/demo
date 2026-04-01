# Creating a Complete HTML Dialog

Every HTML Dialog requires **3 files** in the same directory under `src_hd/{namespace}/{DialogName}/`:

| File | Purpose |
|------|---------|
| `{DialogName}.xhtml` | UI view (JSF/PrimeFaces) |
| `{DialogName}Data.d.json` | Data model — fields accessible via `#{data.*}` |
| `{DialogName}Process.p.json` | Process logic — start, events, methods |

To invoke a dialog, create a **launcher process** in `processes/` using `DialogCall`.

## Creation Order

1. **Shared data classes** (if needed) — `dataclasses/{namespace}/{Name}.d.json`
2. **Dialog data model** — `src_hd/{namespace}/{DialogName}/{DialogName}Data.d.json`
3. **Dialog process** — `src_hd/{namespace}/{DialogName}/{DialogName}Process.p.json`
4. **XHTML view** — `src_hd/{namespace}/{DialogName}/{DialogName}.xhtml`
5. **Launcher process** — `processes/{Name}.p.json`

## ID/GUID Generation

All process IDs and GUIDs must be **unique 16-character uppercase hex strings** (e.g., `139E30EF308FD0DC`). Generate a different value for each `id` and `guid` field.

---

## 1. Data Model — `{DialogName}Data.d.json`

```json
{
  "$schema": "https://json-schema.axonivy.com/data-class/12.0.0/data-class.json",
  "simpleName": "{DialogName}Data",
  "namespace": "{namespace}.{DialogName}",
  "isBusinessCaseData": false,
  "fields": [
    { "name": "fieldName", "type": "String", "modifiers": ["PERSISTENT"] }
  ]
}
```

### Field Types

`String`, `Number`, `Integer`, `Boolean`, `Date`, `DateTime`, `List<T>`, `java.io.File`, custom data classes (e.g., `{namespace}.Person`).

### Modifier `PERSISTENT`

Field value survives across process steps. Use for all fields that need to retain their value.

### Example: Using Shared Data Classes as Fields

When using shared data classes (e.g., `ProcurementRequest`, `LogEntry`), reference them by full qualified name:

```json
{
  "$schema": "https://json-schema.axonivy.com/data-class/12.0.0/data-class.json",
  "simpleName": "EnterRequestData",
  "namespace": "workflow.humantask.EnterRequest",
  "isBusinessCaseData": false,
  "fields": [
    { "name": "procurementRequestData", "type": "workflow.humantask.ProcurementRequest", "modifiers": ["PERSISTENT"] },
    { "name": "logEntry", "type": "workflow.humantask.LogEntry", "modifiers": ["PERSISTENT"] }
  ]
}
```

---

## 2. Process — `{DialogName}Process.p.json`

```json
{
  "$schema": "https://json-schema.axonivy.com/process/13.2.0/process.json",
  "id": "UNIQUE_HEX_ID",
  "kind": "HTML_DIALOG",
  "config": {
    "data": "{namespace}.{DialogName}.{DialogName}Data"
  },
  "elements": [ ... ]
}
```

### Element Types

| Type | Purpose | XHTML binding |
|------|---------|---------------|
| `HtmlDialogStart` | Entry point, initializes dialog | — |
| `HtmlDialogEnd` | Flow ends, dialog stays open | — |
| `HtmlDialogExit` | Flow ends, dialog closes and returns to caller | — |
| `HtmlDialogEventStart` | Handles button clicks | `actionListener="#{logic.eventName}"` |
| `HtmlDialogMethodStart` | Handles AJAX calls with return values | `#{logic.methodName}` |
| `Script` | Executes Ivy script between elements | — |

### HtmlDialogStart with Result Mapping

When the dialog returns data to the calling process, `HtmlDialogStart` must define a `result` section with params and map:

```json
{
  "id": "f0", "type": "HtmlDialogStart", "name": "start()",
  "config": {
    "signature": "start",
    "result": {
      "params": [
        { "name": "procurementRequestData", "type": "workflow.humantask.ProcurementRequest", "desc": "" },
        { "name": "logEntry", "type": "workflow.humantask.LogEntry", "desc": "" }
      ],
      "map": {
        "result.procurementRequestData": "in.procurementRequestData",
        "result.logEntry": "in.logEntry"
      }
    },
    "guid": "GUID_1"
  },
  "visual": { "at": { "x": 96, "y": 64 } },
  "connect": [{ "id": "f7", "to": "f6" }]
}
```

- `result.params` — defines return value names and types
- `result.map` — maps process data (`in.*`) back to `result.*` for the calling process
- **CRITICAL**: Without `result`, the calling process cannot resolve `result.fieldName` in its output mapping

### Script Element

Script elements support two ways to set output data: `map` (declarative) and `code` (imperative). Both can be used together.

#### Script with `map` only (declarative mappings)

```json
{
  "id": "f8", "type": "Script", "name": "Init LogEntry",
  "config": {
    "output": {
      "map": {
        "out": "in",
        "out.logEntry.activity": "ivy.cms.co(\"/Dialogs/procurementRequest/requestedBy\")",
        "out.logEntry.timestamp": "new DateTime()",
        "out.logEntry.user.fullName": "ivy.session.getSessionUser().fullName"
      }
    }
  },
  "visual": { "at": { "x": 224, "y": 160 } },
  "connect": [{ "id": "f5", "to": "f4" }]
}
```

#### Script with `map` + `code` (declarative + imperative)

Use `map` for simple assignments and `code` for complex logic (conditionals, imports, loops):

```json
{
  "id": "f6", "type": "Script", "name": "init data",
  "config": {
    "output": {
      "map": {
        "out": "in",
        "out.procurementRequestData": "new workflow.humantask.ProcurementRequest()",
        "out.procurementRequestData.requester.email": "ivy.session.getSessionUser().eMailAddress"
      },
      "code": [
        "import org.apache.commons.lang3.StringUtils;",
        "",
        "",
        "if (StringUtils.isBlank(out.procurementRequestData.requester.email))",
        "{",
        "  out.procurementRequestData.requester.email = \"developer@axonivy.com\";",
        "}"
      ]
    }
  },
  "visual": { "at": { "x": 224, "y": 64 } },
  "connect": [{ "id": "f2", "to": "f1" }]
}
```

**Important**: When using both `map` and `code`, mappings in `map` are applied first, then `code` runs. Always include `"out": "in"` in `map` to carry forward all existing data.

#### Script with `code` only

```json
{
  "id": "f10", "type": "Script", "name": "Process data",
  "config": {
    "output": {
      "code": [
        "in.matchingCountries.clear();",
        "// Ivy script logic here"
      ]
    }
  },
  "visual": { "at": { "x": 272, "y": 512 } },
  "connect": [{ "id": "f13", "to": "f11" }]
}
```

### Minimal Process (start + close)

```json
"elements": [
  {
    "id": "f0", "type": "HtmlDialogStart", "name": "start()",
    "config": {
      "signature": "start",
      "result": {
        "params": [
          { "name": "procurementRequestData", "type": "workflow.humantask.ProcurementRequest", "desc": "" },
          { "name": "logEntry", "type": "workflow.humantask.LogEntry", "desc": "" }
        ],
        "map": {
          "result.procurementRequestData": "in.procurementRequestData",
          "result.logEntry": "in.logEntry"
        }
      },
      "guid": "GUID_1"
    },
    "visual": { "at": { "x": 96, "y": 64 }, "labelOffset": { "x": 12 } },
    "connect": [{ "id": "f7", "to": "f6" }]
  },
  {
    "id": "f6", "type": "Script", "name": "init data",
    "config": {
      "output": {
        "map": {
          "out": "in",
          "out.procurementRequestData": "new workflow.humantask.ProcurementRequest()"
        }
      }
    },
    "visual": { "at": { "x": 224, "y": 64 } },
    "connect": [{ "id": "f2", "to": "f1" }]
  },
  { "id": "f1", "type": "HtmlDialogEnd", "visual": { "at": { "x": 352, "y": 64 } } },
  {
    "id": "f3", "type": "HtmlDialogEventStart", "name": "close",
    "config": { "guid": "GUID_2" },
    "visual": { "at": { "x": 96, "y": 160 }, "labelOffset": { "x": 9 } },
    "connect": [{ "id": "f9", "to": "f8" }]
  },
  {
    "id": "f8", "type": "Script", "name": "Init LogEntry",
    "config": {
      "output": {
        "map": {
          "out": "in",
          "out.logEntry.activity": "ivy.cms.co(\"/Dialogs/procurementRequest/requestedBy\")",
          "out.logEntry.timestamp": "new DateTime()",
          "out.logEntry.user.fullName": "ivy.session.getSessionUser().fullName"
        }
      }
    },
    "visual": { "at": { "x": 224, "y": 160 } },
    "connect": [{ "id": "f5", "to": "f4" }]
  },
  { "id": "f4", "type": "HtmlDialogExit", "visual": { "at": { "x": 352, "y": 160 } } }
]
```

### Process Flow Patterns

```
start()  → Script (init data)   → HtmlDialogEnd   (dialog opens and stays open)
close    → Script (prepare data) → HtmlDialogExit  (dialog closes, returns result to caller)
submit   → Script (validate)     → HtmlDialogExit  (dialog closes, returns result to caller)
upload() → Script (save file)    → HtmlDialogEnd   (file uploaded, dialog stays open)
```

### Method Start (for AJAX methods with params and return)

```json
{
  "id": "f9", "type": "HtmlDialogMethodStart",
  "name": "completeCountry(String)",
  "config": {
    "signature": "completeCountry",
    "input": {
      "params": [{ "name": "query", "type": "String", "desc": "" }],
      "map": { "out.country": "param.query" }
    },
    "result": {
      "params": [{ "name": "matchingCountries", "type": "List<String>", "desc": "" }],
      "map": { "result.matchingCountries": "in.matchingCountries" }
    },
    "guid": "GUID_4"
  },
  "visual": { "at": { "x": 96, "y": 512 } },
  "connect": [{ "id": "f12", "to": "f10" }]
}
```

---

## 3. View — `{DialogName}.xhtml`

Refer to `primefaces.md`, `form-design.md`, `css-js.md`, and other component-specific files for XHTML rules and patterns.

### Base Template

```xml
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://xmlns.jcp.org/jsf/core"
  xmlns:h="http://xmlns.jcp.org/jsf/html" xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
  xmlns:ic="http://ivyteam.ch/jsf/component" xmlns:p="http://primefaces.org/ui"
  xmlns:pe="http://primefaces.org/ui/extensions">
<h:body>
  <ui:composition template="/layouts/frame.xhtml">
    <ui:define name="title">Dialog Title</ui:define>
    <ui:define name="content">
      <!-- content here -->
    </ui:define>
  </ui:composition>
</h:body>
</html>
```

### Example: Form with PrimeFaces Components

Based on the EnterRequest dialog pattern using `h:panelGrid` for simple 2-column forms:

```xml
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://xmlns.jcp.org/jsf/core"
  xmlns:h="http://xmlns.jcp.org/jsf/html" xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
  xmlns:ic="http://ivyteam.ch/jsf/component" xmlns:p="http://primefaces.org/ui"
  xmlns:pe="http://primefaces.org/ui/extensions">
<h:body>
  <ui:composition template="/layouts/frame.xhtml">
    <ui:define name="title">EnterProcurementRequest</ui:define>
    <ui:define name="content">
      <h3>
        <h:outputText escape="false" value="#{ivy.cms.co('/Dialogs/procurementRequest/enterTitle')}" />
      </h3>

      <h:outputText escape="false" value="#{ivy.cms.co('/Dialogs/procurementRequest/enterDescription')}" />
      <br />
      <br />

      <h:form id="form">
        <h:panelGrid columns="2">
          <f:facet name="header">
            <p:messages></p:messages>
          </f:facet>

          <p:outputLabel for="procurementRequestDataDescription"
            value="#{ivy.cms.co('/Dialogs/procurementRequest/description')}" />
          <p:inputText id="procurementRequestDataDescription" value="#{data.procurementRequestData.description}"
            required="true"></p:inputText>

          <p:outputLabel for="procurementRequestDataPricePerUnit"
            value="#{ivy.cms.co('/Dialogs/procurementRequest/pricePerUnit')}" />
          <p:inputNumber id="procurementRequestDataPricePerUnit" value="#{data.procurementRequestData.pricePerUnit}"
            symbol="#{ivy.cms.co('/Dialogs/procurementRequest/currencySymbol')} " symbolPosition="p"
            decimalSeparator="." thousandSeparator="'" required="true" />

          <p:outputLabel for="procurementRequestDataAmount"
            value="#{ivy.cms.co('/Dialogs/procurementRequest/amount')}" />
          <p:spinner id="procurementRequestDataAmount" value="#{data.procurementRequestData.amount}" min="0"
            required="true" />

          <p:outputLabel for="procurementRequestDataNotes" value="#{ivy.cms.co('/Dialogs/procurementRequest/notes')}" />
          <p:inputText id="procurementRequestDataNotes" value="#{data.procurementRequestData.notes}"></p:inputText>

          <br />
          <p:commandButton actionListener="#{logic.close}" value="#{ivy.cms.co('/Dialogs/general/proceed')}"
            update="form" icon="fa-solid fa-check" />
        </h:panelGrid>
      </h:form>

    </ui:define>
  </ui:composition>
</h:body>
</html>
```

### Key XHTML Patterns

- **Labels**: Use `<p:outputLabel for="inputId">` with CMS values `#{ivy.cms.co('/path')}`
- **Data binding**: Use `#{data.fieldName}` or `#{data.nestedObject.field}` for two-way binding
- **Validation**: Add `required="true"` on input components
- **Messages**: Place `<p:messages>` inside `<f:facet name="header">` of `h:panelGrid` or as standalone
- **Submit button**: Use `actionListener="#{logic.eventName}"` with `update="form"` to trigger process events

### EL Expression Bindings

| Expression | Purpose |
|------------|---------|
| `#{data.field}` | Two-way data binding |
| `#{data.person.name}` | Nested object binding |
| `#{logic.eventName}` | Trigger event process (use with `actionListener`) |
| `#{logic.methodName}` | Call method process (AJAX) |
| `#{ivy.cms.co('/path/Label')}` | CMS label (i18n) |
| `#{ivy.session.getSessionUser().fullName}` | Current user info |

### Common PrimeFaces Input Components

| Component | Use Case |
|-----------|----------|
| `<p:inputText>` | Single-line text input |
| `<p:inputNumber>` | Numeric input with formatting (symbol, separators) |
| `<p:spinner>` | Integer input with increment/decrement |
| `<p:inputTextarea>` | Multiline text input |
| `<p:selectOneMenu>` | Dropdown selection |
| `<p:datePicker>` | Date/DateTime input |
| `<p:selectBooleanCheckbox>` | Boolean toggle |
| `<p:commandButton>` | Primary action button |
| `<p:commandLink>` | Secondary action (Cancel, Back) |

---

## 4. Launcher Process — `processes/{Name}.p.json`

```json
{
  "$schema": "https://json-schema.axonivy.com/process/13.2.0/process.json",
  "id": "UNIQUE_HEX_ID",
  "config": { "data": "{namespace}.Data" },
  "elements": [
    {
      "id": "f0", "type": "RequestStart", "name": "start.ivp",
      "config": {
        "signature": "start",
        "request": {
          "name": "Dialog Display Name",
          "category": "demo",
          "customFields": [
            { "name": "cssIcon", "value": "fa fa-desktop" },
            { "name": "embedInFrame", "value": "false" }
          ]
        }
      },
      "visual": { "at": { "x": 96, "y": 64 } },
      "connect": [{ "id": "f2", "to": "f1" }]
    },
    {
      "id": "f1", "type": "DialogCall", "name": "Dialog Name",
      "config": { "dialog": "{namespace}.{DialogName}:start()" },
      "visual": { "at": { "x": 256, "y": 64 } },
      "connect": [{ "id": "f4", "to": "f3" }]
    },
    { "id": "f3", "type": "TaskEnd", "visual": { "at": { "x": 416, "y": 64 } } }
  ]
}
```

---

## 5. Shared Data Classes — `dataclasses/{namespace}/{Name}.d.json`

Create shared data classes when fields represent reusable domain objects (e.g., Person, Address, ProcurementRequest).

```json
{
  "$schema": "https://json-schema.axonivy.com/data-class/12.0.0/data-class.json",
  "simpleName": "Person",
  "namespace": "{namespace}",
  "isBusinessCaseData": false,
  "fields": [
    { "name": "id", "type": "Number", "modifiers": ["PERSISTENT"] },
    { "name": "name", "type": "String", "modifiers": ["PERSISTENT"] },
    { "name": "address", "type": "{namespace}.Address", "modifiers": ["PERSISTENT"] }
  ]
}
```

Reference shared classes from dialog data models using their full qualified name: `{namespace}.Person`.

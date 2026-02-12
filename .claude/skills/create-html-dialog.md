# Instructions: Create HTML Dialog for Axon Ivy 14

Every HTML Dialog requires **3 files** in the same directory under `src_hd/{namespace}/{DialogName}/`:

| File | Purpose |
|------|---------|
| `{DialogName}.xhtml` | UI view (JSF/PrimeFaces) |
| `{DialogName}Data.d.json` | Data model — fields accessible via `#{data.*}` |
| `{DialogName}Process.p.json` | Process logic — start, events, methods |

To invoke a dialog, create a **launcher process** in `processes/` using `DialogCall`.

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

**Field types:** `String`, `Number`, `Integer`, `Boolean`, `Date`, `DateTime`, `List<T>`, `ch.ivyteam.ivy.scripting.objects.File`, custom data classes.

**Modifier `PERSISTENT`** — field value survives across process steps.

---

## 2. Process Logic — `{DialogName}Process.p.json`

```json
{
  "$schema": "https://json-schema.axonivy.com/process/13.2.0/process.json",
  "id": "UNIQUE_HEX_ID",
  "kind": "HTML_DIALOG",
  "config": {
    "data": "{namespace}.{DialogName}.{DialogName}Data"
  },
  "elements": [ ... ],
  "layout": {
    "lanes": [
      { "name": "Initialisation", "size": 192 },
      { "name": "Events", "size": 225 },
      { "name": "Methods", "size": 192 }
    ]
  }
}
```

### Process Element Types

| Type | Purpose | XHTML binding |
|------|---------|---------------|
| `HtmlDialogStart` | Entry point | — |
| `HtmlDialogEnd` | Returns to view (stays in dialog) | — |
| `HtmlDialogExit` | Terminates dialog, returns to calling process | — |
| `HtmlDialogEventStart` | Handles button clicks | `#{logic.eventName}` |
| `HtmlDialogMethodStart` | Handles AJAX calls with return values | `#{logic.methodName}` |
| `Script` | Executes Ivy script between elements | — |

### Minimal Process (start + one event + close)

```json
"elements": [
  {
    "id": "f0", "type": "HtmlDialogStart", "name": "start()",
    "config": { "signature": "start", "guid": "GUID_1" },
    "visual": { "at": { "x": 96, "y": 96 } },
    "connect": [{ "id": "f2", "to": "f1" }]
  },
  { "id": "f1", "type": "HtmlDialogEnd", "visual": { "at": { "x": 224, "y": 96 } } },
  {
    "id": "f3", "type": "HtmlDialogEventStart", "name": "send",
    "config": { "guid": "GUID_2" },
    "visual": { "at": { "x": 96, "y": 256 } },
    "connect": [{ "id": "f5", "to": "f4" }]
  },
  { "id": "f4", "type": "HtmlDialogEnd", "visual": { "at": { "x": 224, "y": 256 } } },
  {
    "id": "f6", "type": "HtmlDialogEventStart", "name": "close",
    "config": { "guid": "GUID_3" },
    "visual": { "at": { "x": 96, "y": 352 } },
    "connect": [{ "id": "f8", "to": "f7" }]
  },
  { "id": "f7", "type": "HtmlDialogExit", "visual": { "at": { "x": 224, "y": 352 } } }
]
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

### Script Element

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

**ID/GUID generation:** Use unique 16-character uppercase hex strings (e.g., `139E30EF308FD0DC`).

---

## 3. View — `{DialogName}.xhtml`

### Base Template

```xml
<html xmlns="http://www.w3.org/1999/xhtml"
  xmlns:f="http://xmlns.jcp.org/jsf/core"
  xmlns:h="http://xmlns.jcp.org/jsf/html"
  xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
  xmlns:ic="http://ivyteam.ch/jsf/component"
  xmlns:p="http://primefaces.org/ui"
  xmlns:cc="http://xmlns.jcp.org/jsf/composite">
<h:body>
  <ui:composition template="/layouts/HtmlDemo.xhtml">
    <ui:param name="centerHeader" value="Dialog Title" />
    <ui:define name="breadcrumb">Category/Dialog Name</ui:define>
    <ui:define name="content">
      <!-- content here -->
    </ui:define>
  </ui:composition>
</h:body>
</html>
```

### EL Expression Bindings

| Expression | Purpose |
|------------|---------|
| `#{data.field}` | Two-way data binding |
| `#{data.person.name}` | Nested object binding |
| `#{logic.eventName}` | Trigger event process |
| `#{logic.methodName}` | Call method process (AJAX) |
| `#{ivy.cms.co('/path/Label')}` | CMS label (i18n) |
| `#{ivy.html.fileref(data.file)}` | File download URL |

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

## 5. Shared Data Class — `dataclasses/{namespace}/{Name}.d.json`

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

---

## Demo Patterns

### Pattern A: Input Form

3-column layout: label + input + validation message. Use `<p:messages>` for global errors.

```xml
<h:form id="Form">
  <h:panelGroup layout="block" id="panel">
    <p:messages id="msgs" />
    <p:panelGrid columns="3" layout="flex" style="max-width: 700px;">

      <!-- Text input -->
      <p:outputLabel value="#{ivy.cms.co('/Labels/Name')}" for="Name" />
      <p:inputText value="#{data.name}" id="Name" required="true" />
      <p:message for="Name" display="icon" />

      <!-- Date input with converter -->
      <p:outputLabel value="Birthday" for="Birthday" />
      <p:calendar id="Birthday" value="#{data.birthday}" pattern="dd.MM.yyyy" required="true">
        <f:convertDateTime pattern="dd.MM.yyyy" />
      </p:calendar>
      <p:message for="Birthday" display="icon" />

      <!-- Email with custom validator -->
      <p:outputLabel value="Email" for="Mail" />
      <p:inputText id="Mail" value="#{data.email}" required="true">
        <f:validator validatorId="com.example.CustomMailValidator" />
      </p:inputText>
      <p:message for="Mail" display="icon" />

      <!-- Masked input (phone) -->
      <p:outputLabel value="Phone" for="Phone" />
      <p:inputMask value="#{data.phone}" id="Phone" mask="+99 (0)99 999 99 99" />
      <p:message for="Phone" display="icon" />

      <!-- Number with converter + length validator -->
      <p:outputLabel value="Zip" for="Zip" />
      <p:inputText value="#{data.zipCode}" id="Zip">
        <f:convertNumber integerOnly="true" groupingUsed="false" />
        <f:validateLength minimum="4" maximum="5" />
      </p:inputText>
      <p:message for="Zip" display="icon" />

      <!-- Auto-complete (calls method process) -->
      <p:outputLabel value="Country" for="Country" />
      <p:autoComplete value="#{data.country}" id="Country"
        completeMethod="#{logic.completeCountry}" queryDelay="500" />
      <p:message for="Country" display="icon" />

    </p:panelGrid>
    <p:commandButton value="Send" icon="pi pi-save"
      actionListener="#{logic.send}" update="panel" />
  </h:panelGroup>
</h:form>
```

**Process:** `HtmlDialogEventStart name="send"` → `HtmlDialogEnd`
**Process:** `HtmlDialogMethodStart name="completeCountry(String)"` → `Script` → `HtmlDialogEnd`

---

### Pattern B: Data Table (Sort, Filter, Paginate, Select)

```xml
<h:form id="Form">
  <p:dataTable var="person" value="#{data.persons}" paginator="true" rows="10"
    selectionMode="single" selection="#{data.selectedPerson}" rowKey="#{person.id}">

    <f:facet name="header">
      <p:inputText id="globalFilter" onkeyup="PF('personTable').filter()" placeholder="Search" />
    </f:facet>

    <p:column headerText="Name" sortBy="#{person.name}" filterBy="#{person.name}">
      <h:outputText value="#{person.name}" />
    </p:column>

    <p:column headerText="Year" sortBy="#{person.birthYear}">
      <h:outputText value="#{person.birthYear}" />
    </p:column>

    <p:ajax event="rowSelect" listener="#{logic.onRowSelect}" update=":Form:detail" />
  </p:dataTable>
</h:form>
```

**Lazy loading** for large datasets — use `lazy="true"` with a `LazyDataModel`:
```java
public class PersonLazyDataModel extends LazyDataModel<Person> {
    @Override
    public List<Person> load(int first, int pageSize,
        Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        // fetch page from DB
    }
    @Override
    public int count(Map<String, FilterMeta> filterBy) { return totalCount; }
}
```

**Editable table** — use `editable="true"` with `<p:cellEditor>` and `<p:rowEditor>`.

---

### Pattern C: File Upload & Download

```xml
<!-- Simple upload -->
<h:form enctype="multipart/form-data">
  <p:fileUpload value="#{data.ivyFile}" mode="simple" />
  <p:commandButton value="Upload" actionListener="#{logic.fileUpload}" ajax="false" />
</h:form>

<!-- Download link -->
<a href="#{ivy.html.fileref(data.ivyFile)}">#{data.ivyFile.name}</a>
```

**Advanced upload** (multiple files, drag-and-drop): use `mode="advanced"` with `fileUploadListener`.

---

### Pattern D: Multi-View Dialog (Wizard)

One dialog with multiple XHTML views sharing the same data object. Switch views via method calls.

**Views:** `MultiViewDemo.xhtml`, `Invoice.xhtml`, `CreditCard.xhtml`, `Summary.xhtml`

```xml
<!-- In Invoice.xhtml -->
<p:commandButton value="Next" actionListener="#{logic.nextView}" />
```

**Process:** `HtmlDialogEventStart name="nextView"` → `Script` (set view) → `HtmlDialogEnd`

---

### Pattern E: Reusable Component (Ivy Component)

Embed one HTML Dialog inside another via the `ic:` namespace:

```xml
<ic:com.example.component.PersonComponent
  person="#{data.person}" id="personComp" />
```

The component dialog's `HtmlDialogStart` declares the parameter:
```json
"name": "start(com.example.Person)",
"config": {
  "signature": "start",
  "input": {
    "params": [{ "name": "person", "type": "com.example.Person" }],
    "map": { "out.person": "param.person" }
  }
}
```

---

### Pattern F: JSF Composite Component

Lightweight reusable fragments (no process logic needed). Place in `webContent/resources/{library}/`:

```xml
<!-- webContent/resources/components/addressInput.xhtml -->
<cc:interface>
  <cc:attribute name="address" type="com.example.Address" required="true" />
  <cc:attribute name="label" type="java.lang.String" default="Address" />
</cc:interface>
<cc:implementation>
  <p:fieldset legend="#{cc.attrs.label}">
    <p:panelGrid columns="2">
      <p:outputLabel value="Street" for="street" />
      <p:inputText value="#{cc.attrs.address.street}" id="street" />
    </p:panelGrid>
    <cc:insertChildren />
  </p:fieldset>
</cc:implementation>
```

Use with: `<comp:addressInput address="#{data.address}" label="Billing" />`

---

### Pattern G: Dynamic Rows (Add/Delete)

```xml
<ui:repeat var="score" value="#{data.scores}" varStatus="row">
  <p:panelGrid columns="3">
    <p:calendar value="#{score.date}" />
    <p:inputText value="#{score.points}" />
    <p:commandLink actionListener="#{logic.delRow(row.index)}"
      rendered="#{row.index > 0}" update="@form">
      <i class="pi pi-trash" />
    </p:commandLink>
  </p:panelGrid>
</ui:repeat>
<p:commandLink actionListener="#{logic.addRow}" update="@form">Add Row</p:commandLink>
```

**Process:** `HtmlDialogEventStart name="addRow"` → `Script` (add to list) → `HtmlDialogEnd`
**Process:** `HtmlDialogMethodStart name="delRow(Integer)"` → `Script` (remove from list) → `HtmlDialogEnd`

---

### Pattern H: AJAX Partial Updates

Update specific panels without full page reload:

```xml
<h:panelGroup id="detailPanel" layout="block">
  <h:outputText value="#{data.detail}" />
</h:panelGroup>
<p:commandButton value="Load" actionListener="#{logic.loadDetail}" update="detailPanel" />
```

---

### Pattern I: Modal Dialog (Lazy Loaded)

```xml
<p:commandButton value="Open" onclick="PF('dlg').show()" type="button" />
<p:dialog widgetVar="dlg" modal="true" header="Details" dynamic="true">
  <h:panelGroup rendered="#{data.showDialog}">
    <!-- content loads only when dialog opens -->
  </h:panelGroup>
</p:dialog>
```

---

### Pattern J: Selection Widgets

**Dropdown:**
```xml
<p:selectOneMenu value="#{data.selected}" filter="true">
  <f:selectItem itemLabel="-- Select --" itemValue="" />
  <f:selectItems value="#{data.options}" var="opt"
    itemLabel="#{opt.label}" itemValue="#{opt.value}" />
</p:selectOneMenu>
```

**Multi-checkbox:**
```xml
<p:selectManyCheckbox value="#{data.selectedItems}" converter="ivy.ListItem">
  <f:selectItems value="#{data.allItems}" />
</p:selectManyCheckbox>
```

**Pick list (dual list):**
```xml
<p:pickList value="#{data.dualList}" var="item" itemLabel="#{item}" itemValue="#{item}" />
```

---

## Validation

### JSF Inline Validators
```xml
<p:inputText value="#{data.value}">
  <f:validateLength minimum="3" maximum="50" />
  <f:convertNumber integerOnly="true" />
</p:inputText>
```

### Custom Validator (Java)
```java
@FacesValidator("com.example.CustomMailValidator")
public class CustomMailValidator implements Validator {
    public void validate(FacesContext ctx, UIComponent comp, Object value)
        throws ValidatorException {
        if (!((String) value).contains("@"))
            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email", null));
    }
}
```
```xml
<p:inputText value="#{data.email}">
  <f:validator validatorId="com.example.CustomMailValidator" />
</p:inputText>
```

### Bean Validation (JSR 303)
```java
public class Person {
    @NotNull @Size(min = 3, max = 10)
    private String name;
    @Min(1900) @Max(2100)
    private Integer yearOfBirth;
    @Pattern(regexp = "\\d{3}\\.\\d{4}\\.\\d{4}\\.\\d{2}")
    private String ssn;
}
```
Bind via `#{data.person.name}` — validation is automatic.

---

## Managed Beans

```java
@ManagedBean
@ApplicationScoped  // or @SessionScoped, @ViewScoped, @RequestScoped
public class MyBean {
    public String getValue() { return "shared value"; }
}
```
Use in XHTML: `#{myBean.value}`

---

## Error Handling

```xml
<!-- AJAX exception handler -->
<p:ajaxExceptionHandler type="javax.faces.application.ViewExpiredException"
  update="exceptionDialog" onexception="PF('exDlg').show()" />

<!-- Non-AJAX: redirect to error page in web.xml -->
```

---

## Key PrimeFaces Components

| Component | Use Case |
|-----------|----------|
| `p:inputText` | Text input |
| `p:inputTextarea` | Multi-line text |
| `p:inputMask` | Formatted input (phone, SSN) |
| `p:calendar` | Date picker |
| `p:autoComplete` | Search with AJAX method |
| `p:selectOneMenu` | Dropdown |
| `p:selectManyCheckbox` | Multi-select |
| `p:fileUpload` | File upload (simple/advanced) |
| `p:dataTable` | Data grid (sort, filter, paginate, select, edit) |
| `p:panelGrid` | Form layout (`layout="flex"`) |
| `p:dialog` | Modal/non-modal popup |
| `p:tabView` | Tabbed panels |
| `p:commandButton` | Action button (AJAX) |
| `p:messages` | Global validation messages |
| `p:message` | Per-field validation icon |
| `p:chart` | Charts (bar, pie, line) |
| `p:textEditor` | Rich text (WYSIWYG) |
| `p:pickList` | Dual-list picker |
| `p:orderList` | Reorderable list |
| `p:barcode` | Barcode/QR code generation |

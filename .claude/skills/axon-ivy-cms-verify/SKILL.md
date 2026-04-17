````skill
---
name: axon-ivy-cms-verify
description: Verification checklist for Axon Ivy CMS files (cms_*.yaml). MUST be used after axon-ivy-cms skill to catch common errors.
---

**MANDATORY**: Run this checklist on EVERY `cms_*.yaml` file after creating or modifying it with the `axon-ivy-cms` skill. Read each CMS file, then verify each check below. Fix any violations before considering the task done.

Also enforces the CMS rules from [.claude/rules/axon-ivy-coding.md](../../rules/axon-ivy-coding.md) section 7.

## Checklist

### 1. YAML Boolean Keys — `Yes`, `No`, `On`, `Off`, `True`, `False` must be quoted

YAML 1.1 (used by SnakeYAML / Axon Ivy Java runtime) treats these words as **booleans**, not strings. Unquoted, `Yes` becomes `true` and `No` becomes `false`, silently breaking CMS lookups.

Affected words (case-insensitive): `yes`, `no`, `on`, `off`, `true`, `false`, `y`, `n`

```yaml
# WRONG — parsed as {true: "Yes", false: "No"}
Labels:
  Yes: 'Yes'
  No: 'No'

# RIGHT — keys stay as strings "Yes" and "No"
Labels:
  'Yes': 'Yes'
  'No': 'No'
```

Scan for: Any **key** (left side of `:`) that is one of these reserved words without quotes. Values (right side) already quoted are fine.

### 2. Key Hierarchy Mismatch Between Language Files

All `cms_*.yaml` files MUST have **identical key hierarchies**. If a key exists in `cms_en.yaml`, it must exist in every other language file and vice versa.

```yaml
# cms_en.yaml — has 3 keys
Labels:
  Submit: Submit
  Cancel: Cancel
  Approve: Approve

# cms_de.yaml — WRONG: missing "Approve"
Labels:
  Submit: Absenden
  Cancel: Abbrechen

# cms_de.yaml — RIGHT: all 3 keys present
Labels:
  Submit: Absenden
  Cancel: Abbrechen
  Approve: Genehmigen
```

How to check: Compare key paths across all `cms_*.yaml` files. Every leaf key path must appear in every file.

### 3. Special Characters in Values — Must be quoted

Values containing YAML special characters must be wrapped in quotes. Unquoted values with these characters cause parse errors or silent truncation.

Characters requiring quotes: `: { } [ ] , & * # ? | - < > = ! % @ \`

```yaml
# WRONG — colon breaks parsing
Labels:
  Greeting: Hello: World
  Note: Task for role: Admin

# RIGHT — quoted values
Labels:
  Greeting: 'Hello: World'
  Note: 'Task for role: Admin'
```

Single quotes within values must be escaped by doubling: `'It''s working'`

### 4. Indentation — Must use spaces, NEVER tabs

YAML forbids tab characters for indentation. Tabs cause the entire file to fail parsing.

```yaml
# WRONG — tab indentation (invisible but fatal)
Labels:
→ Submit: Submit

# RIGHT — space indentation (2 spaces per level)
Labels:
  Submit: Submit
```

### 5. Duplicate Keys — No two keys at the same level with the same name

YAML silently uses only the **last occurrence** of duplicate keys, discarding earlier ones.

```yaml
# WRONG — second "Title" silently overwrites the first
Dialogs:
  hr:
    onboarding:
      MyDialog:
        Title: First Title
        Title: Second Title    # Only this one survives

# RIGHT — unique keys
Dialogs:
  hr:
    onboarding:
      MyDialog:
        Title: First Title
        Subtitle: Second Title
```

### 6. CMS Path vs. YAML Key Path — Colons not slashes

In YAML files, the hierarchy uses **colon-indentation** (nested keys). The CMS access path uses `/` slashes but the YAML must NOT contain literal slashes as key separators.

```yaml
# WRONG — slash in key name
Dialogs:
  hr/onboarding/MyDialog:
    Title: My Title

# RIGHT — nested keys with colon indentation
Dialogs:
  hr:
    onboarding:
      MyDialog:
        Title: My Title
```

### 7. Null/Empty Values — Keys must have values

Keys with null or empty values return `null` from `ivy.cms.co()`, which typically renders as blank text in the UI.

```yaml
# WRONG — null value (missing value after colon)
Labels:
  Title:
  Description:

# RIGHT — explicit value
Labels:
  Title: My Title
  Description: Enter details here
```

If intentionally blank, use an explicit empty string: `Title: ''`

### 8. Dialog CMS Namespace — Must match dialog package path

CMS keys under `Dialogs:` must mirror the dialog's Java package path exactly. Mismatched paths cause `ivy.cms.co()` to return `null`.

```yaml
# Dialog Java package: hr.onboarding.StartOnboarding

# WRONG — package path doesn't match
Dialogs:
  onboarding:
    StartOnboarding:
      Title: Start

# RIGHT — exact package match
Dialogs:
  hr:
    onboarding:
      StartOnboarding:
        Title: Start
```

### 9. Portal Override Keys — Must match Portal's actual CMS keys

When overriding Portal labels under `ch.ivy.addon.portalkit.ui.jsf:`, the key path must match the Portal's original key **exactly**. Invented keys are silently ignored.

```yaml
# WRONG — "saveButton" doesn't exist in Portal CMS
ch.ivy.addon.portalkit.ui.jsf:
  common:
    saveButton: Save

# RIGHT — "save" is the actual Portal key
ch.ivy.addon.portalkit.ui.jsf:
  common:
    save: Save
```

### 10. Consistent Quoting Style — Don't mix quote types for similar values

Use single quotes consistently for values that need quoting. Don't mix single and double quotes within the same file for similar value patterns.

```yaml
# INCONSISTENT
Labels:
  Select: "Select..."
  PhonePlaceholder: '+1 (555) 000-0000'

# CONSISTENT
Labels:
  Select: 'Select...'
  PhonePlaceholder: '+1 (555) 000-0000'
```

Exception: Double quotes are needed for escape sequences like `\n` (newline).

### 11. Folder purpose — no mixing UI text with technical errors

CMS content MUST be organized by purpose. Never put a backend/technical error message inside a `Dialogs:` or `Labels:` namespace that is surfaced as friendly UI text, and never put user-facing UI copy inside an `errors:` / `messages:` namespace intended for technical codes.

```yaml
# WRONG — technical detail leaked into a button label
Dialogs:
  hr:
    leaveRequest:
      SubmitButton: "NullPointerException at LeaveService.submit(line 42)"

# RIGHT — dedicated purpose-based namespaces
Dialogs:
  hr:
    leaveRequest:
      SubmitButton: Submit
errors:
  leave:
    saveFailed: "Could not save leave request {0}. Please try again."
```

Rule §7 (Axon Ivy coding rules): keep `/messages`, `/errors`, `/ui`, `/templates`, `/email` separate.

### 12. Placeholders `{0}`, `{1}` — NEVER string concatenation at the call site

Messages with dynamic content MUST use positional placeholders so the whole sentence is translatable.

```yaml
# WRONG — forces the Java caller to concatenate, breaking non-English translations
errors:
  customer:
    notFoundPrefix: "Customer with ID "
    notFoundSuffix: " not found."

# RIGHT — one message with placeholders, translatable as a whole
errors:
  customer:
    notFound: "Customer with ID {0} not found."
```

```java
// Corresponding Java access — still pulled via an enum / constant, not a string literal
String msg = Ivy.cms().co(CmsMessageKey.ERROR_CUSTOMER_NOT_FOUND.getKey(),
                          java.util.List.of(customerId));
```

### 13. Java callers access CMS via enum / constant, not ad-hoc string literals

Scan the Java side (not the YAML) for every `Ivy.cms().co("...")` and `ivy.cms.co("...")`. The first argument should be an enum value or a named constant (see `CmsMessageKey` pattern in rule §7). Flag bare string literals used for the same key in multiple places — they should be lifted into an enum.

```java
// WRONG — literal keys scattered everywhere
Ivy.cms().co("/errors/customer/notFound");
Ivy.cms().co("/errors/customer/notFound");   // duplicated literal

// RIGHT — one source of truth
Ivy.cms().co(CmsMessageKey.ERROR_CUSTOMER_NOT_FOUND.getKey());
```
````

---
name: axon-ivy-html
description: Rules and best practices for Axon Ivy HTML Dialog implementations including PrimeFaces, PrimeFlex, CSS, JS, and Ivy components.
---

## Coding Standards (MUST follow)

Every dialog artifact — XHTML, managed bean, dialog logic process, CSS/JS — MUST comply with [.claude/rules/axon-ivy-coding.md](../../rules/axon-ivy-coding.md):

- **Dialog names reflect the use case** — `customerEditDialog`, `leaveApprovalForm`. Never `Dialog1`, `ScreenX`, `View2`.
- **All user-visible strings come from CMS** (via `#{ivy.cms.co('...')}`) — no hardcoded English in XHTML. See section 7.
- **Bean validation** on any form-backing object (`jakarta.validation` annotations); validate early at the dialog boundary.
- **Managed beans** follow Java rules — one public type per file, no wildcard imports, Javadoc on public methods, role-based packages (`managedbean/`, `converter/`, `validator/`).
- **Logging inside beans** uses SLF4J parameterized calls (`LOG.info("… {}", value)`), never string concatenation; never log form payloads containing PII.

## Creating a New HTML Dialog

When asked to create, implement, or build an HTML Dialog:
1. **Always** load `create-dialog.md` first — it defines the required files and creation workflow
2. Then load other references below as needed based on the dialog's features

## Always Load

These references are needed for every HTML dialog:

- Load `primefaces.md` — JSF & PrimeFaces component rules
- Load `css-js.md` — Styling, layout, icons, CSS & JS rules

## Load When Needed

- Creating a new dialog (all required files) → Load `create-dialog.md`
- Building input forms → Load `form-design.md`
- Using date picker or calendar components (`p:datePicker`) → Load `date-picker.md`
- Using file upload components (`p:fileUpload`) → Load `file-upload.md`
- Working with dialog logic, events, or methods (`#{logic.*}`, `#{data.*}`) → Load `logic-process.md` and `code.md`
- Creating or updating managed beans for dialogs → Load `managed-bean.md`
- Using Ivy HTML components (`<ic:*>`) → Load `ivy.md`
- Looking up icon names → Refer to `icons.txt`
- Adding/updating UI labels or translations → Use `axon-ivy-cms` skill to create CMS entries

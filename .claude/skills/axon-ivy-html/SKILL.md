---
name: axon-ivy-html
description: Rules and best practices for Axon Ivy HTML Dialog implementations including PrimeFaces, PrimeFlex, CSS, JS, and Ivy components.
---

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

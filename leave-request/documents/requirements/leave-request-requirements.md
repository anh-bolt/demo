# Leave Request Workflow — Requirements Document

## 1. General Information

**Requirement ID:** REQ-PRC-001

**Requirement Name:** Leave Request Workflow

**Business Domain:** HR

**Stakeholders:**
- Business Owner: HR Department
- Process Owner: Department Manager
- IT Owner: IT / Axon Ivy Administrator

**Priority:** Must

**Status:** Draft

---

## 2. Business Goal

- **Business problem:** Leave requests are currently handled informally (email/verbal), leading to lost requests, unclear approval chains, and no audit trail.
- **Expected outcome:** A structured, trackable digital workflow where employees submit leave requests that go through a two-level approval chain (Line Manager → Department Manager), with automatic email notifications at each decision.
- **Success metrics:** All leave requests tracked digitally; approval decisions recorded with comments; employees notified within minutes of any decision.

---

## 3. Process Overview

### 3.1 Process Trigger

An employee manually starts the process by navigating to the "Create Leave Request" start form and submitting it.

### 3.2 High-Level Flow

1. Employee fills in and submits the Leave Request form.
2. A task is created for the Line Manager to approve or reject.
3. If the Line Manager **approves**: email is sent to the employee (intermediate notification), then a task is created for the Department Manager to approve or reject.
4. If the Line Manager **rejects**: email is sent to the employee and the process ends with status REJECTED.
5. If the Department Manager **approves**: email is sent to the employee and the process ends with status APPROVED.
6. If the Department Manager **rejects**: email is sent to the employee and the process ends with status REJECTED.

### 3.3 State Machine

```
SUBMITTED → IN_LINE_MANAGER_REVIEW → [Approved] → IN_DEPT_MANAGER_REVIEW → [Approved] → APPROVED (end)
                                   → [Rejected] → REJECTED (end)
                                                                          → [Rejected] → REJECTED (end)
```

| From Status | To Status | Triggered By |
|---|---|---|
| (process start) | SUBMITTED | Employee submits form |
| SUBMITTED | IN_LINE_MANAGER_REVIEW | System creates Line Manager task |
| IN_LINE_MANAGER_REVIEW | IN_DEPT_MANAGER_REVIEW | Line Manager approves |
| IN_LINE_MANAGER_REVIEW | REJECTED | Line Manager rejects |
| IN_DEPT_MANAGER_REVIEW | APPROVED | Department Manager approves |
| IN_DEPT_MANAGER_REVIEW | REJECTED | Department Manager rejects |

### 3.4 Process End States

| End State | Description |
|---|---|
| APPROVED | Both Line Manager and Department Manager have approved |
| REJECTED | Rejected by Line Manager or Department Manager |

---

## 4. Actors & Roles

| Role | Internal Name | Responsibility | Permissions |
|---|---|---|---|
| Employee | `LeaveRequest/Employees` | Submit a leave request | Start process, view own requests |
| Line Manager | `LeaveRequest/LineManagers` | First-level approval | View request, approve or reject with comment |
| Department Manager | `LeaveRequest/DepartmentManagers` | Second-level approval | View request + line manager comment, approve or reject with comment |

**Role assignment logic:** Tasks are assigned to the role group (not a specific individual). Any user holding the role can claim and complete the task.

---

## 5. Detailed Task Definition

### Task 1: Create Leave Request

**Type:** Process Start (HTML Dialog)

**Responsible Role:** Employee (LeaveRequest/Employees)

**Inputs:**
| Field | Source |
|---|---|
| leaveType | Employee selects from dropdown |
| startDate | Employee enters via date picker |
| endDate | Employee enters via date picker |
| reason | Employee types free text |

**Auto-populated (from session user, not editable):**
| Field | Source |
|---|---|
| employeeId | `ivy.session.getSessionUser().getMemberName()` |
| employeeName | `ivy.session.getSessionUser().getFullName()` |
| employeeEmail | `ivy.session.getSessionUser().getEMailAddress()` |

**System-generated:**
| Field | Value |
|---|---|
| requestId | UUID generated on submit |
| createdDate | Current timestamp |
| numberOfDays | `endDate - startDate + 1` (calendar days) |
| status | `SUBMITTED` (set by script after form submit) |

**UI Required:** Yes

**Validation Rules:**
- `leaveType` is required
- `startDate` is required; must be >= today
- `endDate` is required; must be >= `startDate`
- `reason` is required; max 500 characters

**Possible Outcomes:**
- Submit: process continues to Line Manager Review task

---

### Task 2: Line Manager Review

**Type:** User Task (HTML Dialog)

**Responsible Role:** Line Manager (LeaveRequest/LineManagers)

**Inputs (all read-only from process data):**
- employeeName, employeeEmail
- leaveType, startDate, endDate, numberOfDays, reason

**Editable fields:**
- `lineManagerComment` (String, optional when approving, required when rejecting)

**UI Required:** Yes

**Validation Rules:**
- `lineManagerComment` is required if the action is Reject

**Possible Outcomes:**
- **Approve:** status → `IN_DEPT_MANAGER_REVIEW`; email sent to employee; Department Manager task created
- **Reject:** status → `REJECTED`; email sent to employee; process ends

**SLA:** 2 business days from task creation

---

### Task 3: Department Manager Review

**Type:** User Task (HTML Dialog)

**Responsible Role:** Department Manager (LeaveRequest/DepartmentManagers)

**Inputs (all read-only from process data):**
- employeeName, employeeEmail
- leaveType, startDate, endDate, numberOfDays, reason
- lineManagerComment

**Editable fields:**
- `departmentManagerComment` (String, optional when approving, required when rejecting)

**UI Required:** Yes

**Validation Rules:**
- `departmentManagerComment` is required if the action is Reject

**Possible Outcomes:**
- **Approve:** status → `APPROVED`; email sent to employee; process ends
- **Reject:** status → `REJECTED`; email sent to employee; process ends

**SLA:** 2 business days from task creation

---

### Task 4: Send Email Notification

**Type:** Script Task (using Ivy Mail API)

**Responsible Role:** System

**Triggered:** After every approval or rejection decision (4 events total — see Notifications section)

**Inputs:** LeaveRequest data (startDate, endDate, status, approver comment, employeeEmail)

**Outputs:** Email sent to employee

**UI Required:** No

---

## 6. Data Model

### 6.1 Enumerations

| Enum Name | Values | Used By |
|---|---|---|
| `LeaveType` | `ANNUAL_LEAVE`, `SICK_LEAVE`, `UNPAID_LEAVE`, `PARENTAL_LEAVE`, `BEREAVEMENT_LEAVE`, `OTHER` | LeaveRequest.leaveType |
| `LeaveStatus` | `SUBMITTED`, `IN_LINE_MANAGER_REVIEW`, `IN_DEPT_MANAGER_REVIEW`, `APPROVED`, `REJECTED` | LeaveRequest.status |

### 6.2 Process Data (LeaveRequest Data Class)

Data class file: `leaveRequest/src/com/axonivy/LeaveRequest.d.json`

| Field | Type | Mandatory | Description |
|---|---|---|---|
| requestId | String | Yes (system) | UUID, generated on submit |
| employeeId | String | Yes (system) | Session user member name |
| employeeName | String | Yes (system) | Session user full name |
| employeeEmail | String | Yes (system) | Session user email |
| leaveType | LeaveType | Yes (user) | Type of leave |
| startDate | LocalDate | Yes (user) | First day of leave |
| endDate | LocalDate | Yes (user) | Last day of leave |
| numberOfDays | int | Yes (system) | Calculated: endDate - startDate + 1 |
| reason | String | Yes (user) | Reason for leave, max 500 chars |
| status | LeaveStatus | Yes (system) | Current status of the request |
| createdDate | LocalDateTime | Yes (system) | Timestamp of submission |
| lineManagerComment | String | No | Comment by Line Manager (required on reject) |
| departmentManagerComment | String | No | Comment by Department Manager (required on reject) |

### 6.3 Process Variable

The single process variable is:

| Variable | Type | Description |
|---|---|---|
| `leaveRequest` | `LeaveRequest` | Main data object flowing through the process |

### 6.4 Data Ownership

| Data Section | Create Leave Request (Employee) | Line Manager Review | Dept Manager Review |
|---|---|---|---|
| Employee Info (id, name, email) | Auto-populated, hidden | Read-only | Read-only |
| Leave Details (type, dates, days, reason) | Editable | Read-only | Read-only |
| Line Manager Comment | Hidden | Editable | Read-only |
| Dept Manager Comment | Hidden | Hidden | Editable |
| Status, Request ID, Created Date | System-generated | Read-only | Read-only |

---

## 7. Business Rules

1. `startDate` must be >= today's date (employees cannot request leave in the past).
2. `endDate` must be >= `startDate`.
3. `reason` is mandatory; max 500 characters.
4. `leaveType` is mandatory.
5. `numberOfDays` is auto-calculated as: `endDate - startDate + 1` (inclusive, calendar days).
6. `lineManagerComment` is required when the Line Manager selects Reject; optional when selecting Approve.
7. `departmentManagerComment` is required when the Department Manager selects Reject; optional when selecting Approve.
8. An employee can submit multiple overlapping leave requests (the system does not automatically detect conflicts — that is a future enhancement).
9. Once submitted, the employee cannot edit or cancel the request.

---

## 8. Notifications

All notifications are sent via email using Axon Ivy's mail sending capability.

| Event | Recipient | Channel | Subject | Message Summary |
|---|---|---|---|---|
| Line Manager approves | Employee | Email | "Your Leave Request — Approved by Line Manager" | "Your leave request from [startDate] to [endDate] ([numberOfDays] days) has been approved by the Line Manager and forwarded to the Department Manager for final approval. Line Manager comment: [lineManagerComment or 'No comment']" |
| Line Manager rejects | Employee | Email | "Your Leave Request — Rejected by Line Manager" | "Your leave request from [startDate] to [endDate] ([numberOfDays] days) has been rejected by the Line Manager. Comment: [lineManagerComment]" |
| Department Manager approves | Employee | Email | "Your Leave Request — Fully Approved" | "Your leave request from [startDate] to [endDate] ([numberOfDays] days) has been fully approved. Department Manager comment: [departmentManagerComment or 'No comment']" |
| Department Manager rejects | Employee | Email | "Your Leave Request — Rejected by Department Manager" | "Your leave request from [startDate] to [endDate] ([numberOfDays] days) has been rejected by the Department Manager. Comment: [departmentManagerComment]" |

**Email sender:** Configured in Axon Ivy variable `LeaveRequest.mailSender` (e.g., `noreply@company.com`).

---

## 9. Integration & External Systems

| System | Direction | Purpose |
|---|---|---|
| Email Server (SMTP) | Outbound | Send notification emails to employee |

**Error Handling:** If email sending fails, the process continues (best-effort delivery). The failure is logged to Axon Ivy's application log. No retry or manual intervention is defined for this version.

---

## 10. Exception & Alternative Flows

| Scenario | Handling |
|---|---|
| Line Manager rejects | Email sent to employee with comment; process ends with status REJECTED |
| Department Manager rejects | Email sent to employee with comment; process ends with status REJECTED |
| Line Manager does not act within 2 business days | Ivy task expiry triggers a reminder notification to the Line Manager role; task remains open |
| Department Manager does not act within 2 business days | Ivy task expiry triggers a reminder notification to the Department Manager role; task remains open |
| Employee submits with invalid dates | Client-side and server-side validation prevents submission; user sees validation error |

---

## 11. SLA & Time Constraints

| Task | Due Time | Expiry Action |
|---|---|---|
| Line Manager Review | 2 business days | Send reminder email to LeaveRequest/LineManagers role |
| Department Manager Review | 2 business days | Send reminder email to LeaveRequest/DepartmentManagers role |

---

## 12. Security & Data Protection

- Employee email is used only for notification purposes and is sourced from the Axon Ivy user directory.
- No PII is stored externally; all data is within the Axon Ivy system.
- Data visibility is enforced by task assignment: only the assigned role can open the review task.
- Employees cannot access other employees' leave requests through the process.

---

## 13. Audit & Compliance

Axon Ivy automatically logs:
- Who started the process (session user)
- Task assignments and completions (role, user, timestamp)
- State transitions

Additionally, the `LeaveRequest` data class captures:
- `createdDate`: submission timestamp
- `lineManagerComment` and `departmentManagerComment`: approver decisions with reasons

---

## 14. UI Requirements

### Form 1: Create Leave Request

**Used by role:** Employee (LeaveRequest/Employees)
**Purpose:** Employee enters leave details and submits the request.

| Section | Fields | Mode |
|---|---|---|
| Leave Details | leaveType (dropdown), startDate (date picker), endDate (date picker), numberOfDays (number, auto-calculated), reason (textarea) | Editable |

**Dynamic behavior:**
- `numberOfDays` updates automatically when `startDate` or `endDate` changes (client-side AJAX or `p:ajax`)
- `startDate` date picker disables past dates (minDate = today)
- `endDate` date picker disables dates before `startDate`

**Form actions:**
- **Submit** — validates all fields, initializes system fields (requestId, createdDate, status, employeeInfo), submits the process
- **Cancel** — navigates away without creating a request

---

### Form 2: Line Manager Review

**Used by role:** Line Manager (LeaveRequest/LineManagers)
**Purpose:** Line Manager reviews the leave request and approves or rejects.

| Section | Fields | Mode |
|---|---|---|
| Employee Info | employeeName, employeeEmail | Read-only |
| Leave Details | leaveType, startDate, endDate, numberOfDays, reason | Read-only |
| Decision | lineManagerComment (textarea) | Editable |

**Dynamic behavior:**
- `lineManagerComment` is highlighted as required only when the Reject button is clicked (client-side validation before server submit)

**Form actions:**
- **Approve** — saves `lineManagerComment` (if any); transitions to Department Manager Review
- **Reject** — validates `lineManagerComment` is non-empty; transitions to REJECTED end state

---

### Form 3: Department Manager Review

**Used by role:** Department Manager (LeaveRequest/DepartmentManagers)
**Purpose:** Department Manager reviews the leave request (with Line Manager's decision) and approves or rejects.

| Section | Fields | Mode |
|---|---|---|
| Employee Info | employeeName, employeeEmail | Read-only |
| Leave Details | leaveType, startDate, endDate, numberOfDays, reason | Read-only |
| Line Manager Decision | lineManagerComment | Read-only |
| Decision | departmentManagerComment (textarea) | Editable |

**Dynamic behavior:**
- `departmentManagerComment` is highlighted as required only when the Reject button is clicked

**Form actions:**
- **Approve** — saves `departmentManagerComment` (if any); transitions to APPROVED end state
- **Reject** — validates `departmentManagerComment` is non-empty; transitions to REJECTED end state

---

## 15. Acceptance Criteria

The process is accepted when:

- [ ] An employee with the Employee role can open and submit the Create Leave Request form.
- [ ] Employee info (name, email) is auto-populated from the session user.
- [ ] `numberOfDays` is correctly calculated and updated in real time.
- [ ] A task appears in the Line Manager's task list after employee submission.
- [ ] Line Manager can approve or reject with a comment.
- [ ] When Line Manager rejects, the employee receives an email with the comment; no further tasks are created.
- [ ] When Line Manager approves, the employee receives an intermediate email; a task appears for the Department Manager.
- [ ] Department Manager can approve or reject with a comment.
- [ ] When Department Manager approves or rejects, the employee receives an email with the comment.
- [ ] Request status is correctly updated at each transition.
- [ ] Validation prevents submission with past start date, end date before start date, or missing required fields.
- [ ] Comment is required on reject and enforced in both review forms.
- [ ] Task expiry fires after 2 business days without action.

---

## 16. Open Questions / Risks

| # | Question | Assumption Made |
|---|---|---|
| 1 | How is the specific Line Manager determined per employee? | Any user in `LeaveRequest/LineManagers` role can claim the task (pool task, not targeted). |
| 2 | Should the employee receive an intermediate email when Line Manager approves? | Yes — notified at every decision event. |
| 3 | Can employees cancel or edit a request after submission? | No — request is locked after submission. |
| 4 | Are weekend days counted in `numberOfDays`? | Yes — calendar days (simpler). Can be changed to business days. |
| 5 | Should overlapping requests be blocked? | No — conflict detection is a future enhancement. |
| 6 | What happens if the employee email address is not set in the user profile? | Email sending is skipped; failure is logged. |

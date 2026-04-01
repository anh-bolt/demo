# Story 4: Line Manager Review

**As a** Line Manager,
**I want to** review a submitted leave request and approve or reject it,
**So that** the request proceeds to the next approval level or is finalized as rejected.

## Acceptance Criteria

- [ ] Line Manager sees a task in their task list after an employee submits a leave request
- [ ] The review form displays employee info (name, email) as read-only
- [ ] The review form displays leave details (type, dates, number of days, reason) as read-only
- [ ] Line Manager can enter a comment in the `lineManagerComment` field
- [ ] Line Manager can click **Approve** to advance the request to Department Manager review
- [ ] Line Manager can click **Reject** to end the process with status REJECTED
- [ ] `lineManagerComment` is required when rejecting (validation enforced)
- [ ] `lineManagerComment` is optional when approving
- [ ] On approve: status changes to `IN_DEPT_MANAGER_REVIEW`, email sent to employee, task created for Department Manager
- [ ] On reject: status changes to `REJECTED`, email sent to employee, process ends

## Technical Details

- **Role:** `LeaveRequest/LineManagers`
- **Type:** User Task (HTML Dialog)
- **SLA:** 2 business days from task creation
- **Read-only fields:** employeeName, employeeEmail, leaveType, startDate, endDate, numberOfDays, reason
- **Editable fields:** lineManagerComment (textarea)

## UI Behavior

- `lineManagerComment` is highlighted as required only when the Reject button is clicked (client-side validation before server submit)
- **Approve** button saves comment (if any) and transitions to Department Manager Review
- **Reject** button validates comment is non-empty, then transitions to REJECTED end state

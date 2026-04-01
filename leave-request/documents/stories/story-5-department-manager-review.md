# Story 5: Department Manager Review

**As a** Department Manager,
**I want to** review a leave request that has been approved by the Line Manager and make a final decision,
**So that** the request is fully approved or rejected with proper notification to the employee.

## Acceptance Criteria

- [ ] Department Manager sees a task in their task list after the Line Manager approves a request
- [ ] The review form displays employee info (name, email) as read-only
- [ ] The review form displays leave details (type, dates, number of days, reason) as read-only
- [ ] The review form displays the Line Manager's comment as read-only
- [ ] Department Manager can enter a comment in the `departmentManagerComment` field
- [ ] Department Manager can click **Approve** to finalize the request as APPROVED
- [ ] Department Manager can click **Reject** to finalize the request as REJECTED
- [ ] `departmentManagerComment` is required when rejecting (validation enforced)
- [ ] `departmentManagerComment` is optional when approving
- [ ] On approve: status changes to `APPROVED`, email sent to employee, process ends
- [ ] On reject: status changes to `REJECTED`, email sent to employee, process ends

## Technical Details

- **Role:** `LeaveRequest/DepartmentManagers`
- **Type:** User Task (HTML Dialog)
- **SLA:** 2 business days from task creation
- **Read-only fields:** employeeName, employeeEmail, leaveType, startDate, endDate, numberOfDays, reason, lineManagerComment
- **Editable fields:** departmentManagerComment (textarea)

## UI Behavior

- `departmentManagerComment` is highlighted as required only when the Reject button is clicked (client-side validation before server submit)
- **Approve** button saves comment (if any) and transitions to APPROVED end state
- **Reject** button validates comment is non-empty, then transitions to REJECTED end state

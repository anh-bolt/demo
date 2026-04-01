# Story 7: SLA & Task Expiry

**As a** process owner,
**I want** review tasks to have a 2 business day SLA with automatic reminders,
**So that** leave requests are not left unattended and approvers are reminded to act.

## Acceptance Criteria

- [ ] Line Manager Review task has a due time of 2 business days from task creation
- [ ] Department Manager Review task has a due time of 2 business days from task creation
- [ ] When Line Manager task expires, a reminder email is sent to the `LeaveRequest/LineManagers` role
- [ ] When Department Manager task expires, a reminder email is sent to the `LeaveRequest/DepartmentManagers` role
- [ ] The task remains open after expiry (not auto-closed)

## Technical Details

- **Duration format:** `new Duration("2D")`
- **Expiry action:** Send reminder notification to the responsible role
- Tasks remain claimable after expiry — the reminder is informational only

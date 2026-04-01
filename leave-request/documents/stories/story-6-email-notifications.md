# Story 6: Email Notifications

**As an** Employee,
**I want to** receive email notifications at each approval or rejection decision,
**So that** I am promptly informed about the status of my leave request.

## Acceptance Criteria

- [ ] Employee receives email when Line Manager approves with subject: "Your Leave Request — Approved by Line Manager"
- [ ] Employee receives email when Line Manager rejects with subject: "Your Leave Request — Rejected by Line Manager"
- [ ] Employee receives email when Department Manager approves with subject: "Your Leave Request — Fully Approved"
- [ ] Employee receives email when Department Manager rejects with subject: "Your Leave Request — Rejected by Department Manager"
- [ ] All emails include: startDate, endDate, numberOfDays, and the approver's comment (or "No comment" if empty)
- [ ] Email sender is configured via variable `LeaveRequest.mailSender`
- [ ] If email sending fails, the process continues (best-effort delivery)
- [ ] Email sending failures are logged to the application log

## Technical Details

- **Type:** Script Task (using Ivy Mail API)
- **Triggered:** After every approval or rejection decision (4 events total)
- **Recipient:** Employee's email (`leaveRequest.employeeEmail`)

## Email Templates

| Event | Subject | Message Summary |
|---|---|---|
| Line Manager approves | "Your Leave Request — Approved by Line Manager" | Leave details + forwarded to Department Manager + Line Manager comment |
| Line Manager rejects | "Your Leave Request — Rejected by Line Manager" | Leave details + rejected + Line Manager comment |
| Dept Manager approves | "Your Leave Request — Fully Approved" | Leave details + fully approved + Department Manager comment |
| Dept Manager rejects | "Your Leave Request — Rejected by Department Manager" | Leave details + rejected + Department Manager comment |

# Story 3: Submit Leave Request

**As an** Employee,
**I want to** fill in and submit a leave request form,
**So that** my leave request is formally recorded and sent for approval.

## Acceptance Criteria

- [x] Employee can select a leave type from: ANNUAL_LEAVE, SICK_LEAVE, UNPAID_LEAVE, PARENTAL_LEAVE, BEREAVEMENT_LEAVE, OTHER
- [x] Employee can pick a start date (must be >= today) and end date (must be >= start date)
- [x] Employee can enter a reason (required, max 500 characters)
- [x] Number of days is auto-calculated as `endDate - startDate + 1` and updates in real time
- [x] Employee info (name, email, ID) is auto-populated from the session user and not editable
- [x] System generates a UUID `requestId` and `createdDate` on submit
- [x] Status is set to `SUBMITTED` after submission
- [x] A task is created for the Line Manager role after submission
- [x] Validation prevents submission with missing fields, past start date, or end date before start date
- [x] Once submitted, the employee cannot edit or cancel the request

## Technical Details

- **Role:** `LeaveRequest/Employees`
- **Type:** Process Start (HTML Dialog)
- **Form fields (editable):** leaveType (dropdown), startDate (date picker), endDate (date picker), reason (textarea)
- **Auto-populated fields:** employeeId (`ivy.session.getSessionUser().getMemberName()`), employeeName (`ivy.session.getSessionUser().getFullName()`), employeeEmail (`ivy.session.getSessionUser().getEMailAddress()`)
- **System-generated fields:** requestId (UUID), createdDate (timestamp), numberOfDays (calculated), status (SUBMITTED)

## UI Behavior

- `numberOfDays` updates automatically when `startDate` or `endDate` changes (client-side AJAX)
- `startDate` date picker disables past dates (minDate = today)
- `endDate` date picker disables dates before `startDate`
- **Submit** button validates all fields, initializes system fields, and submits the process
- **Cancel** button navigates away without creating a request

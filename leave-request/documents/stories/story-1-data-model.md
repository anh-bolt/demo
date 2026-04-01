# Story 1: Data Model & Enumerations

**As a** developer,
**I want to** define the LeaveRequest data class and supporting enumerations,
**So that** all process data is structured and typed correctly for the workflow.

## Acceptance Criteria

- [ ] `LeaveType` enum defined with values: `ANNUAL_LEAVE`, `SICK_LEAVE`, `UNPAID_LEAVE`, `PARENTAL_LEAVE`, `BEREAVEMENT_LEAVE`, `OTHER`
- [ ] `LeaveStatus` enum defined with values: `SUBMITTED`, `IN_LINE_MANAGER_REVIEW`, `IN_DEPT_MANAGER_REVIEW`, `APPROVED`, `REJECTED`
- [ ] `LeaveRequest` data class defined with all fields per requirements (Section 6.2)
- [ ] Process variable `leaveRequest` of type `LeaveRequest` is available for the process
- [ ] Auto-generated Java classes compile successfully after build

## Technical Details

- **Data class file:** `dataclasses/com/axonivy/leaveRequest/LeaveRequest.d.json`
- **Package:** `com.axonivy.leaveRequest`,
- **Fields:**

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

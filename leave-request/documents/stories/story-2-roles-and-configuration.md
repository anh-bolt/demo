# Story 2: Roles, Users & Configuration

**As an** administrator,
**I want to** configure roles, test users, and application variables,
**So that** the workflow has proper access control and is ready for testing.

## Acceptance Criteria

- [ ] Role `LeaveRequest/Employees` is defined and grants permission to start the leave request process
- [ ] Role `LeaveRequest/LineManagers` is defined and grants permission to review as first-level approver
- [ ] Role `LeaveRequest/DepartmentManagers` is defined and grants permission to review as second-level approver
- [ ] Test user `john.employee` exists with Employee role and email `john.employee@company.com`
- [ ] Test user `mary.manager` exists with Line Manager role and email `mary.manager@company.com`
- [ ] Test user `david.director` exists with Department Manager role and email `david.director@company.com`
- [ ] All test users have password `Password1`
- [ ] Variable `LeaveRequest.mailSender` is configured (e.g., `noreply@company.com`)
- [ ] Tasks are assigned to role groups (pool tasks), not specific individuals

## Technical Details

- **Role config file:** `config/roles.yaml`
- **User config file:** `config/users.yaml`
- **Variable config file:** `config/variables.yaml`
- Role assignment is group-based: any user holding the role can claim and complete the task

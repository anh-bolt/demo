# Axon Ivy Process Design Patterns

## Signal/Catch Pattern
Use for async communication between processes:
- Signal Start: Triggered by external signal
- Signal Boundary: Catch signal during task execution

## Subprocess Patterns
- Embedded: Runs in same case context
- Call: Separate case, linked to parent
- Trigger: Fire and forget

## Error Handling
- Error Boundary Events on tasks
- Error Start Events in error processes
- Use try/catch in Script Steps

## Gateway Best Practices
- Exclusive Gateway: Use for simple if/else
- Parallel Gateway: Ensure all paths merge
- Inclusive Gateway: At least one path must be valid

## Task Assignment Patterns
```ivyscript
// Role-based
task.activator = ivy.wf.findRole("Manager");

// User-based
task.activator = ivy.session.getSessionUser();

// Dynamic from data
task.activator = ivy.wf.findUser(in.assigneeUsername);
```
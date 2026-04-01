package com.axonivy.leaveRequest.model;

public enum LeaveStatus {
  SUBMITTED("Submitted"),
  IN_LINE_MANAGER_REVIEW("In Line Manager Review"),
  IN_DEPT_MANAGER_REVIEW("In Department Manager Review"),
  APPROVED("Approved"),
  REJECTED("Rejected");

  private final String description;

  LeaveStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public boolean isApproved() {
    return this == APPROVED;
  }

  public boolean isRejected() {
    return this == REJECTED;
  }

  public boolean isPending() {
    return this != APPROVED && this != REJECTED;
  }
}

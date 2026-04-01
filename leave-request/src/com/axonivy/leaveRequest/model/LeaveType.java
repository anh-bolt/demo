package com.axonivy.leaveRequest.model;

public enum LeaveType {
  ANNUAL_LEAVE("Annual Leave"),
  SICK_LEAVE("Sick Leave"),
  UNPAID_LEAVE("Unpaid Leave"),
  PARENTAL_LEAVE("Parental Leave"),
  BEREAVEMENT_LEAVE("Bereavement Leave"),
  OTHER("Other");

  private final String description;

  LeaveType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

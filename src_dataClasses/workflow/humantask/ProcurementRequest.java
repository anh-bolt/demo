package workflow.humantask;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class ProcurementRequest", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class ProcurementRequest extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -8548442143255205118L;

  private java.lang.Number amount;

  /**
   * Gets the field amount.
   * @return the value of the field amount; may be null.
   */
  public java.lang.Number getAmount()
  {
    return amount;
  }

  /**
   * Sets the field amount.
   * @param _amount the new value of the field amount.
   */
  public void setAmount(java.lang.Number _amount)
  {
    amount = _amount;
  }

  private java.lang.String description;

  /**
   * Gets the field description.
   * @return the value of the field description; may be null.
   */
  public java.lang.String getDescription()
  {
    return description;
  }

  /**
   * Sets the field description.
   * @param _description the new value of the field description.
   */
  public void setDescription(java.lang.String _description)
  {
    description = _description;
  }

  private java.lang.String notes;

  /**
   * Gets the field notes.
   * @return the value of the field notes; may be null.
   */
  public java.lang.String getNotes()
  {
    return notes;
  }

  /**
   * Sets the field notes.
   * @param _notes the new value of the field notes.
   */
  public void setNotes(java.lang.String _notes)
  {
    notes = _notes;
  }

  private java.lang.Number pricePerUnit;

  /**
   * Gets the field pricePerUnit.
   * @return the value of the field pricePerUnit; may be null.
   */
  public java.lang.Number getPricePerUnit()
  {
    return pricePerUnit;
  }

  /**
   * Sets the field pricePerUnit.
   * @param _pricePerUnit the new value of the field pricePerUnit.
   */
  public void setPricePerUnit(java.lang.Number _pricePerUnit)
  {
    pricePerUnit = _pricePerUnit;
  }

  private java.lang.Boolean dataOkManager;

  /**
   * Gets the field dataOkManager.
   * @return the value of the field dataOkManager; may be null.
   */
  public java.lang.Boolean getDataOkManager()
  {
    return dataOkManager;
  }

  /**
   * Sets the field dataOkManager.
   * @param _dataOkManager the new value of the field dataOkManager.
   */
  public void setDataOkManager(java.lang.Boolean _dataOkManager)
  {
    dataOkManager = _dataOkManager;
  }

  private java.lang.Boolean accepted;

  /**
   * Gets the field accepted.
   * @return the value of the field accepted; may be null.
   */
  public java.lang.Boolean getAccepted()
  {
    return accepted;
  }

  /**
   * Sets the field accepted.
   * @param _accepted the new value of the field accepted.
   */
  public void setAccepted(java.lang.Boolean _accepted)
  {
    accepted = _accepted;
  }

  private java.lang.Boolean dataOkTeamLeader;

  /**
   * Gets the field dataOkTeamLeader.
   * @return the value of the field dataOkTeamLeader; may be null.
   */
  public java.lang.Boolean getDataOkTeamLeader()
  {
    return dataOkTeamLeader;
  }

  /**
   * Sets the field dataOkTeamLeader.
   * @param _dataOkTeamLeader the new value of the field dataOkTeamLeader.
   */
  public void setDataOkTeamLeader(java.lang.Boolean _dataOkTeamLeader)
  {
    dataOkTeamLeader = _dataOkTeamLeader;
  }

  private java.lang.Number totalPrice;

  /**
   * Gets the field totalPrice.
   * @return the value of the field totalPrice; may be null.
   */
  public java.lang.Number getTotalPrice()
  {
    return totalPrice;
  }

  /**
   * Sets the field totalPrice.
   * @param _totalPrice the new value of the field totalPrice.
   */
  public void setTotalPrice(java.lang.Number _totalPrice)
  {
    totalPrice = _totalPrice;
  }

  private workflow.humantask.User requester;

  /**
   * Gets the field requester.
   * @return the value of the field requester; may be null.
   */
  public workflow.humantask.User getRequester()
  {
    return requester;
  }

  /**
   * Sets the field requester.
   * @param _requester the new value of the field requester.
   */
  public void setRequester(workflow.humantask.User _requester)
  {
    requester = _requester;
  }

  private ch.ivyteam.ivy.scripting.objects.List<workflow.humantask.LogEntry> activityLog;

  /**
   * Gets the field activityLog.
   * @return the value of the field activityLog; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.List<workflow.humantask.LogEntry> getActivityLog()
  {
    return activityLog;
  }

  /**
   * Sets the field activityLog.
   * @param _activityLog the new value of the field activityLog.
   */
  public void setActivityLog(ch.ivyteam.ivy.scripting.objects.List<workflow.humantask.LogEntry> _activityLog)
  {
    activityLog = _activityLog;
  }

  private java.util.List<java.lang.String> dynamicRoles;

  /**
   * Gets the field dynamicRoles.
   * @return the value of the field dynamicRoles; may be null.
   */
  public java.util.List<java.lang.String> getDynamicRoles()
  {
    return dynamicRoles;
  }

  /**
   * Sets the field dynamicRoles.
   * @param _dynamicRoles the new value of the field dynamicRoles.
   */
  public void setDynamicRoles(java.util.List<java.lang.String> _dynamicRoles)
  {
    dynamicRoles = _dynamicRoles;
  }

  private java.lang.String dynamicRole;

  /**
   * Gets the field dynamicRole.
   * @return the value of the field dynamicRole; may be null.
   */
  public java.lang.String getDynamicRole()
  {
    return dynamicRole;
  }

  /**
   * Sets the field dynamicRole.
   * @param _dynamicRole the new value of the field dynamicRole.
   */
  public void setDynamicRole(java.lang.String _dynamicRole)
  {
    dynamicRole = _dynamicRole;
  }

}

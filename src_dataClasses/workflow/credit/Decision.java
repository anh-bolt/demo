package workflow.credit;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class Decision", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class Decision extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 343715453106758082L;

  private java.lang.Boolean granted;

  /**
   * Gets the field granted.
   * @return the value of the field granted; may be null.
   */
  public java.lang.Boolean getGranted()
  {
    return granted;
  }

  /**
   * Sets the field granted.
   * @param _granted the new value of the field granted.
   */
  public void setGranted(java.lang.Boolean _granted)
  {
    granted = _granted;
  }

  private java.lang.String rejectReason;

  /**
   * Gets the field rejectReason.
   * @return the value of the field rejectReason; may be null.
   */
  public java.lang.String getRejectReason()
  {
    return rejectReason;
  }

  /**
   * Sets the field rejectReason.
   * @param _rejectReason the new value of the field rejectReason.
   */
  public void setRejectReason(java.lang.String _rejectReason)
  {
    rejectReason = _rejectReason;
  }

  private java.lang.Boolean needsLevel1Approval;

  /**
   * Gets the field needsLevel1Approval.
   * @return the value of the field needsLevel1Approval; may be null.
   */
  public java.lang.Boolean getNeedsLevel1Approval()
  {
    return needsLevel1Approval;
  }

  /**
   * Sets the field needsLevel1Approval.
   * @param _needsLevel1Approval the new value of the field needsLevel1Approval.
   */
  public void setNeedsLevel1Approval(java.lang.Boolean _needsLevel1Approval)
  {
    needsLevel1Approval = _needsLevel1Approval;
  }

  private java.lang.Boolean needsLevel2Approval;

  /**
   * Gets the field needsLevel2Approval.
   * @return the value of the field needsLevel2Approval; may be null.
   */
  public java.lang.Boolean getNeedsLevel2Approval()
  {
    return needsLevel2Approval;
  }

  /**
   * Sets the field needsLevel2Approval.
   * @param _needsLevel2Approval the new value of the field needsLevel2Approval.
   */
  public void setNeedsLevel2Approval(java.lang.Boolean _needsLevel2Approval)
  {
    needsLevel2Approval = _needsLevel2Approval;
  }

  private java.lang.Boolean level1Approved;

  /**
   * Gets the field level1Approved.
   * @return the value of the field level1Approved; may be null.
   */
  public java.lang.Boolean getLevel1Approved()
  {
    return level1Approved;
  }

  /**
   * Sets the field level1Approved.
   * @param _level1Approved the new value of the field level1Approved.
   */
  public void setLevel1Approved(java.lang.Boolean _level1Approved)
  {
    level1Approved = _level1Approved;
  }

  private java.lang.Boolean level2Approved;

  /**
   * Gets the field level2Approved.
   * @return the value of the field level2Approved; may be null.
   */
  public java.lang.Boolean getLevel2Approved()
  {
    return level2Approved;
  }

  /**
   * Sets the field level2Approved.
   * @param _level2Approved the new value of the field level2Approved.
   */
  public void setLevel2Approved(java.lang.Boolean _level2Approved)
  {
    level2Approved = _level2Approved;
  }

}

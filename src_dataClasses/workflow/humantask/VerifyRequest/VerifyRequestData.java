package workflow.humantask.VerifyRequest;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class VerifyRequestData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class VerifyRequestData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 3002962713522723309L;

  private workflow.humantask.ProcurementRequest procurementRequest;

  /**
   * Gets the field procurementRequest.
   * @return the value of the field procurementRequest; may be null.
   */
  public workflow.humantask.ProcurementRequest getProcurementRequest()
  {
    return procurementRequest;
  }

  /**
   * Sets the field procurementRequest.
   * @param _procurementRequest the new value of the field procurementRequest.
   */
  public void setProcurementRequest(workflow.humantask.ProcurementRequest _procurementRequest)
  {
    procurementRequest = _procurementRequest;
  }

  private java.lang.Boolean ok;

  /**
   * Gets the field ok.
   * @return the value of the field ok; may be null.
   */
  public java.lang.Boolean getOk()
  {
    return ok;
  }

  /**
   * Sets the field ok.
   * @param _ok the new value of the field ok.
   */
  public void setOk(java.lang.Boolean _ok)
  {
    ok = _ok;
  }

  private workflow.humantask.LogEntry logEntry;

  /**
   * Gets the field logEntry.
   * @return the value of the field logEntry; may be null.
   */
  public workflow.humantask.LogEntry getLogEntry()
  {
    return logEntry;
  }

  /**
   * Sets the field logEntry.
   * @param _logEntry the new value of the field logEntry.
   */
  public void setLogEntry(workflow.humantask.LogEntry _logEntry)
  {
    logEntry = _logEntry;
  }

}

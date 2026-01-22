package workflow.humantask.EnterRequest;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class EnterRequestData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class EnterRequestData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 1818439977042019491L;

  private workflow.humantask.ProcurementRequest procurementRequestData;

  /**
   * Gets the field procurementRequestData.
   * @return the value of the field procurementRequestData; may be null.
   */
  public workflow.humantask.ProcurementRequest getProcurementRequestData()
  {
    return procurementRequestData;
  }

  /**
   * Sets the field procurementRequestData.
   * @param _procurementRequestData the new value of the field procurementRequestData.
   */
  public void setProcurementRequestData(workflow.humantask.ProcurementRequest _procurementRequestData)
  {
    procurementRequestData = _procurementRequestData;
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

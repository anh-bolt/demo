package workflow.humantask.RequestLog;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class RequestLogData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class RequestLogData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 1233780595368046015L;

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

}

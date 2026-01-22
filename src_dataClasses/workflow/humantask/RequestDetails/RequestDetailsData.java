package workflow.humantask.RequestDetails;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class RequestDetailsData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class RequestDetailsData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 1233780595527784969L;

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

}

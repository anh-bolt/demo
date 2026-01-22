package workflow.credit;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class CreditDossier", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
@ch.ivyteam.ivy.business.data.store.context.BusinessCaseData
public class CreditDossier extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -3074227083932252353L;

  private workflow.credit.Person customer;

  /**
   * Gets the field customer.
   * @return the value of the field customer; may be null.
   */
  public workflow.credit.Person getCustomer()
  {
    return customer;
  }

  /**
   * Sets the field customer.
   * @param _customer the new value of the field customer.
   */
  public void setCustomer(workflow.credit.Person _customer)
  {
    customer = _customer;
  }

  private workflow.credit.CreditRequest request;

  /**
   * Gets the field request.
   * @return the value of the field request; may be null.
   */
  public workflow.credit.CreditRequest getRequest()
  {
    return request;
  }

  /**
   * Sets the field request.
   * @param _request the new value of the field request.
   */
  public void setRequest(workflow.credit.CreditRequest _request)
  {
    request = _request;
  }

  private workflow.credit.Decision decision;

  /**
   * Gets the field decision.
   * @return the value of the field decision; may be null.
   */
  public workflow.credit.Decision getDecision()
  {
    return decision;
  }

  /**
   * Sets the field decision.
   * @param _decision the new value of the field decision.
   */
  public void setDecision(workflow.credit.Decision _decision)
  {
    decision = _decision;
  }

}

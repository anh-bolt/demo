package workflow.order.BookPayment;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class BookPaymentData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class BookPaymentData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -7867276755498309068L;

  private workflow.order.Person customer;

  /**
   * Gets the field customer.
   * @return the value of the field customer; may be null.
   */
  public workflow.order.Person getCustomer()
  {
    return customer;
  }

  /**
   * Sets the field customer.
   * @param _customer the new value of the field customer.
   */
  public void setCustomer(workflow.order.Person _customer)
  {
    customer = _customer;
  }

  private workflow.order.Order order;

  /**
   * Gets the field order.
   * @return the value of the field order; may be null.
   */
  public workflow.order.Order getOrder()
  {
    return order;
  }

  /**
   * Sets the field order.
   * @param _order the new value of the field order.
   */
  public void setOrder(workflow.order.Order _order)
  {
    order = _order;
  }

}

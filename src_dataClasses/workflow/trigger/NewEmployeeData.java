package workflow.trigger;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class NewEmployeeData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class NewEmployeeData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -4365924445826731214L;

  private java.lang.Boolean needsParkingLot;

  /**
   * Gets the field needsParkingLot.
   * @return the value of the field needsParkingLot; may be null.
   */
  public java.lang.Boolean getNeedsParkingLot()
  {
    return needsParkingLot;
  }

  /**
   * Sets the field needsParkingLot.
   * @param _needsParkingLot the new value of the field needsParkingLot.
   */
  public void setNeedsParkingLot(java.lang.Boolean _needsParkingLot)
  {
    needsParkingLot = _needsParkingLot;
  }

  private java.lang.String name;

  /**
   * Gets the field name.
   * @return the value of the field name; may be null.
   */
  public java.lang.String getName()
  {
    return name;
  }

  /**
   * Sets the field name.
   * @param _name the new value of the field name.
   */
  public void setName(java.lang.String _name)
  {
    name = _name;
  }

  private ch.ivyteam.ivy.scripting.objects.Date dateOfBirth;

  /**
   * Gets the field dateOfBirth.
   * @return the value of the field dateOfBirth; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.Date getDateOfBirth()
  {
    return dateOfBirth;
  }

  /**
   * Sets the field dateOfBirth.
   * @param _dateOfBirth the new value of the field dateOfBirth.
   */
  public void setDateOfBirth(ch.ivyteam.ivy.scripting.objects.Date _dateOfBirth)
  {
    dateOfBirth = _dateOfBirth;
  }

  private java.lang.String email;

  /**
   * Gets the field email.
   * @return the value of the field email; may be null.
   */
  public java.lang.String getEmail()
  {
    return email;
  }

  /**
   * Sets the field email.
   * @param _email the new value of the field email.
   */
  public void setEmail(java.lang.String _email)
  {
    email = _email;
  }

  private java.lang.Number selectedParkingLotNr;

  /**
   * Gets the field selectedParkingLotNr.
   * @return the value of the field selectedParkingLotNr; may be null.
   */
  public java.lang.Number getSelectedParkingLotNr()
  {
    return selectedParkingLotNr;
  }

  /**
   * Sets the field selectedParkingLotNr.
   * @param _selectedParkingLotNr the new value of the field selectedParkingLotNr.
   */
  public void setSelectedParkingLotNr(java.lang.Number _selectedParkingLotNr)
  {
    selectedParkingLotNr = _selectedParkingLotNr;
  }

  private ch.ivyteam.ivy.scripting.objects.List<java.lang.Number> availableParkingLots;

  /**
   * Gets the field availableParkingLots.
   * @return the value of the field availableParkingLots; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.List<java.lang.Number> getAvailableParkingLots()
  {
    return availableParkingLots;
  }

  /**
   * Sets the field availableParkingLots.
   * @param _availableParkingLots the new value of the field availableParkingLots.
   */
  public void setAvailableParkingLots(ch.ivyteam.ivy.scripting.objects.List<java.lang.Number> _availableParkingLots)
  {
    availableParkingLots = _availableParkingLots;
  }

}

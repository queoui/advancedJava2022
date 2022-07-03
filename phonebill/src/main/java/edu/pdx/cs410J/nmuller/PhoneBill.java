package edu.pdx.cs410J.nmuller;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;
import java.util.Vector;

/**
 * This class represents a <code>PhoneBill</code>
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;
  public Vector<PhoneCall> billOfCalls = new Vector<>() {
  };

  /**
   * Creates a new <code>PhoneBill</code>
   * @param customer
   *        The name of the PhoneBill owner, only one per PhoneBill
   */

  public PhoneBill(String customer) {this.customer = customer;}

  /**
   *
   * Returns a <code>String</code> of the PhoneBill customer
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  /**
   * Adds a call to billOfCalls for a specific customer bill
   * @param call
   *        Void return is successful, otherwise Exception thrown
   */
  @Override
  public void addPhoneCall(PhoneCall call) {
    try {
      billOfCalls.addElement(call);
    }catch(Exception e){
      System.err.println("failure to add phone call to phone bill");
    }
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * @return Collection in form of vector
   * returns the entire bill with all phone calls
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {return this.billOfCalls;}

}
